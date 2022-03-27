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
	public static <T> boolean startsWith(T[] haystack, T[] needle) {
		return needle.length<haystack.length
				&& Arrays.mismatch(needle, 0, needle.length, haystack, 0, needle.length)==-1;
	}
	/**
	 * Checks if this circular buffer ends with the sequence in the array {@code s}
	 * @param s the array to compare
	 * @return true if this buffer ends with the sequence specified in array {@code s}
	 */
	public static <T> boolean endsWith(T[] haystack, T[] needle) {
		return needle.length<haystack.length
				&& Arrays.mismatch(needle, 0, needle.length, haystack, haystack.length-needle.length, haystack.length)==-1;
	}
}
