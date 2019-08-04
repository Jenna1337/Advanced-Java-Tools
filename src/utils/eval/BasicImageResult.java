package utils.eval;

class BasicImageResult extends EvalResult{
	public BasicImageResult(String r_img){
		super(EvalResultStatus.SUCCESSFUL, r_img, "", "", "", "");
	}
}
