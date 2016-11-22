package utils;

import java.util.Iterator;

public class Iterator2D<T> implements Iterable<T>
{
	private final T[][] objs;
	private int xindex;
	private int yindex;
	public Iterator2D(T[][] arr)
	{
		objs = arr;
		xindex=0;
		yindex=0;
	}
	@Override
	public Iterator<T> iterator()
	{
		return new Iterator<T>(){
			@Override
			public boolean hasNext()
			{
				try
				{
					if(xindex<objs.length)
						return (objs[xindex][yindex]!=null);
					return (objs[0][yindex]!=null);
				}
				catch(IndexOutOfBoundsException ioobe)
				{
					return false;
				}
			}
			public T next()
			{
				try
				{
					if(xindex<objs.length)
						return objs[xindex++][yindex];
					return objs[xindex=0][yindex++];
				}
				catch(IndexOutOfBoundsException ioobe)
				{
					ioobe.printStackTrace();
					throw new java.util.NoSuchElementException();
				}
			}
		};
	}
}
