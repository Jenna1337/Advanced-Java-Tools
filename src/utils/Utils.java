package utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import static utils.WebRequest.GET;

public class Utils
{
	private static final java.util.Random rand = new java.util.Random();
	private Utils(){
	}
	
	private static final boolean debuggerRunning = java.lang.management.ManagementFactory
			.getRuntimeMXBean().getInputArguments().toString()
			.contains("suspend=y");
	public static boolean isdebuggerrunning(){
		return debuggerRunning;
	}
	
	private static volatile int evalRecalcCount = 0;
	private static final int evalRecalcCountMax = 1;
	public static synchronized String eval(String query){
		query = query.trim();
		if(query.equalsIgnoreCase("alive"))
			return "Cogito ergo sum";
		String response = null;
		try{
			final String input = urlencode(query);
			final String inputurl = "http://www.wolframalpha.com/input/?i="
					+ input;
			GET(inputurl);
			GET("http://www.wolframalpha.com/input/cookietest.jsp");
			final String proxycode = getStringValueJSON("code",
					GET("https://www.wolframalpha.com/input/api/v1/code?"
							+ System.currentTimeMillis() * 1000));
			return parseResponse(response = WebRequest.GET(
					new URL("" + "https://www.wolframalpha.com/input/json.jsp"
							+ "?async=false" + "&banners=raw"
							+ "&debuggingdata=false" + "&format=image,plaintext"
							+ "&formattimeout=16" + "&input=" + input
							+ "&output=JSON" + "&parsetimeout=10"
							+ "&proxycode=" + proxycode + "&scantimeout=1"
							+ "&sponsorcategories=false"
							+ "&statemethod=deploybutton"
							+ "&storesubpodexprs=true"),
					new String[][]{{"Host", "https://www.wolframalpha.com"},
						{"Origin", "https://www.wolframalpha.com"},
						{"Referer", inputurl}}));
		}
		catch(ConnectException e){
			e.printStackTrace();
			return "Failed to connect to server";
		}
		catch(IOException e){
			e.printStackTrace();
			return "I do not understand.";
		}
		catch(ScriptException | NullPointerException e){
			String respo = null;
			if(response != null && evalRecalcCount <= evalRecalcCountMax){
				evalRecalcCount++;
				try{
					respo = parseResponse(response = GET(
							getStringValueJSON("recalculate", response)));
				}
				catch(Exception e2){
					System.err.println("Failed to parse JSON:\n" + response);
				}
				evalRecalcCount--;
			}
			e.printStackTrace();
			return (respo != null && respo.length() > 0)
					? respo
							: "Failed to parse response";
		}
	}
	
	private static final SimpleBindings bindings = new SimpleBindings();
	static{
		
	}
	
