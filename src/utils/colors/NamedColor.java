package utils.colors;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("serial")
public class NamedColor extends Color
{
	private static final ArrayList<NamedColor> colors = getColors();
	/**
	 * Gets all of the Color fields from NamedColor
	 */
	private static ArrayList<NamedColor> getColors()
	{
		Field[] fields = NamedColorConstants.class.getDeclaredFields(); //TODO
		ArrayList<NamedColor> arr = new java.util.ArrayList<NamedColor>();
		for(Field f : fields)
		{
			try{
				arr.add(new NamedColor(f.getName(), (int)f.get(null)));
			}catch(Exception e){}
		}
		return arr;
	}
	private final String name;
	public NamedColor(String name, int rgbacolor)
	{
		super(rgbacolor, true);
		this.name=name;
	}
	public NamedColor(String name, Color color)
	{
		this(name, getARGB(color));
	}
	
	public int getARGB()
	{
		return getARGB(this);
	}
	public static int getARGB(java.awt.Color c)
	{
		return (c.getAlpha()<<24) | (c.getRed()<<16) | (c.getGreen()<<8) | (c.getBlue()<<0); 
	}
	public static NamedColor getColorForName(String name)
	{
		for(NamedColor color : colors)
			if(color.name.equalsIgnoreCase(name))
				return color;
		return null;
	}
	public String getName()
	{
		return this.name;
	}
	@Override
	public String toString()
	{
		String argb=""+Integer.toUnsignedString(this.getARGB(), 16);
		while(argb.length()<8)
			argb="0"+argb;
		return getClass().getName()+"[name=\""+getName()+"\", argb=0x"+argb+"]";
	}
	private static final Comparator<NamedColor> sortnameup = new Comparator<NamedColor>()
		{
			public int compare(NamedColor o1, NamedColor o2)
			{
				int c1=o1.getName().compareToIgnoreCase(o2.getName());
				return c1!=0?c1:((Integer)o1.getRGB()).compareTo(o2.getRGB());
			}
		},
		sortnamedown = new Comparator<NamedColor>()
		{
			public int compare(NamedColor o1, NamedColor o2)
			{
				int c1=o1.getName().compareToIgnoreCase(o2.getName());
				return -(c1!=0?c1:((Integer)o1.getRGB()).compareTo(o2.getRGB()));
			}
		};
	public static <T extends List<NamedColor>> T sortByNameAcending(T lst)
	{
		lst.sort(sortnameup);
		return lst;
	}
	public static <T extends List<NamedColor>> T sortByNameDecending(T lst)
	{
		lst.sort(sortnamedown);
		return lst;
	}
	public static NamedColor parse(String text)
	{
		
		NamedColor nc = NamedColor.getColorForName(text);
		if(nc!=null)
			return nc;
		try
		{
			//Note: Integer.decode(String) throws an error if the String represents a negative integer.
			//For example, Integer.decode("0xFFFFFFFF") will throw an error.
			return new NamedColor("Custom", Long.decode(text).intValue());
		}
		catch(NumberFormatException nfe)
		{
			throw new IllegalArgumentException("\""+text+"\" is not a valid color.");
		}
	}
}
