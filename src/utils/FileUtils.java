package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class FileUtils
{
	public static File[] getAllSubfiles(final File pathname)
	{
		if(pathname.isDirectory())
		{
			ArrayList<File> files = new ArrayList<File>();
			File[] subpaths = pathname.listFiles();
			for(File subpath : subpaths)
			{
				files.addAll(java.util.Arrays.asList(getAllSubfiles(subpath)));
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
	 * @param adat_pre <i>Unused</i>.
	 * @param adat_post Number of characters after {@code chunk_end}.
	 * @param overwrite Whether or not to overwrite any existing files.
	 * @return The files written.
	 * @throws IOException if an I/O error occurs.
	 */
	public static File[] copyChunks(final File source, String outputdir,
			String ext, final String chunk_start, final String chunk_end,
			final int adat_pre, final int adat_post, final boolean overwrite)
			throws IOException
	{
		// TODO add functionality for adat_pre
		ArrayList<File> files = new ArrayList<File>();
		if(!(outputdir.endsWith("/") || outputdir.endsWith("\\")))
			outputdir += File.pathSeparator;
		if(!ext.startsWith("."))
			ext = "." + ext;
		// int amount = 0;
		FileInputStream sfi = new FileInputStream(source);
		int ch;
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
										System.out.println(files.size() + 1);
										File f = new File(outputdir
												+ (files.size() + 1) + ext);
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
										files.add(f);
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