	private static String parseResponse(String response)
			throws ScriptException, NullPointerException{
		if(response.matches("\\s*"))
			return "";
		if(containsRegex("\"error\"\\s*:\\s*true", response))
			// if(response.contains("\"error\" : true"))
			return "I encountered an error while processing your request.";
		if(containsRegex("\"success\"\\s*:\\s*false", response))
			// if(response.contains("\"success\" : false"))
			return "I do not understand.";
		String jscmd = "var regex = /.*=image\\/([^&]*).*/g;\n"
				+ "var httpsregex = /[^\\/]+\\/\\/.*/g;\n"
				+ "var htsec = \"https:\";\n"
				+ "var subst = \"&=.$1\";\n"
				+ "var results=" + response + ".queryresult;\n"
				+ "var pods=results.pods;\n"
				+ "var output = '';\n"
				+ "if(results.numpods!=0 && !!pods){"
				+ "	for(var i=0;i<pods.length;++i){\n"
				+ "		if(!(/^.*\\b[Ii]nput\\b.*$/gim).test(pods[i].title)){\n"
				+ "			var sbpd=pods[i].subpods[0];\n"
				+ "			var src = sbpd.img.src;\n"
				+ "			if(sbpd.plaintext!=\"\"){\n"
				+ "				output = sbpd.plaintext;\n"
				+ "			}else{\n"
				+ "				var msg = (src.match(regex) ? src.replace(regex, src+subst) : src);\n"
				+ "				output = (msg.match(httpsregex) ? msg : htsec+msg);\n"
				+ "			}\n"
				+ "			break;\n"
				+ "		}\n"
				+ "	}\n"
				+ "}\n"
				+ "output";
		String jscmd2 = "var assum = results.assumptions;\n"
				+ "var output = '';\n"
				+ "if(assum){\n"
				+ "	var f = (function(assum,frst){\n"
				+ "		output='Assuming '+((frst&&assum.word)?('\"'+assum.word+'\" is a '+assum.values[0].desc):assum.values[0].desc)+'. Can also be ';\n"
				+ "		var last=assum.values[assum.values.length-1];\n"
				+ "		if(assum.values.length==2){\n"
				+ "			output+=last.desc;\n" + "	}else{\n"
				+ "			for(var i=1;i<assum.values.length-1;++i){\n"
				+ "				var v = assum.values[i];\n"
				+ "				output+=v.desc+', ';\n"
				+ "			}\n"
				+ "			output+='or '+last.desc;\n"
				+ "		}\n"
				+ "		return output;\n"
				+ "	});\n"
				+ "	var outputmain;\n"
				+ "	if(assum[0]){\n"
				+ "		outputmain='';\n"
				+ "		outputmain+=f(assum[0],true)+'\\n';\n"
				+ "		for(var i=1;i<assum.length;++i)\n"
				+ "			outputmain+=f(assum[i],false)+'\\n';\n"
				+ "	}else\n"
				+ "	outputmain=f(assum)+'\\n';\n"
				+ "	outputmain=outputmain.substring(0,outputmain.length-1);\n"
				+ "	output=outputmain\n"
				+ "}\n"
				+ "output";
		javax.script.ScriptEngine engine = new javax.script.ScriptEngineManager()
				.getEngineByName("js");
		engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
		Object r = engine.eval(jscmd);
		String result = (r == null ? "" : r.toString());
		Object a = engine.eval(jscmd2);
		String assumptions = (a == null ? "" : a.toString());
		System.out.println(assumptions);
		//TODO: maybe actually do something with the assumptions?
		return result;
	}
	
	public static Properties loadProperties(String filename) throws IOException{
		Properties props = new Properties();
		FileReader reader = new FileReader(filename);
		props.load(reader);
		reader.close();
		return props;
	}
	
	public static boolean containsRegex(String regex, String in){
		return Pattern.compile(regex).matcher(in).find();
	}
	
	/**
	 * 
	 * @param regex
	 * @param in
	 * @return The match in <code>in</code> for the first group in
	 * <code>regex</code>.
	 * @throws IllegalArgumentException If no match was found or if there is no
	 * capturing group in the regex
	 */
	public static String search(String regex, String in){
		try{
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(in);
			m.find();
			return m.group(1);
		}
		catch(Exception e){
			System.err.println("Warning: Failed to find \""
					+ regex.replaceAll("[\"\\\\]", "\\\\$0") + "\" in \""
					+ in.replaceAll("[\"\\\\]", "\\\\$0") + "\"");
			throw new IllegalArgumentException(e);
		}
	}
	private static HashMap<String, Matcher> jsonmatchers = new HashMap<>();
	private static String searchJSON(String regex, String input){
		Matcher m;
		if(!jsonmatchers.containsKey(regex))
			jsonmatchers.put(regex, m = Pattern.compile(regex).matcher(input));
		else{
			m = jsonmatchers.get(regex);
			m.reset(input);
		}
		m.find();
		return m.group(1);
	}
	
	public static String getStringValueJSON(String parname, String rawjson){
		String match = "";
		char QUOTE = '\"';
		int startindex = rawjson.indexOf(QUOTE + parname + QUOTE + ':');
		if(startindex < 0)
			try{// TODO there must be a better regex...
				return searchJSON(
						"\"" + parname
						+ "\"\\s*:\\s*\"(([^\\\\\"]*(\\\\.)?)*)\"",
						rawjson);
			}
		catch(Exception e){
			try{
				return searchJSON(
						"\"" + parname
						+ "\"\\\\s*:\\\\s*\'(([^\\\\\']*(\\\\.)?)*)\'",
						rawjson);
			}
			catch(Exception e2){
				try{
					return searchJSON(
							"\'" + parname
							+ "\'\\\\s*:\\\\s*\"(([^\\\\\"]*(\\\\.)?)*)\"",
							rawjson);
				}
				catch(Exception e3){
					try{
						return searchJSON(
								"\'" + parname
								+ "\'\\\\s*:\\\\s*\'(([^\\\\\']*(\\\\.)?)*)\'",
								rawjson);
					}
					catch(Exception e4){
						return "";
					}
				}
			}
		}
		int index = startindex + parname.length() + 3;
		boolean escapenext = false;
		char nextchar = rawjson.charAt(++index);
		try{
			while(nextchar != QUOTE || escapenext){
				escapenext = false;
				match += nextchar;
				if(nextchar == '\\' && !escapenext)
					escapenext = true;
				nextchar = rawjson.charAt(++index);
			}
		}
		catch(Exception e){
			System.err.println("Partial: " + match);
			System.err.println("While looking for \"" + parname + "\"");
			System.err.println("Full text: %%STX%%" + rawjson + "%%ETX%%\n");
			throw(e);
		}
		return match;
	}
	
