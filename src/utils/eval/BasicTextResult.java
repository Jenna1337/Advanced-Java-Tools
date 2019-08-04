package utils.eval;

class BasicTextResult extends EvalResult{
	public BasicTextResult(String r_txt){
		super(EvalResultStatus.SUCCESSFUL, "", r_txt, "", "", "");
	}
}
