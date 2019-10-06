package net.ftp;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.BiConsumer;

public class FtpRecusiveFileDirectoryIterator{
	final FtpClient c;
	final BiConsumer<String, FtpDirEntry> acceptor;
	public FtpRecusiveFileDirectoryIterator(final FtpClient client, final BiConsumer<String, FtpDirEntry> consumer){
		this.c = client;
		this.acceptor = consumer;
	}
	public void iterateAllFilesDataRecursive(String dir) throws FtpProtocolException, IOException {
		if(dir==null){
			dir = c.getWorkingDirectory();
			if(dir==null)
				throw new IOException("Unable to determine current working directory.");
		}
		Iterator<FtpDirEntry> fileiterator = c.listFiles(dir);
		for(FtpDirEntry f; fileiterator.hasNext() && (f=fileiterator.next())!=null;){
			switch(f.getType()){
				case DIR:
					iterateAllFilesDataRecursive(dir+"/"+f.getName());
					break;
				case FILE:
					acceptor.accept(dir,f);
					break;
				case LINK:
					break;
				case CDIR:
				case PDIR:
				default:
					break;
					
			}
		}
	}
}