	public static String urldecode(String text){
		try{
			return URLDecoder.decode(text, "UTF-8");
		}
		catch(UnsupportedEncodingException e){
			throw new InternalError(e);
		}
	}
	public static String urlencode(String text){
		try{
			return URLEncoder.encode(text, "UTF-8");
		}
		catch(UnsupportedEncodingException e){
			throw new InternalError(e);
		}
	}
	public static String urlencode(String[] singlemap){
		return urlencode(new String[][]{singlemap});
	}
	public static String urlencode(String[][] map){
		try{
			String encoded = "";
			for(int i = 0; i < map.length; ++i){
				String[] parmap = map[i];
				if(parmap.length != 2)
					throw new IllegalArgumentException(
							"Invalid parameter mapping "
									+ java.util.Arrays.deepToString(parmap));
				encoded += parmap[0] + "="
						+ URLEncoder.encode(parmap[1], "UTF-8")
						+ (i + 1 < map.length ? "&" : "");
			}
			return encoded;
		}
		catch(UnsupportedEncodingException e){
			throw new InternalError(e);
		}
	}
	private static Matcher unicodematcher = Pattern
			.compile("\\\\u([A-Za-z0-9]{4})").matcher("");
	private static String replaceUnicodeEscapes(String text){
		unicodematcher.reset(text);
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		while(unicodematcher.find()){
			String match = unicodematcher.group(1);
			text = text.replaceAll("\\\\u" + match,
					"" + ((char)Integer.parseUnsignedInt(match, 16)));
			unicodematcher.reset(text);
		}
		Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
		return text;
	}
	private static Matcher htmlescapematcher = Pattern
			.compile("\\&\\#(x?[A-Fa-f0-9]+);").matcher("");
	private static String replaceHtmlEscapes(String text){
		htmlescapematcher.reset(text);
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		while(htmlescapematcher.find()){
			String match = htmlescapematcher.group(1);
			text = text.replaceAll("&#" + match + ";", "" + ((char)Integer
					.parseUnsignedInt(match, match.startsWith("x")
							? 16
									: 10)));
		}
		Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
		return text;
	}
	public static String replaceAllAll(String target, String[][] rr){
		for(String[] r : rr)
			target = target.replaceAll(r[0], r[1]);
		return target;
	}
	/**
	 * @see <a href=
	 * "https://en.wikipedia.org/wiki/List_of_XML_and_HTML_character_entity_references#Character_entity_references_in_HTML">
	 * List of XML and HTML character entity references - Wikipedia</a>
	 */
	private static final String[][] replacementRegexes = {
			{"[\\*_\\\u0060()\\[\\]]", "$0"}, {"</?b>", "**"},
			{"</?strong>", "**"}, {"</?i>", "*"}, {"</?em>", "*"},
			{"</?strike>", "---"}, // Note: The <strike> tag is not supported in
			// HTML5
			{"</?del>", "---"}, {"</?s>", "---"}, {"\\\\\"", "\""},
			{"\\\\\'", "\'"}, {"<\\/?code>", "\u0060"},
			{"<\\s*br\\s*>", "\u0060"},};
	public static String unescapeHtml(String text){
		if(text == null)
			return "";
		text = replaceAllAll(replaceAllAll(
				replaceHtmlEscapes(replaceUnicodeEscapes(
						text.replaceAll("&zwnj;&#8203;", ""))),
				replacementRegexes), htmlCharacterEntityReferences);
		return text;
	}
	
