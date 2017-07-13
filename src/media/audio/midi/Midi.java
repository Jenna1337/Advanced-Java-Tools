package media.audio.midi;

import media.InvalidDataException;
import media.audio.Audio;

public class Midi extends Audio
{
	//  https://www.csie.ntu.edu.tw/~r92092/ref/midi/
	
	short format, num_tracks, division;
	MTrk[] tracks;
	
	/**
	 * @param bytes The bytes of the file 
	 * @throws InvalidDataException 
	 */
	public Midi(byte[] bytes) throws InvalidDataException
	{
		this.loadBytes(bytes);
	}

	public void loadBytes(byte[] bytes) throws InvalidDataException
	{
		try
		{
			//TODO
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			throw new InvalidDataException(e);
		}
	}
}
