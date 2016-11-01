package utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;
import javax.imageio.ImageIO;

public class ImageUtils
{
	private ImageUtils(){}
	public static final Comparator<BufferedImage> comparator = new Comparator<BufferedImage>()
	{
		public int compare(BufferedImage o1, BufferedImage o2)
		{
			if((o1.getHeight() == o2.getHeight())
			&&(o1.getWidth() == o2.getWidth())
			&&(o1.getType() == o2.getType()))
			{
				for(int x=0; x<o1.getWidth(); ++x)
					for(int y=0; y<o1.getHeight(); ++y)
						if(o1.getRGB(x, y) != o2.getRGB(x, y))
							return 1;
				return 0;
			}
			return -1;
		}
	};
	public static boolean equals(BufferedImage o1, BufferedImage o2)
	{
		return comparator.compare(o1, o2)==0;
	}
	public static String toDataString(BufferedImage img, String format) throws IOException
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ImageIO.write(img, format, bytes);
		return bytes.toString();
	}
	public static BufferedImage fromDataString(String data) throws IOException
	{
		File tmp = File.createTempFile("img"+data.hashCode(), ".tmp");
		RandomAccessFile raf = new RandomAccessFile(tmp, "rws");
		raf.write(data.getBytes());
		raf.close();
		BufferedImage img = ImageIO.read(tmp);
		return img;
	}
}
