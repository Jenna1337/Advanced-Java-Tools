package audio.midi;

public class Midi
{
	//  https://www.csie.ntu.edu.tw/~r92092/ref/midi/
	
	short format, num_tracks, division;
	MTrk[] tracks;
	
	public Midi(byte[] bytes) throws InvalidMidiException
	{
		try
		{
			
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			throw new InvalidMidiException(e);
		}
	}
}
