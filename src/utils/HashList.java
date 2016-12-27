package utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HashList<E> implements List<E>
{
	private HashMap<Integer, E> map;
	public int size()
	{
		return map.size();
	}
	public boolean isEmpty()
	{
		return map.isEmpty();
	}
	public boolean contains(Object o)
	{
		return map.containsValue(o);
	}
	public Iterator<E> iterator()
	{
		// TODO Auto-generated method stub
		return null;
	}
	public Object[] toArray()
	{
		// TODO Auto-generated method stub
		return null;
	}
	public <T> T[] toArray(T[] a)
	{
		// TODO Auto-generated method stub
		return null;
	}
	public boolean add(E e)
	{
		map.put(map.size(), e);
		return true;
	}
	public boolean remove(Object o)
	{
		// TODO Auto-generated method stub
		return false;
	}
	public boolean containsAll(Collection<?> c)
	{
		for(Object o:c)
			if(!this.contains(o))
				return false;
		return true;
	}
	public boolean addAll(Collection<? extends E> c)
	{
		for(E e:c)
			this.add(e);
		return false;
	}
	public boolean addAll(int index, Collection<? extends E> c)
	{
		// TODO Auto-generated method stub
		return false;
	}
	public boolean removeAll(Collection<?> c)
	{
		// TODO Auto-generated method stub
		return false;
	}
	public boolean retainAll(Collection<?> c)
	{
		// TODO Auto-generated method stub
		return false;
	}
	public void clear()
	{
		map.clear();
	}
	public E get(int index)
	{
		return map.get(index);
	}
	public E set(int index, E element)
	{
		return map.put(index, element);
	}
	public void add(int index, E element)
	{
		// TODO Auto-generated method stub
		
	}
	public E remove(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}
	public int indexOf(Object o)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	public int lastIndexOf(Object o)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	public ListIterator<E> listIterator()
	{
		// TODO Auto-generated method stub
		return null;
	}
	public ListIterator<E> listIterator(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}
	public List<E> subList(int fromIndex, int toIndex)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
