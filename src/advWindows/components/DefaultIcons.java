package advWindows.components;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class DefaultIcons
{
	private DefaultIcons(){}
	private static ImageIcon getIcon(String name)
	{
		try
		{
			return  new ImageIcon(ImageIO.read(ClassLoader.getSystemResourceAsStream(name)));
		}
		catch(IOException e)
		{
			throw new InternalError(e);
		}
	}
	
	public static final ImageIcon ico = getIcon("");
	
	
}
