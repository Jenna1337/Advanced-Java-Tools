package utils.colors;

import java.awt.Color;
import java.util.ArrayList;

public class Blender
{
	private Blender(){}
	/**
	 * Blends the colors to produce their average color.
	 *  Assumes {@code bitmask} is {@code true}.
	 * @param colors - the {@code Color}(s) to blend.
	 * @return {@code blend(true, Color...)}
	 * @see #blend(boolean, Color...)
	 */
	public static Color blend(Color... colors)
	{
		return blend(true, colors);
	}
	/**
	 * Blends the colors to produce their average color.
	 *  Assumes {@code bitmask} is {@code false}.
	 * @param bitmask - determines if colors are semi-transparent.
	 *  {@code false} allows semi-transparency while {@code true} does not.
	 * @param colors - the {@code Color}(s) to blend.
	 * @return The average of the colors.
	 * @see {@link #blend(boolean, ArrayList) blend(boolean, ArrayList&lt;Integer>)}
	 */
	public static Color blend(final boolean bitmask, Color... colors)
	{
		int[] colorlist = new int[colors.length];
		for(int i=0;i<colors.length;++i)
			colorlist[i]=colors[i].getRGB();
		return new Color(blend(bitmask, colorlist));
	}
	/**
	 * Blends the colors to produce their average color.
	 *  Assumes {@code bitmask} is {@code false}.
	 * @param bitmask - determines if colors are semi-transparent.
	 *  {@code false} allows semi-transparency while {@code true} does not.
	 * @param colors - the {@code Color}(s) to blend.
	 * @return The average of the colors.
	 */
	public static int blend(final boolean bitmask, int... colors)
	{
		long sum1=0,sum2=0;
		int alpha=0;
		for (int color : colors)
		{
			alpha+=((color>>24)&0xFF);
			sum1+= color    &0x00FF00FF;
			sum2+=(color>>8)&0x00FF00FF;
		}
		final int size=colors.length;
		alpha=bitmask ? ((alpha==0)?0:0xFF) : (alpha/size);
		int value=0;
		value|=(((sum1&0xFFFF)/(bitmask?1:size))&0xFF);
		value|=(((sum2&0xFFFF)/(bitmask?1:size))&0xFF)<<8;
		value|=((((sum1>>=16)&0xFFFF)/(bitmask?1:size))&0xFF)<<16;
		return value|=alpha<<24;
	}
	/**
	 * @deprecated
	 * Blends the colors to produce their average color.
	 *  Assumes {@code bitmask} is {@code false}.
	 * @param bitmask - determines if colors are semi-transparent.
	 *  {@code false} allows semi-transparency while {@code true} does not.
	 * @param colors - the {@code Color}(s) to blend.
	 * @return The average of the colors.
	 */
	public static int blend(boolean bitmask, ArrayList<Integer> colors)
	{
		int[] colorlist = new int[colors.size()];
		for(int i=0;i<colorlist.length;++i)
			colorlist[i]=colors.get(i);
		return blend(bitmask, colorlist);
	}
}
