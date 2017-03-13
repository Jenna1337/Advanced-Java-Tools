package media.image.jpg;

import media.InvalidDataException;
import media.image.Image;

public class JPG extends Image
{
	
	/**
	 * @param bytes The bytes of the file 
	 * @throws InvalidDataException 
	 */
	public JPG(byte[] bytes) throws InvalidDataException
	{
		loadBytes(bytes);
		// TODO Auto-generated constructor stub
	}

	public void loadBytes(byte[] bytes) throws InvalidDataException
	{
		// TODO Auto-generated method stub
		
	}
	
}
