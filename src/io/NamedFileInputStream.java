package io;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

public class NamedFileInputStream extends FileInputStream
{
	private final File file;
	
	public NamedFileInputStream(String name) throws FileNotFoundException
	{
		this(new File(name));
	}
	
	public NamedFileInputStream(File file) throws FileNotFoundException
	{
		super(file);
		this.file = file;
	}

	public File getFile()
	{
		return file;
	}
	@Deprecated
	public String getPath() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		Field field = FileInputStream.class.getDeclaredField("path");
		field.setAccessible(true);
		return (String)field.get((FileInputStream)this);
	}
}
