package utils.collections;

import java.util.AbstractList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

//TODO document this class
public class HashList<E> extends AbstractList<E>
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
		return super.iterator();
	}
	public Object[] toArray()
	{
		int size = size();
		Object[] r = new Object[size];
		for(int i=0;i<size;++i)
		{
			r[i]=get(i);
		}
		return r;
	}
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a)
	{
		// Estimate size of array; be prepared to see more or fewer elements
		int size = size();
		T[] r = a.length >= size ? a :
			(T[])java.lang.reflect.Array
			.newInstance(a.getClass().getComponentType(), size);
		for(int i=0;i<size;++i){
			r[i] = (T) get(i);
		}
		return r;
	}
	public boolean add(E e)
	{
		map.put(map.size(), e);
		return true;
	}
	public boolean remove(Object o)
	{
		return super.remove(o);
	}
	public boolean containsAll(Collection<?> c)
	{
		return super.containsAll(c);
	}
	public boolean addAll(Collection<? extends E> c)
	{
		return super.addAll(c);
	}
	public boolean addAll(int index, Collection<? extends E> c)
	{
		return super.addAll(index, c);
	}
	public boolean removeAll(Collection<?> c)
	{
		return super.removeAll(c);
	}
	public boolean retainAll(Collection<?> c)
	{
		return super.retainAll(c);
	}
	public void clear()
	{
		map.clear();
	}
	public E get(int index)
	{
		rangeCheck(index);
		return map.get(index);
	}
	public E set(int index, E element)
	{
		rangeCheck(index);
		return map.put(index, element);
	}
	public void add(int index, E element)
	{
		rangeCheckForAdd(index);
		int size = size();
		for(int i=size;i>=index;--i)
		{
			//move the item at i to the right
			set(i+1, get(i));
			remove(i);
		}
		set(index, element);
	}
	public E remove(int index)
	{
		rangeCheck(index);
		E item = get(index);
		int size = size();
		for(int i=index+1;i<size;++i)
		{
			//move the item at i to the left
			set(i-1, get(i));
			remove(i);
		}
		remove(size-1);
		return item;
	}
	public int indexOf(Object o)
	{
		return super.indexOf(o);
	}
	public int lastIndexOf(Object o)
	{
		return super.lastIndexOf(o);
	}
	public ListIterator<E> listIterator()
	{
		return listIterator(0);
	}
	public ListIterator<E> listIterator(int index)
	{
		return super.listIterator(index);
	}
	public List<E> subList(int fromIndex, int toIndex)
	{
		return super.subList(fromIndex, toIndex);
	}
	/**
	 * Checks if the given index is in range.  If not, throws an appropriate
	 * runtime exception.  This method does *not* check if the index is
	 * negative: It is always used immediately prior to an array access,
	 * which throws an ArrayIndexOutOfBoundsException if index is negative.
	 */
	private void rangeCheck(int index) {
		if (index >= size())
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}
	/**
	 * A version of rangeCheck used by add and addAll.
	 */
	private void rangeCheckForAdd(int index) {
		if (index > size() || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}
	/**
	 * Constructs an IndexOutOfBoundsException detail message.
	 * Of the many possible refactorings of the error handling code,
	 * this "outlining" performs best with both server and client VMs.
	 */
	private String outOfBoundsMsg(int index) {
		return "Index: "+index+", Size: "+size();
	}
}
