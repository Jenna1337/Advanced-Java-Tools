package utils.json;

public class MalformedJSONException extends Exception
{
	public MalformedJSONException(int i, String message){
		super(message + " at character " + (i+1) + " of the JSON data");
	}
}
