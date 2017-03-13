package media.audio.wav;

import media.InvalidDataException;
import media.audio.Audio;

public class WAV extends Audio
{
	
	/**
	 * @param bytes The bytes of the file 
	 * @throws InvalidDataException 
	 */
	public WAV(byte[] bytes) throws InvalidDataException
	{
		loadBytes(bytes);
		// TODO Auto-generated constructor stub
	}

	public void loadBytes(byte[] bytes) throws InvalidDataException
	{
		// TODO Auto-generated method stub
		
	}
	
}
