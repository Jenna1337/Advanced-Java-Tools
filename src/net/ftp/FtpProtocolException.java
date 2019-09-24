package net.ftp;

public class FtpProtocolException extends Exception
{
	private final FtpReplyCode ftpReplyCode;
	
	public FtpProtocolException(String message, FtpReplyCode ftpReplyCode){
		super(message);
		this.ftpReplyCode = ftpReplyCode;
	}
	public FtpReplyCode getReplyCode(){
		return ftpReplyCode;
	}
}
