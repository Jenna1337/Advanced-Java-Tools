package utils.eval;

public enum EvalResultStatus{
	SUCCESSFUL(""),
	ERROR("The server returned an error while processing your request."),
	FUTURE_TOPIC("This topic is still being researched."),
	UNSUCCESSFUL("The server was not able to process your request.");
	private final String msg;
	EvalResultStatus(String msg){
		this.msg = msg;
	}
	public String toString(){
		return msg;
	}
}
