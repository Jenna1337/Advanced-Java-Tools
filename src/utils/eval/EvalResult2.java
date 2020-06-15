package utils.eval;

import utils.WebRequest;
import utils.json.JSON;
import utils.json.JavaScriptObject;
import utils.json.MalformedJSONException;

public class EvalResult2{
	private final EvalResultStatus resultStatus;
	//TODO
	private /*final*/ String resultImage, resultText, warnings, didyoumeans, assumptions;
	EvalResult2(String json) throws MalformedJSONException{
		JavaScriptObject data=JSON.parse(json);
		JavaScriptObject results = data.get("queryresult");
		boolean error = results.get("error");
		boolean success = results.get("success");
		boolean isFutureTopic = results.containsKey("futuretopic") || "FutureTopic".equals(results.get("datatypes"));
		
		if(error){
			this.resultStatus = EvalResultStatus.ERROR;
		}
		else if(isFutureTopic){
			this.resultStatus = EvalResultStatus.FUTURE_TOPIC;
		}
		else if(success){
			this.resultStatus = EvalResultStatus.SUCCESSFUL;
		}
		else{
			this.resultStatus = EvalResultStatus.UNSUCCESSFUL;
		}
		
		if(results.containsKey("pods") && results.containsKey("numpods")){
			int numpods = results.get("numpods");
			if(numpods!=0){
				JavaScriptObject[] pods = results.get("pods");
				//TODO
			}
		}
		
		/*
		String jscmd_eval_get_results_img = ""
				+ "var output = '';\n"
				+ "	var pods=results.pods;\n"
				+ "if(results.numpods!=0 && !!pods){\n"
				+ "	for(var i=0;i<pods.length;++i){\n"
				+ "		if(!(/^.*\\b[Ii]nput\\b.*$/gim).test(pods[i].title)){\n"
				+ "			var sbpd=pods[i].subpods[0];\n"
				+ "			output = sbpd.img.src;\n"
				+ "			output = sbpd.plaintext;\n"
				+ "			break;\n"
				+ "		}\n"
				+ "	}\n"
				+ "}\n"
				+ "output";
		String r_img = engine.eval(jscmd_eval_get_results_img);
		this.resultImage = standardizeImage(r_img);
		
		String jscmd_eval_get_warnings = "var warns = results.warnings;\n"
				+ "var output = '';\n"
				+ "if(warns){\n"
				+ "	output = warns.text;\n"
				+ "}\n"
				+ "output";
		String warnings = engine.eval(jscmd_eval_get_warnings);
		this.warnings = warnings;
		System.out.println(warnings);
		
		String jscmd_eval_get_didyoumeans = "var didyoumeans = results.didyoumeans;\n"
				+ "var output = '';\n"
				+ "if(didyoumeans && didyoumeans.length>0){\n"
				+ "	output = didyoumeans[0].val;\n"
				+ "}\n"
				+ "output";
		String didyoumeans = engine.eval(jscmd_eval_get_didyoumeans);
		this.didyoumeans = didyoumeans;
		
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
		String assumptions = engine.eval(jscmd_eval_get_assum);
		this.assumptions = assumptions;
		System.out.println(assumptions);
		*/
	}
	private String standardizeImage(String r_img){
		if(r_img.isEmpty() || r_img.matches(".+\\.(?:jpe?g|png|gif|bmp|svg)$"))
			return r_img;
		try{
			return r_img + (r_img.contains("?") ? '&' : '?') + '.'
					+ WebRequest.HEAD(r_img).get("Content-Type")
					.get(0).replaceAll(".*\\/|;.*", "");
		}
		catch(Exception e){
			e.printStackTrace();
			return r_img;
		}
	}
	
	public EvalResultStatus getResultStatus(){
		return resultStatus;
	}
	public String getResultImage(){
		return resultImage;
	}
	public String getResultText(){
		return resultText;
	}
	public String getWarnings(){
		return warnings;
	}
	public String getDidyoumeans(){
		return didyoumeans;
	}
	public String getAssumptions(){
		return assumptions;
	}
	public String getResultDefault(){
		return resultStatus.equals(EvalResultStatus.SUCCESSFUL) ? (resultText.isEmpty() ? resultImage : resultText) : resultStatus.getMsg();
	}
}
