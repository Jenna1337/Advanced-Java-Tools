package media.audio.mp3;

import media.InvalidDataException;
import media.audio.Audio;

public class MP3 extends Audio
{
	
	/**
	 * @param bytes The bytes of the file 
	 * @throws InvalidDataException 
	 */
	public MP3(byte[] bytes) throws InvalidDataException
	{
		loadBytes(bytes);
		// TODO Auto-generated constructor stub
	}

	public void loadBytes(byte[] bytes) throws InvalidDataException
	{
		// TODO Auto-generated method stub
		
	}
	
}
