package utils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;
import javax.imageio.ImageIO;

public class ImageUtils
{
	private ImageUtils()
	{
	}
	
	private static final Comparator<ColorModel> colormodelcomp = new Comparator<ColorModel>()
	{
		public int compare(ColorModel o1, ColorModel o2)
		{
			System.out.println(o1.hashCode() + " " + o2.hashCode());
			if(o1.hashCode() == o2.hashCode()
					|| o1.toString().equals(o2.toString()))
				return 0;
			return 1;
		}
	};
	private static final Comparator<BufferedImage> bufimgcomp = new Comparator<BufferedImage>()
	{
		public int compare(BufferedImage o1, BufferedImage o2)
		{
			if((o1.getHeight() == o2.getHeight())
					&& (o1.getWidth() == o2.getWidth())
					&& (o1.getType() == o2.getType())
					&& (ImageUtils.compare(o1.getColorModel(),
							o2.getColorModel()) == 0))
			{
				for(int x = 0; x < o1.getWidth(); ++x)
					for(int y = 0; y < o1.getHeight(); ++y)
						if(o1.getRGB(x, y) != o2.getRGB(x, y))
							return 1;
				return 0;
			}
			return -1;
		}
	};
	
	public static boolean equals(BufferedImage o1, BufferedImage o2)
	{
		return bufimgcomp.compare(o1, o2) == 0;
	}
	public static boolean equals(ColorModel o1, ColorModel o2)
	{
		return colormodelcomp.compare(o1, o2) == 0;
	}
	public static int compare(BufferedImage o1, BufferedImage o2)
	{
		return bufimgcomp.compare(o1, o2);
	}
	public static int compare(ColorModel o1, ColorModel o2)
	{
		return colormodelcomp.compare(o1, o2);
	}
	public static String toDataString(BufferedImage img, String format)
			throws IOException
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ImageIO.write(img, format, bytes);
		return bytes.toString();
	}
	public static BufferedImage fromDataString(String data) throws IOException
	{
		File tmp = File.createTempFile("img" + data.hashCode(), ".tmp");
		RandomAccessFile raf = new RandomAccessFile(tmp, "rws");
		raf.write(data.getBytes());
		raf.close();
		BufferedImage img = ImageIO.read(tmp);
		return img;
	}
}
