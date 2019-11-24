package utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
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
import utils.eval.*;
import static utils.WebRequest.GET;
import static utils.HTMLCharacterEntities.htmlCharacterEntityReferences;

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
	public static EvalResult eval(String query){
		return Eval.eval(query);
	}
	
	public static Properties loadProperties(String filename) throws IOException{
		Properties props = new Properties();
		FileReader reader = new FileReader(filename);
		props.load(reader);
		reader.close();
		return props;
	}
	@Deprecated
	public static void forceKillThread(Thread thread) throws InterruptedException{
			Thread.State s = thread.getState();
			switch(s)
			{
				case NEW:
					throw new InternalError("Thread not started.");
				case BLOCKED:
				case RUNNABLE:
				case TIMED_WAITING:
				case WAITING:
					//Interrupt the thread
					thread.interrupt();
					Thread.sleep(1000);
					if(thread.isAlive()){
						//Kill thread
						thread.stop();
					}
					break;
				case TERMINATED:
					break;
				default:
					throw new InternalError("Unknown thread state: " + s.name());
			}
	}
	/**
	 * Moved to {@linkplain StringUtils#containsRegex(String, String)}.
	 */
	@Deprecated
	public static boolean containsRegex(String regex, String in){
		return Pattern.compile(regex).matcher(in).find();
	}
	
	/**
	 * Moved to {@linkplain StringUtils#search(String, String)}.
	 * 
	 * @param regex
	 * @param in
	 * @return The match in <code>in</code> for the first group in
	 * <code>regex</code>.
	 * @throws IllegalArgumentException If no match was found or if there is no
	 * capturing group in the regex
	 */
	@Deprecated
	public static String search(String regex, String in){
		return StringUtils.search(regex, in, true);
	}
	/**
	 * Moved to {@linkplain StringUtils#search(String, String, boolean)}.
	 * 
	 * @param regex
	 * @param in
	 * @param showWarnings
	 * @return The match in <code>in</code> for the first group in
	 * <code>regex</code>.
	 * @throws IllegalArgumentException If no match was found or if there is no
	 * capturing group in the regex
	 */
	@Deprecated
	public static String search(String regex, String in, boolean showWarnings){
		return StringUtils.search(regex, in, showWarnings);
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
	/**
	 * Moved to {@link StringUtils#replaceAllAll(String, String[][])}
	 */
	@Deprecated
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
	private static final String wotdFeedUrl = "https://www.oed.com/rss/wordoftheday";
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
		catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Could not get word of the day.";
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
	
	public static String escapeNonAscii(String string){
		char[] chs = string.toCharArray();
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < chs.length; ++i)
			builder.append(escapeNonAscii(chs[i]));
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
	/**
	 * Unescapes all <code>&bsol;u</code> characters.
	 * @param string
	 * @return The resulting String.
	 */
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
	/**
	 * Unescapes all <code>&bsol;u</code> characters from all {@code strings}.
	 * @param strings The strings to unescape.
	 * @return The resulting array.
	 */
	public static String[] unescapeNonAscii(String[] strings){
		String[] newstrings = new String[strings.length];
		for(int i = 0; i < strings.length; ++i)
			newstrings[i] = unescapeNonAscii(strings[i]);
		return newstrings;
	}
	/**
	 * Unescapes all <code>&bsol;u</code> characters from all {@code strings}.
	 * @param strings The strings to unescape.
	 * @return The resulting array.
	 */
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
			case '\f': return "\\f";
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
		return builder.toString();
	}
	public static String unescapeString(String string) throws MalformedEscapedStringException{
		char[] chs = string.toCharArray();
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < chs.length; ++i){
			char ch=chs[i];
			if(ch=='\"' || Character.isISOControl(ch))
				;//TODO
			if(ch=='\\'){
				if(chs.length <= i+1)
					throw new MalformedEscapedStringException(i, "Unterminated string");
					
				++i;
				char ch0 = chs[i];
				switch(ch0){
					case '\"':
					case '\\':
					case '/':
						builder.append(ch0);
						break;
					case 'b':
						builder.append('\b');
						break;
					case 'f':
						builder.append('\f');
						break;
					case 'n':
						builder.append('\n');
						break;
					case 'r':
						builder.append('\r');
						break;
					case 't':
						builder.append('\t');
						break;
					case 'u':{
						if(chs.length <= i+4)
							throw new MalformedEscapedStringException(i, "Bad Unicode escape");
						char[] chardata = new char[6];
						System.arraycopy(chs, i-1, chardata, 0, 6);
						try{
							String nch = unescapeNonAscii(new String(chardata));
							builder.append(nch);
						}catch(NumberFormatException nfe){
							throw new MalformedEscapedStringException(i, "Bad Unicode escape");
						}
						i+=4;
						break;
					}
					default:
						throw new MalformedEscapedStringException(i, "Bad escaped character");
				}
				
			}
			else
				builder.append(ch);
		}
		return builder.toString();
	}
	
	/**
	 * Use {@link utils.json.JSON} instead.
	 */
	@Deprecated
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
	
	/**
	 * Use {@link utils.json.JSON} instead.
	 */
	@Deprecated
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
	
	@SuppressWarnings("unchecked")
	public static <T> T castToOriginalType(Object obj){
		return obj == null ? null : (T)obj.getClass().cast(obj);
	}
}
