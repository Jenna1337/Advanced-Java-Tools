package utils;

import java.awt.Color;
import java.util.ArrayList;

public class Blender
{
	public static Color blend(final boolean bitmask, Color... colors)
	{
		ArrayList<Integer> colorlist=new ArrayList<Integer>();
		for(Color color : colors)
			colorlist.add(color.getRGB());
		
		return new Color(blend(bitmask, colorlist));
	}
	public static int blend(final boolean bitmask, int... colors)
	{
		ArrayList<Integer> colorlist=new ArrayList<Integer>();
		for(int color : colors)
			colorlist.add(color);
		return blend(bitmask, colorlist);
	}
	public static int blend(boolean bitmask, ArrayList<Integer> colorlist)
	{
		long sum1=0,sum2=0;
		int alpha=0;
		for (int color : colorlist)
		{
			alpha+=((color>>24)&0xFF);
			sum1+= color    &0x00FF00FF;
			sum2+=(color>>8)&0x00FF00FF;
		}
		final int size=colorlist.size();
		alpha=bitmask ? ((alpha==0)?0:0xFF) : (alpha/size);
		int value=0;
		value|=(((sum1&0xFFFF)/(bitmask?1:size))&0xFF);
		value|=(((sum2&0xFFFF)/(bitmask?1:size))&0xFF)<<8;
		value|=((((sum1>>=16)&0xFFFF)/(bitmask?1:size))&0xFF)<<16;
		return value|=alpha<<24;
	}
}
