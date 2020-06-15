package advWindows.progs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import net.ftp.FtpClient;
import net.ftp.FtpProtocolException;

/**
 * Connects a local folder to a remote folder, with a GUI showing the changed
 * files, checkboxes for each changed file, as well as a "push" and "pull" button.
 * 
 * @author Jenna
 *
 */
public class FtpFolderSyncClient
{
	private FtpClient ftp;
	private String localDirectory;
	/**
	 * @param hostname The domain name or IP address to connect to.
	 * @param remoteDirectory the path to set the current directory the remote server. 
	 * If {@code null}, will default to the server's default directory.
	 * @throws IOException if an IOException occurs.
	 * @throws FtpProtocolException if a FTP related exception occurs.
	 */
	public FtpFolderSyncClient(String localDirectory, String hostname, String remoteDirectory) throws FtpProtocolException, IOException{
		if(localDirectory == null)
			throw new IllegalArgumentException("localDirectory cannot be null.");
		File local = new File(localDirectory);
		if(!local.exists())
			throw new FileNotFoundException("Failed to locate folder "+localDirectory);
		if(!local.canRead())
			throw new IOException("Cannot read from folder "+localDirectory);
		if(!local.canWrite())
			throw new IOException("Cannot write to folder "+localDirectory);
		this.localDirectory = localDirectory;
		this.ftp = FtpClient.create(hostname);
		if(remoteDirectory!=null)
			ftp.changeDirectory(remoteDirectory);
		
		// TODO Auto-generated constructor stub
	}
}
