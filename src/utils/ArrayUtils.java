package utils;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayUtils
{
	private ArrayUtils(){}
	
	public static <T> T[] append(T[] arr, T val)
	{
		ArrayList<T> lst = new ArrayList<T>(Arrays.asList(arr));
		lst.add(val);
		return lst.toArray(arr);
	}
	public static <T> T[] append(T[] arr, T[] vals)
	{
		ArrayList<T> lst = new ArrayList<T>(Arrays.asList(arr));
		for(T val : vals)
			lst.add(val);
		return lst.toArray(arr);
	}
}
