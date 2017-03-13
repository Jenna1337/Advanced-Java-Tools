package media.video.mp4;

import media.InvalidDataException;
import media.video.Video;

public class MP4 extends Video
{
	
	/**
	 * @param bytes The bytes of the file 
	 * @throws InvalidDataException 
	 */
	public MP4(byte[] bytes) throws InvalidDataException
	{
		loadBytes(bytes);
		// TODO Auto-generated constructor stub
	}

	public void loadBytes(byte[] bytes) throws InvalidDataException
	{
		// TODO Auto-generated method stub
		
	}
	
}
