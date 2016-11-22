package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public class DLLUtils
{
	private static final String PNG_START = "\u0089PNG";
	private static final String PNG_END = "IEND";
	

	public static void copyPNGsFromDLL(File dll, String dir, boolean overwrite)
			throws AccessDeniedException, IOException
	{
		new File(dir).mkdirs();
		FileUtils.copyChunks(dll, dir, ".png", PNG_START, PNG_END, 0, 4, overwrite);
	}
	public static void copyPNGsFromDLL(File dll, String dir) throws IOException
	{
		copyPNGsFromDLL(dll, dir, true);
	}
}
