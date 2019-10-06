import java.io.IOException;
import java.util.Properties;
import net.ftp.FtpClient;
import net.ftp.FtpProtocolException;
import net.ftp.FtpRecusiveFileDirectoryIterator;
import utils.Utils;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		Properties ftpprops = Utils.loadProperties("ftpclient.properties");
		;
		FtpClient client = FtpClient.create(ftpprops.getProperty("DEST"));
		{
			String user = ftpprops.getProperty("USER");
			String pass = ftpprops.getProperty("PASS");
			if(user != null){
				if(pass != null)
					client.login(user, pass.toCharArray());
				else
					client.login(user, new char[0]);
			}
			user=null;
			pass=null;
		}
		System.gc();
				
		printAllFilesData(client, ftpprops.getProperty("BASE", ""));
		client.close();
		
		
		//DataBuffer b = new DataBuffer();
		//System.out.println(b);
	}
	
	public static void printAllFilesData(final FtpClient client, final String dir) throws FtpProtocolException, IOException{
		new FtpRecusiveFileDirectoryIterator(client, (filedir,fileentry)->{
			System.out.println(filedir+"/"+fileentry.getName()+" "+fileentry.getLastModified());
		}).iterateAllFilesDataRecursive(dir);
	}
}
