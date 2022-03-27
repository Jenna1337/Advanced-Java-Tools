import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import net.ftp.FtpClient;
import net.ftp.FtpProtocolException;
import net.ftp.FtpRecusiveFileDirectoryIterator;
import sys.CircularByteBuffer;
import sys.CircularIntBuffer;
import utils.Utils;
import utils.filesystem.FileUtils;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		//CircularByteBuffer c = new CircularByteBuffer(4);
		//c.put((byte)1);
		//for(int i : new int[] {4,5,6,7})
		//	c.put((byte)i);
		//c.put((byte)2);
		//System.out.println(Arrays.toString(c.toArray()));
		//System.out.println(c.startsWith(new byte[] {5,6,7,2}));
		//System.out.println(c.endsWith(new byte[] {5,6,7,2}));
		
		new File("test/").mkdir();
		FileUtils.copyChunks(new File("C:\\Windows\\explorer.exe"),
				"test/", ".png",
				new byte[]{(byte)0x89,'P','N','G'}, "IEND".getBytes(),
				0, 4, true);
		
		System.exit(0);
		
		Properties ftpprops = Utils.loadProperties("ftpclient.properties");
		
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
	}
	
	public static void printAllFilesData(final FtpClient client, final String dir) throws FtpProtocolException, IOException{
		new FtpRecusiveFileDirectoryIterator(client, (filedir,fileentry)->{
			System.out.println(filedir+"/"+fileentry.getName()+" "+fileentry.getLastModified());
		}).iterateAllFilesDataRecursive(dir);
	}
}
