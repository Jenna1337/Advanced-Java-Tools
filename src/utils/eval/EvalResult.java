package utils.eval;

import utils.WebRequest;

public class EvalResult{
	private final EvalResultStatus resultStatus;
	private final String resultImage, resultText, warnings, didyoumeans, assumptions;
	public EvalResult(EvalResultStatus r_status, String r_img, String r_txt,
			String warnings, String didyoumeans, String assumptions){
		this.resultStatus = r_status;
		this.resultImage = standardizeImage(r_img);
		this.resultText = r_txt;
		this.warnings = warnings;
		this.didyoumeans = didyoumeans;
		this.assumptions = assumptions;
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
