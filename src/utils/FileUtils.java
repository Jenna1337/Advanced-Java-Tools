package utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils
{
	public static File[] getAllSubfiles(final File pathname)
	{
		return getAllSubfiles(pathname, "");
	}
	public static File[] getAllSubfiles(final File pathname, final String endswith)
	{
		System.out.println("Scanning \""+pathname.getAbsolutePath()+"\"");
		if(pathname.isDirectory())
		{
			ArrayList<File> files = new ArrayList<File>();
			File[] subpaths = pathname.listFiles();
			if(subpaths == null)
				return new File[]{};
			for(File subpath : subpaths)
			{
				List<File> subfiles = java.util.Arrays.asList(getAllSubfiles(subpath, endswith));
				for(File subfile : subfiles)
					if(StringUtils.endsWithIgnoreCase(subfile.getName(), endswith))
						files.add(subfile);
			}
			return files.toArray(new File[files.size()]);
		}
		else if(pathname.isFile())
			return new File[]{pathname};
		else
			throw new IllegalArgumentException("Pathname \"" + pathname + "\""
					+ " is not a file or directory!");
	}
	/**
	 * Copies the desired chunks of data from a file to a given output
	 * directory.<br>
	 * <b>Note:</b> large chunks will take <u>longer</u> to process, and
	 * <u>there is no way to know how much more time is required</u>.
	 * 
	 * @param source The {@link File} to read from.
	 * @param outputdir The directory to write to.
	 * @param ext The extension of the output files.
	 * @param chunk_start The {@code String} that denotes the start of a chunk.
	 * @param chunk_end The {@code String} that denotes the end of the chunk.
	 * @param adat_pre Number of characters before {@code chunk_end}.
	 * @param adat_post Number of characters after {@code chunk_end}.
	 * @param overwrite Whether or not to overwrite any existing files.
	 * @return The files written.
	 * @throws IOException if an I/O error occurs.
	 */
	public static File[] copyChunks(final File source, String outputdir,
			String ext, final String chunk_start, final String chunk_end,
			final int adat_pre, final int adat_post, final boolean overwrite)
			throws AccessDeniedException, IOException
	{
		// TODO add functionality for adat_pre
		ArrayList<File> files = new ArrayList<File>();
		if(!(outputdir.endsWith("/") || outputdir.endsWith("\\")))
			outputdir += File.separator;
		if(!ext.startsWith("."))
			ext = "." + ext;
		int amount = 0;
		BufferedInputStream sfi;
		try
		{
			sfi = new BufferedInputStream(new FileInputStream(source));
		}
		catch(IOException e)
		{
			throw new AccessDeniedException(source.getAbsolutePath(), null, e.getMessage());
		}
		int ch;
		File f;
		loop1: while((ch = sfi.read()) != -1)
		{
			String buffer = "";
			loop2: for(int start = 0; start < chunk_start.length(); ++start)
			{
				if(ch == chunk_start.charAt(start))
				{
					buffer += new String(Character.toChars(ch));
					// System.out.println(buffer);
					ch = sfi.read();
					if(ch == -1)
						break loop1;
					if(start == chunk_start.length() - 1)
					{
						f = new File(outputdir + amount + ext);
						if(f.exists() && !overwrite)
						{
							System.out.println("Copying "+source.getAbsolutePath()+"/"+(amount++));
							continue loop1;
						}
						buffer += new String(Character.toChars(ch));
						while((ch = sfi.read()) != -1)
						{
							buffer += new String(Character.toChars(ch));
							loop3: for(int end = 0; end < chunk_end
									.length(); ++end)
							{
								
								if(ch == chunk_end.charAt(end))
								{
									if(end != 0)
										buffer += new String(
												Character.toChars(ch));
									// System.out.println(buffer);
									ch = sfi.read();
									if(ch == -1)
										break loop1;
									if(end == chunk_end.length() - 1)
									{
										for(int i = 0; i < adat_post; ++i)
											buffer += new String(Character
													.toChars(ch = sfi.read()));
										// System.out.println(buffer);
										System.out.println("Copying "+source.getAbsolutePath()+"/"+(++amount));
										files.add(f);
										if(f.exists() && !overwrite)
											continue loop1;
										StringReader sr = new StringReader(
												buffer);
										FileOutputStream fos = new FileOutputStream(
												f);
										int ch2;
										while((ch2 = sr.read()) != -1)
											fos.write(ch2);
										fos.close();
										System.gc();
										continue loop1;
									}
								}
								else
								{
									if(end != 0)
										buffer += new String(
												Character.toChars(ch));
									break loop3;
								}
							}
						}
					}
				}
				else
				{
					break loop2;
				}
			}
		}
		sfi.close();
		return files.toArray(new File[files.size()]);
	}
}
