package sys;


import java.io.IOException;

public class PrintStream extends java.io.PrintStream
{
	public PrintStream(java.io.PrintStream out)
	{
		super(out);
	}
	//	@Override
	//	public void print(String s)
	//	{
	//		
	//	}
	@Override
	public void write(byte[] buf, int off, int len)
	{
		SystemLog(java.util.Arrays.copyOf(buf, len));
		super.write(buf, off, len);
	}
	@Override
	public void write(byte[] b) throws IOException
	{
		SystemLog(b);
		super.write(b);
	}
	@Override
	public void write(int b)
	{
		SystemLog(new byte[]{(byte)b});
		super.write(b);
	}
	@Deprecated
	private void SystemLog(byte[] ba)
	{
		if(!System.logging)
			return;
		String o="";
		for(int i=0; i<ba.length; ++i)
		{
			byte cur = ba[i];
			if((!Character.isDefined(cur) || Character.isWhitespace(cur) ||
					!Character.UnicodeBlock.of(cur).equals(Character.UnicodeBlock.BASIC_LATIN))
					&& cur!=' ')
			{
				switch(cur)
				{
					// \"  \'  \\ 
					case '\b': o+="\\b"; break;
					case '\t': o+="\\t"; break;
					case '\n': o+="\\n"; break;
					case '\f': o+="\\f"; break;
					case '\r': o+="\\r"; break;
					case '\"': o+="\\\""; break;
					case '\'': o+="\\\'"; break;
					case '\\': o+="\\\\"; break;
					
					default:
						String ucp = ""+cur;
						while(ucp.length()<4)
							ucp="0"+ucp;
						o+="\\u"+ucp;
				}
				
			}
			else
				o+=(char)cur;
		}
		System.log("Printed "+o);
	}
}
