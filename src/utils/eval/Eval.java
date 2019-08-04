package utils.eval;

import static utils.WebRequest.GET;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import utils.WebRequest;
import static utils.Utils.*;

public class Eval
{
	private static volatile int evalRecalcCount = 0;
	private static final int evalRecalcCountMax = 1;
	@Deprecated
	public static synchronized String eval(String query){
		return eval(query, false);
	}
	@Deprecated
	public static synchronized String eval(String query, boolean forceImage){
		EvalResult result = eval2(query);
		return forceImage ? result.getResultImage() : result.getResultDefault();
	}
	public static synchronized EvalResult eval2(String query){
		query = query.trim();
		if(query.equalsIgnoreCase("alive"))
			return new BasicTextResult("Cogito ergo sum");
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
							+ "?assumptionsversion=2" + "&async=false" + "&banners=raw"
							+ "&debuggingdata=false" + "&format=image,plaintext"
							+ "&formattimeout=16" + "&input=" + input
							+ "&output=JSON" + "&parsetimeout=10"
							+ "&podinfosasync=false"
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
			return new BasicTextResult("Failed to connect to server");
		}
		catch(IOException e){
			e.printStackTrace();
			return new BasicTextResult("I do not understand.");
		}
		catch(ScriptException | NullPointerException e){
			EvalResult respo = null;
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
			String rr = respo!=null ? respo.getResultDefault() : "";
			return (rr != null && rr.length() > 0)
					? respo
							: new BasicTextResult("Failed to parse response");
		}
	}
	
	private static final SimpleBindings eval_bindings = new SimpleBindings();
	private static volatile int evalReinterpretationCount = 0;
	private static final    int evalReinterpretationCountMax = 1;
	private static EvalResult parseResponse(String response)
			throws ScriptException, NullPointerException{
		if(response.matches("\\s*"))
			return new EvalResult(EvalResultStatus.INVALIDDATA, "", "", "", "", "");
		String jscmd_eval_get_results_status = ""
				+ "var results=(" + response + ").queryresult;\n"
				+ "var output = '';\n"
				+ "if(results.error){\n"
				+ "	output='"+EvalResultStatus.ERROR+"';\n"
				+ "}\n"
				+ "else if(results.datatypes=='FutureTopic' || results.futuretopic){\n"
				+ "	output='"+EvalResultStatus.FUTURE_TOPIC+"';\n"
				+ "}\n"
				+ "else if(!results.success){\n"
				+ "	output='"+EvalResultStatus.UNSUCCESSFUL+"';\n"
				+ "}\n"
				+ "output";
		String jscmd_eval_get_results_img = ""
				+ "var output = '';\n"
				+ "	var pods=results.pods;\n"
				+ "if(results.numpods!=0 && !!pods){\n"
				+ "	for(var i=0;i<pods.length;++i){\n"
				+ "		if(!(/^.*\\b[Ii]nput\\b.*$/gim).test(pods[i].title)){\n"
				+ "			var sbpd=pods[i].subpods[0];\n"
				+ "			output = sbpd.img.src;\n"
				+ "			break;\n"
				+ "		}\n"
				+ "	}\n"
				+ "}\n"
				+ "output";
		String jscmd_eval_get_results_txt = ""
				+ "var output = '';\n"
				+ "var pods=results.pods;\n"
				+ "if(pods && results.numpods && results.numpods!=0){\n"
				+ "	for(var i=0;i<pods.length;++i){\n"
				+ "		if(!(/^.*\\b[Ii]nput\\b.*$/gim).test(pods[i].title)){\n"
				+ "			var sbpd=pods[i].subpods[0];\n"
				+ "			output = sbpd.plaintext;\n"
				+ "			break;\n"
				+ "		}\n"
				+ "	}\n"
				+ "}\n"
				+ "output";
		String jscmd_eval_get_didyoumeans = "var didyoumeans = results.didyoumeans;\n"
				+ "var output = '';\n"
				+ "if(didyoumeans && didyoumeans.length>0){\n"
				+ "	output = didyoumeans[0].val;\n"
				+ "}\n"
				+ "output";
		String jscmd_eval_get_warnings = "var warns = results.warnings;\n"
				+ "var output = '';\n"
				+ "if(warns){\n"
				+ "	output = warns.text;\n"
				+ "}\n"
				+ "output";
		String jscmd_eval_get_assum = "var assum = results.assumptions;\n"
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
		engine.setBindings(eval_bindings, ScriptContext.ENGINE_SCOPE);
		String r_status_string = nullCheckStringCast(engine.eval(jscmd_eval_get_results_status));
		EvalResultStatus r_status = r_status_string.length()>0 ? EvalResultStatus.valueOf(r_status_string) : EvalResultStatus.SUCCESSFUL;
		String r_img = nullCheckStringCast(engine.eval(jscmd_eval_get_results_img));
		String r_txt = nullCheckStringCast(engine.eval(jscmd_eval_get_results_txt));
		
		String warnings = nullCheckStringCast(engine.eval(jscmd_eval_get_warnings));
		System.out.println(warnings);
		//TODO: maybe actually do something with the warnings?
		
		String didyoumeans = nullCheckStringCast(engine.eval(jscmd_eval_get_didyoumeans));
		
		String assumptions = nullCheckStringCast(engine.eval(jscmd_eval_get_assum));
		System.out.println(assumptions);
		//TODO: maybe actually do something with the assumptions?
		
		EvalResult result = new EvalResult(r_status, r_img, r_txt, warnings, didyoumeans, assumptions);
		
		if(r_status.equals(EvalResultStatus.UNSUCCESSFUL) && didyoumeans!=null && didyoumeans.length()>0
				&& evalReinterpretationCount <= evalReinterpretationCountMax){
			System.out.println("Interpreting as: "+didyoumeans);
			evalReinterpretationCount++;
			try{
				return eval2(didyoumeans);
			}
			catch(Exception e){
				System.err.println(response);
				e.printStackTrace();
			}
			evalReinterpretationCount--;
		}
		
		
		return result;
	}
	private static String nullCheckStringCast(Object str){
		return (str == null ? null : (String)str);
	}
	
}