	private static final SimpleDateFormat dtf = new SimpleDateFormat(
			"yyyy-MM-dd_HH:mm:ss.SSS");
	private static final SimpleDateFormat dtf_iso8601 = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm'Z'");
	private static final SimpleDateFormat dtf_rfc5322 = new SimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss 'GMT'");
	static{
		// new java.util.SimpleTimeZone(rawOffset, ID, startMonth, startDay,
		// startDayOfWeek, startTime, startTimeMode, endMonth, endDay,
		// endDayOfWeek, endTime, endTimeMode, dstSavings)
		dtf.setTimeZone(TimeZone.getDefault());
		dtf_rfc5322.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
	public static String getDateTime(){
		return dtf.format(System.currentTimeMillis());
	}
	public static String getDateTime_RFC_5322(){
		return getDateTime_RFC_5322(System.currentTimeMillis());
	}
	public static String getDateTime_RFC_5322(long datetime){
		return dtf_rfc5322.format(datetime);
	}
	public static long parseDateTime_RFC_5322(String datetime)
			throws ParseException{
		return dtf_rfc5322.parse(datetime).getTime();
	}
	public static String getDateTime_ISO_8601(){
		return getDateTime_ISO_8601(TimeZone.getDefault());
	}
	public static String getDateTime_ISO_8601(String timezoneid){
		dtf_iso8601.setTimeZone(TimeZone.getTimeZone(timezoneid));
		return dtf_iso8601.format(System.currentTimeMillis());
	}
	public static String getDateTime_ISO_8601(TimeZone timezone){
		dtf_iso8601.setTimeZone(timezone);
		return dtf_iso8601.format(System.currentTimeMillis());
	}
	/*
	 * Defunct:
	 * http://www.dictionary.com/wordoftheday/wotd.rss
	 * 
	 * TODO add the ability for the user to choose which feed they want
	 * Viable:
	 * http://www.oed.com/rss/wordoftheday
	 * https://wordsmith.org/awad/rss1.xml
	 * http://www.wordthink.com/feed/
	 * https://www.netlingo.com/feed-wotd.rss
	 * http://www.macmillandictionaryblog.com/feed
	 * https://www.investopedia.com/feedbuilder/feed/getfeed/?feedName=rss_tod
	 * http://feeds.urbandictionary.com/UrbanWordOfTheDay
	 * 
	 * Abnormal:
	 * https://daily.wordreference.com/feed/
	 * 
	 * TODO check if this has been fixed yet.
	 * Words and definitions swapped as of October 29, 2018:
	 * https://feeds.feedburner.com/TodaysComputerWordOfTheDay?format=xml
	 * https://www.computerhope.com/rss/wotd.rss
	 */
	private static final String wotdFeedUrl = "http://www.oed.com/rss/wordoftheday";
	private static final Matcher wotdMatcher = Pattern.compile(
			"(?is)<item>.*?<title>(?<title>.*?)<\\/title>(?:.*?(?:<link>(?<link>.*?)<\\/link>|<description>(?<description>.*?)<\\/description>)){2}.*?<\\/item>",
			Pattern.DOTALL | Pattern.CASE_INSENSITIVE).matcher("");
	public static String getWotd(){
		try{
			String text = GET(wotdFeedUrl);
			wotdMatcher.reset(text);
			wotdMatcher.find();
			String output = "Today's Word of the Day is ["
					+ wotdMatcher.group("title") + "]("
					+ wotdMatcher.group("link") + "). \""
					+ wotdMatcher.group("description") + "\"";
			return output;
		}
		catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static String guessEncoding(byte[] bytes){
		String DEFAULT_ENCODING = "UTF-8";
		org.mozilla.universalchardet.UniversalDetector detector = new org.mozilla.universalchardet.UniversalDetector(
				null);
		detector.handleData(bytes, 0, bytes.length);
		detector.dataEnd();
		String encoding = detector.getDetectedCharset();
		detector.reset();
		if(encoding == null){
			encoding = DEFAULT_ENCODING;
		}
		return encoding;
	}
	public static String rawBytesToString(byte[] bytes){
		try{
			return new String(bytes, Utils.guessEncoding(bytes));
		}
		catch(UnsupportedEncodingException e){
			throw new InternalError(e);
		}
	}
	
	private static String[][] htmlCharacterEntityReferences = {
			//@formatter:off
			//And here is the long list of html character entity references
			//See https://en.wikipedia.org/wiki/List_of_XML_and_HTML_character_entity_references
			{"&quot;", "\""},
			{"&amp;", "&"},
			{"&apos;", "\'"},
			{"&lt;", "<"},
			{"&gt;", ">"},
			{"&nbsp;", "\u00A0"},
			{"&iexcl;", "\u00A1"},
			{"&cent;", "\u00A2"},
			{"&pound;", "\u00A3"},
			{"&curren;", "\u00A4"},
			{"&yen;", "\u00A5"},
			{"&brvbar;", "\u00A6"},
			{"&sect;", "\u00A7"},
			{"&uml;", "\u00A8"},
			{"&copy;", "\u00A9"},
			{"&ordf;", "\u00AA"},
			{"&laquo;", "\u00AB"},
			{"&not;", "\u00AC"},
			{"&shy;", "\u00AD"},
			{"&reg;", "\u00AE"},
			{"&macr;", "\u00AF"},
			{"&deg;", "\u00B0"},
			{"&plusmn;", "\u00B1"},
			{"&sup2;", "\u00B2"},
			{"&sup3;", "\u00B3"},
			{"&acute;", "\u00B4"},
			{"&micro;", "\u00B5"},
			{"&para;", "\u00B6"},
			{"&middot;", "\u00B7"},
			{"&cedil;", "\u00B8"},
			{"&sup1;", "\u00B9"},
			{"&ordm;", "\u00BA"},
			{"&raquo;", "\u00BB"},
			{"&frac14;", "\u00BC"},
			{"&frac12;", "\u00BD"},
			{"&frac34;", "\u00BE"},
			{"&iquest;", "\u00BF"},
			{"&Agrave;", "\u00C0"},
			{"&Aacute;", "\u00C1"},
			{"&Acirc;", "\u00C2"},
			{"&Atilde;", "\u00C3"},
			{"&Auml;", "\u00C4"},
			{"&Aring;", "\u00C5"},
			{"&AElig;", "\u00C6"},
			{"&Ccedil;", "\u00C7"},
			{"&Egrave;", "\u00C8"},
			{"&Eacute;", "\u00C9"},
			{"&Ecirc;", "\u00CA"},
			{"&Euml;", "\u00CB"},
			{"&Igrave;", "\u00CC"},
			{"&Iacute;", "\u00CD"},
			{"&Icirc;", "\u00CE"},
			{"&Iuml;", "\u00CF"},
			{"&ETH;", "\u00D0"},
			{"&Ntilde;", "\u00D1"},
			{"&Ograve;", "\u00D2"},
			{"&Oacute;", "\u00D3"},
			{"&Ocirc;", "\u00D4"},
			{"&Otilde;", "\u00D5"},
			{"&Ouml;", "\u00D6"},
			{"&times;", "\u00D7"},
			{"&Oslash;", "\u00D8"},
			{"&Ugrave;", "\u00D9"},
			{"&Uacute;", "\u00DA"},
			{"&Ucirc;", "\u00DB"},
			{"&Uuml;", "\u00DC"},
			{"&Yacute;", "\u00DD"},
			{"&THORN;", "\u00DE"},
			{"&szlig;", "\u00DF"},
			{"&agrave;", "\u00E0"},
			{"&aacute;", "\u00E1"},
			{"&acirc;", "\u00E2"},
			{"&atilde;", "\u00E3"},
			{"&auml;", "\u00E4"},
			{"&aring;", "\u00E5"},
			{"&aelig;", "\u00E6"},
			{"&ccedil;", "\u00E7"},
			{"&egrave;", "\u00E8"},
			{"&eacute;", "\u00E9"},
			{"&ecirc;", "\u00EA"},
			{"&euml;", "\u00EB"},
			{"&igrave;", "\u00EC"},
			{"&iacute;", "\u00ED"},
			{"&icirc;", "\u00EE"},
			{"&iuml;", "\u00EF"},
			{"&eth;", "\u00F0"},
			{"&ntilde;", "\u00F1"},
			{"&ograve;", "\u00F2"},
			{"&oacute;", "\u00F3"},
			{"&ocirc;", "\u00F4"},
			{"&otilde;", "\u00F5"},
			{"&ouml;", "\u00F6"},
			{"&divide;", "\u00F7"},
			{"&oslash;", "\u00F8"},
			{"&ugrave;", "\u00F9"},
			{"&uacute;", "\u00FA"},
			{"&ucirc;", "\u00FB"},
			{"&uuml;", "\u00FC"},
			{"&yacute;", "\u00FD"},
			{"&thorn;", "\u00FE"},
			{"&yuml;", "\u00FF"},
			{"&OElig;", "\u0152"},
			{"&oelig;", "\u0153"},
			{"&Scaron;", "\u0160"},
			{"&scaron;", "\u0161"},
			{"&Yuml;", "\u0178"},
			{"&fnof;", "\u0192"},
			{"&circ;", "\u02C6"},
			{"&tilde;", "\u02DC"},
			{"&Alpha;", "\u0391"},
			{"&Beta;", "\u0392"},
			{"&Gamma;", "\u0393"},
			{"&Delta;", "\u0394"},
			{"&Epsilon;", "\u0395"},
			{"&Zeta;", "\u0396"},
			{"&Eta;", "\u0397"},
			{"&Theta;", "\u0398"},
			{"&Iota;", "\u0399"},
			{"&Kappa;", "\u039A"},
			{"&Lambda;", "\u039B"},
			{"&Mu;", "\u039C"},
			{"&Nu;", "\u039D"},
			{"&Xi;", "\u039E"},
			{"&Omicron;", "\u039F"},
			{"&Pi;", "\u03A0"},
			{"&Rho;", "\u03A1"},
			{"&Sigma;", "\u03A3"},
			{"&Tau;", "\u03A4"},
			{"&Upsilon;", "\u03A5"},
			{"&Phi;", "\u03A6"},
			{"&Chi;", "\u03A7"},
			{"&Psi;", "\u03A8"},
			{"&Omega;", "\u03A9"},
			{"&alpha;", "\u03B1"},
			{"&beta;", "\u03B2"},
			{"&gamma;", "\u03B3"},
			{"&delta;", "\u03B4"},
			{"&epsilon;", "\u03B5"},
			{"&zeta;", "\u03B6"},
			{"&eta;", "\u03B7"},
			{"&theta;", "\u03B8"},
			{"&iota;", "\u03B9"},
			{"&kappa;", "\u03BA"},
			{"&lambda;", "\u03BB"},
			{"&mu;", "\u03BC"},
			{"&nu;", "\u03BD"},
			{"&xi;", "\u03BE"},
			{"&omicron;", "\u03BF"},
			{"&pi;", "\u03C0"},
			{"&rho;", "\u03C1"},
			{"&sigmaf;", "\u03C2"},
			{"&sigma;", "\u03C3"},
			{"&tau;", "\u03C4"},
			{"&upsilon;", "\u03C5"},
			{"&phi;", "\u03C6"},
			{"&chi;", "\u03C7"},
			{"&psi;", "\u03C8"},
			{"&omega;", "\u03C9"},
			{"&thetasym;", "\u03D1"},
			{"&upsih;", "\u03D2"},
			{"&piv;", "\u03D6"},
			{"&ensp;", "\u2002"},
			{"&emsp;", "\u2003"},
			{"&thinsp;", "\u2009"},
			{"&zwnj;", "\u200C"},
			{"&zwj;", "\u200D"},
			{"&lrm;", "\u200E"},
			{"&rlm;", "\u200F"},
			{"&ndash;", "\u2013"},
			{"&mdash;", "\u2014"},
			{"&lsquo;", "\u2018"},
			{"&rsquo;", "\u2019"},
			{"&sbquo;", "\u201A"},
			{"&ldquo;", "\u201C"},
			{"&rdquo;", "\u201D"},
			{"&bdquo;", "\u201E"},
			{"&dagger;", "\u2020"},
			{"&Dagger;", "\u2021"},
			{"&bull;", "\u2022"},
			{"&hellip;", "\u2026"},
			{"&permil;", "\u2030"},
			{"&prime;", "\u2032"},
			{"&Prime;", "\u2033"},
			{"&lsaquo;", "\u2039"},
			{"&rsaquo;", "\u203A"},
			{"&oline;", "\u203E"},
			{"&frasl;", "\u2044"},
			{"&euro;", "\u20AC"},
			{"&image;", "\u2111"},
			{"&weierp;", "\u2118"},
			{"&real;", "\u211C"},
			{"&trade;", "\u2122"},
			{"&alefsym;", "\u2135"},
			{"&larr;", "\u2190"},
			{"&uarr;", "\u2191"},
			{"&rarr;", "\u2192"},
			{"&darr;", "\u2193"},
			{"&harr;", "\u2194"},
			{"&crarr;", "\u21B5"},
			{"&lArr;", "\u21D0"},
			{"&uArr;", "\u21D1"},
			{"&rArr;", "\u21D2"},
			{"&dArr;", "\u21D3"},
			{"&hArr;", "\u21D4"},
			{"&forall;", "\u2200"},
			{"&part;", "\u2202"},
			{"&exist;", "\u2203"},
			{"&empty;", "\u2205"},
			{"&nabla;", "\u2207"},
			{"&isin;", "\u2208"},
			{"&notin;", "\u2209"},
			{"&ni;", "\u220B"},
			{"&prod;", "\u220F"},
			{"&sum;", "\u2211"},
			{"&minus;", "\u2212"},
			{"&lowast;", "\u2217"},
			{"&radic;", "\u221A"},
			{"&prop;", "\u221D"},
			{"&infin;", "\u221E"},
			{"&ang;", "\u2220"},
			{"&and;", "\u2227"},
			{"&or;", "\u2228"},
			{"&cap;", "\u2229"},
			{"&cup;", "\u222A"},
			{"&int;", "\u222B"},
			{"&there4;", "\u2234"},
			{"&sim;", "\u223C"},
			{"&cong;", "\u2245"},
			{"&asymp;", "\u2248"},
			{"&ne;", "\u2260"},
			{"&equiv;", "\u2261"},
			{"&le;", "\u2264"},
			{"&ge;", "\u2265"},
			{"&sub;", "\u2282"},
			{"&sup;", "\u2283"},
			{"&nsub;", "\u2284"},
			{"&sube;", "\u2286"},
			{"&supe;", "\u2287"},
			{"&oplus;", "\u2295"},
			{"&otimes;", "\u2297"},
			{"&perp;", "\u22A5"},
			{"&sdot;", "\u22C5"},
			{"&lceil;", "\u2308"},
			{"&rceil;", "\u2309"},
			{"&lfloor;", "\u230A"},
			{"&rfloor;", "\u230B"},
			{"&lang;", "\u2329"},
			{"&rang;", "\u232A"},
			{"&loz;", "\u25CA"},
			{"&spades;", "\u2660"},
			{"&clubs;", "\u2663"},
			{"&hearts;", "\u2665"},
			{"&diams;", "\u2666"}
			//@formatter:on
	};
	
	public static String escapeNonAscii(String string){
		char[] chs = string.toCharArray();
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < chs.length; ++i)
			builder.append(escapeNonAscii(chs[i]));
		// TODO Auto-generated method stub
		return builder.toString();
	}
	public static String escapeNonAscii(char ch){
		if(ch < ' ' || ch > '~'){
			String hex = Integer.toHexString(ch);
			if(hex.length() > 4)
				throw new InternalError("Character is too large!");
			while(hex.length() < 4)
				hex = '0' + hex;
			return "\\u" + hex;
		}
		return Character.toString(ch);
	}
	public static String unescapeNonAscii(String string){
		Pattern p = Pattern.compile("\\\\u[A-Za-z0-9]{4}");
		Matcher m = p.matcher(string);
		while(m.find()){
			String match = m.group();
			string = string.replace(match, Character
					.toString((char)Integer.parseInt(match.substring(2), 16)));
			m.reset(string);
		}
		return string;
	}
	public static String[] unescapeNonAscii(String[] strings){
		String[] newstrings = new String[strings.length];
		for(int i = 0; i < strings.length; ++i)
			newstrings[i] = unescapeNonAscii(strings[i]);
		return newstrings;
	}
	public static String[][] unescapeNonAscii(String[][] strings){
		String[][] newstrings = new String[strings.length][];
		for(int i = 0; i < strings.length; ++i)
			newstrings[i] = unescapeNonAscii(strings[i]);
		return newstrings;
	}
	
	private static final char[] safeasciichars = {'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9'};
	public static String generateRandomAsciiString(int length){
		Random r = new Random();
		char[] chs = new char[length];
		for(int i = 0; i < length; ++i)
			chs[i] = safeasciichars[r.nextInt(safeasciichars.length)];
		return new String(chs);
	}
	public static <T> T[] deepCopyArray(T[] original){
		return deepCopyArray0(original);
	}
	@SuppressWarnings("unchecked")
	private static <T> T[] deepCopyArray0(T[] original){
		T[] copy = (T[])Array.newInstance(
				original.getClass().getComponentType(), original.length);
		for(int i = 0; i < copy.length; ++i){
			if(original[i] == null)
				copy[i] = null;
			else{
				Class<?> ecls = original[i].getClass();
				if(ecls.isArray()){
					T subarraycopy;
					if(ecls == byte[].class)
						subarraycopy = (T)deepCopyArray((byte[])original[i]);
					else if(ecls == short[].class)
						subarraycopy = (T)deepCopyArray((short[])original[i]);
					else if(ecls == int[].class)
						subarraycopy = (T)deepCopyArray((int[])original[i]);
					else if(ecls == long[].class)
						subarraycopy = (T)deepCopyArray((long[])original[i]);
					else if(ecls == char[].class)
						subarraycopy = (T)deepCopyArray((char[])original[i]);
					else if(ecls == float[].class)
						subarraycopy = (T)deepCopyArray((float[])original[i]);
					else if(ecls == double[].class)
						subarraycopy = (T)deepCopyArray((double[])original[i]);
					else if(ecls == boolean[].class)
						subarraycopy = (T)deepCopyArray((boolean[])original[i]);
					else
						subarraycopy = (T)deepCopyArray((Object[])original[i]);
					copy[i] = subarraycopy;
				}
				else
					copy[i] = original[i];
			}
		}
		return copy;
	}
	public static byte[] deepCopyArray(byte[] original){
		return Arrays.copyOf(original, original.length);
	}
	public static short[] deepCopyArray(short[] original){
		return Arrays.copyOf(original, original.length);
	}
	public static int[] deepCopyArray(int[] original){
		return Arrays.copyOf(original, original.length);
	}
	public static long[] deepCopyArray(long[] original){
		return Arrays.copyOf(original, original.length);
	}
	public static char[] deepCopyArray(char[] original){
		return Arrays.copyOf(original, original.length);
	}
	public static float[] deepCopyArray(float[] original){
		return Arrays.copyOf(original, original.length);
	}
	public static double[] deepCopyArray(double[] original){
		return Arrays.copyOf(original, original.length);
	}
	public static boolean[] deepCopyArray(boolean[] original){
		return Arrays.copyOf(original, original.length);
	}
	
	public static String escapeChar(char ch){
		switch(ch){
			//@formatter:off
			case '\r': return "\\r";
			case '\f': return "\\r";
			case '\"': return "\\\"";
			case '\'': return "\\\'";
			case '\\': return "\\\\";
			case '\n': return "\\n";
			case '\t': return "\\t";
			case '\b': return "\\b";
			//@formatter:on
			
			default:
				if(ch < ' ' || ch > '~'){
					
					String hex = Integer.toHexString(ch);
					if(hex.length() > 4)
						throw new InternalError("Character is too large!");
					while(hex.length() < 4)
						hex = '0' + hex;
					return "\\u" + hex;
				}
		}
		return Character.toString(ch);
	}
	
	public static String escapeString(String string){
		char[] chs = string.toCharArray();
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < chs.length; ++i)
			builder.append(escapeChar(chs[i]));
		// TODO Auto-generated method stub
		return builder.toString();
	}
	
