package utils;


public class MalformedEscapedStringException extends Exception
{
	private final int badCharIndex;
	public MalformedEscapedStringException(int i, String message){
		super(message);
		this.badCharIndex = i;
	}
	public int getBadCharIndex(){
		return badCharIndex;
	}
}
