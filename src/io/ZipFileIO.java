package io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFileIO
{
	private ZipFileIO()
	{
	}
	public static void extractZipFile(final NamedFileInputStream stream,
			String outdir)
	{
		extractZipFile(stream, stream.getFile().getName(), outdir, System.out);
	}
	public static void extractZipFile(final NamedFileInputStream stream,
			String outdir, final PrintStream log)
	{
		extractZipFile(stream, stream.getFile().getName(), outdir, System.out);
	}
	public static void extractZipFile(final InputStream stream, String filename,
			String outdir)
	{
		extractZipFile(stream, filename, outdir, System.out);
	}
	public static void extractZipFile(final InputStream stream, String filename,
			String outdir, final PrintStream log)
	{
		extractZipFile(stream, filename, new File(outdir), log);
	}
	public static void extractZipFile(final NamedFileInputStream stream,
			File outdir)
	{
		extractZipFile(stream, stream.getFile().getName(), outdir, System.out);
	}
	public static void extractZipFile(final NamedFileInputStream stream,
			File outdir, final PrintStream log)
	{
		extractZipFile(stream, stream.getFile().getName(), outdir, System.out);
	}
	public static void extractZipFile(final InputStream stream, String filename,
			File outdir)
	{
		extractZipFile(stream, filename, outdir, System.out);
	}
	public static void extractZipFile(final InputStream stream, String filename,
			File outdir, final PrintStream log)
	{
		ZipInputStream zinstream = new ZipInputStream(stream);
		try
		{
			ZipEntry zentry = zinstream.getNextEntry();
			// TODO
			while(zentry != null)
			{
				String entryName = zentry.getName();
				log.println(filename + ": Extracting " + entryName);
				String fpath = outdir.getAbsolutePath() + File.separator
						+ entryName;
				FileOutputStream outstream;
				try
				{
					File f = new File(fpath);
					//if(!f.exists())
					{
						f.getParentFile().mkdirs();
						if(f.exists())
							f.delete();
						if(!f.createNewFile())
							throw new IOException("Cannot create file " + fpath
									+ " (Access is denied)");
					}
					outstream = new FileOutputStream(f);
					int n = zinstream.read();
					while(n != -1)
					{
						outstream.write(n);
						try
						{
							n = zinstream.read();
						}
						catch(IOException e)
						{
							throw new Error("Failed to read data from stream!",
									e);
						}
					}
					System.out.println("Extracted " + entryName);
					outstream.close();
				}
				catch(IOException e)
				{
					throw new Error("Failed to write data to "
							+ fpath, e);
				}
				zinstream.closeEntry();
				zentry = zinstream.getNextEntry();
			}
			zinstream.close();
		}
		catch(IOException e)
		{
			throw new Error("Failed to get next entry from zip file!", e);
		}
	}
}