	public static boolean getBooleanValueJSON(String parname, String rawjson){
		try{
			return Boolean.parseBoolean(searchJSON(
					"\"" + parname + "\":(?i)(true|false|null)", rawjson));
		}
		catch(Exception e){
			try{
				return Boolean.parseBoolean(searchJSON(
						"\'" + parname + "\':(?i)(true|false|null)", rawjson));
			}
			catch(Exception e2){
				String s = getStringValueJSON(parname, rawjson);
				return s.isEmpty() ? false : Boolean.parseBoolean(s);
			}
		}
	}
	
	public static long getNumValueJSON(String parname, String rawjson){
		try{
			return Long.parseLong(
					searchJSON("\"" + parname + "\":(\\d*)", rawjson));
		}
		catch(Exception e){
			try{
				return Long.parseLong(
						searchJSON("\'" + parname + "\':(\\d*)", rawjson));
			}
			catch(Exception e2){
				// String s = getStringValueJSON(parname, rawjson);
				return 0;
			}
		}
	}
	
	public static long getUnixTimeMillis(){
		return System.currentTimeMillis();
	}
	
	public static Long[] parseLongs(String str){
		String[] strs = str.split("[, ;]");
		Long[] vals = new Long[strs.length];
		for(int i = 0; i < strs.length; ++i)
			vals[i] = new Long(strs[i]);
		return vals;
	}
	
	public static <T> T randomElement(T[] arr){
		return arr[rand.nextInt(arr.length)];
	}
	
	public static String search(String regex, String in, boolean showWarnings){
		try{
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(in);
			m.find();
			return m.group(1);
		}
		catch(Exception e){
			if(showWarnings)
				System.err.println("Warning: Failed to find \""
						+ regex.replaceAll("[\"\\\\]", "\\\\$0") + "\" in \""
						+ in.replaceAll("[\"\\\\]", "\\\\$0") + "\"");
			throw new IllegalArgumentException(e);
		}
	}
}
