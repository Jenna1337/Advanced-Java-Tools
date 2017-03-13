package media;

public interface Media
{
	
	// TODO Auto-generated stub
	
	/**
	 * Attempts to load the specified bytes
	 * @param bytes The bytes of the file 
	 * @throws InvalidDataException if the data structure is invalid. 
	 */
	public abstract void loadBytes(byte[] bytes) throws InvalidDataException;
}
