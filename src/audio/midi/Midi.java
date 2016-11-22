package audio.midi;

public class Midi
{
	//  https://www.csie.ntu.edu.tw/~r92092/ref/midi/
	
	short format, num_tracks, division;
	MTrk[] tracks;
	
	/**
	 * @param bytes The bytes of the midi file 
	 */
	public Midi(byte[] bytes) throws InvalidMidiException
	{
		try
		{
			//TODO
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			throw new InvalidMidiException(e);
		}
	}
}
