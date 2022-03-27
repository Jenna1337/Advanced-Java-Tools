package sys;

import java.util.Arrays;
import java.util.Iterator;

public class CircularBuffer<T> implements Iterable<T>
{
	/**
	 * The backing array
	 */
	T[] data;
	/**
	 * The index where the next added value will be written
	 */
	int head = 0;
	private boolean arrayCacheInvalid = true;
	private T[] cachedArray;
	
	private final Iterator<T> iterator = new Iterator<T>(){
		int i=head;
		@Override
		public boolean hasNext(){
			return (i+1) == head;
		}
		@Override
		public T next(){
			T r = data[i];
			i = (i+1) % data.length;
			return r;
		}
	}; 
	
	/**
	 * Creates a new CircularBuffer from the array {@code a}
	 * @param a the array to use as a base
	 */
	public CircularBuffer(T[] a){
		data = a;
		cachedArray = Arrays.copyOf(data, a.length);
	}
	
	public int size(){
		return data.length;
	}
	@Override
	public boolean equals(Object obj){
		//TODO probably a better way to do this
		Class<?> objclazz;
		return obj != null
				&& (
						((objclazz=obj.getClass()).equals(this.getClass())
								&& Arrays.equals(this.toArray(), (((CircularBuffer<?>)obj).toArray()))
								)
						|| (
								objclazz.isArray() 
								&& objclazz.componentType().equals(data.getClass().componentType())
								&& Arrays.equals(this.toArray(), (Object[])obj)
								)
						);
	}
	/**
	 * Converts this circular buffer to an array with the elements specified by the current head position
	 * @return
	 */
	public T[] toArray(){
		if(arrayCacheInvalid) {
			int i=head,j=0;
			do {
				cachedArray[j] = data[i];
				j++;
				i = (i+1) % data.length;
			}
			while(i!=head);
		}
		return cachedArray;
	}
	
	/**
	 * Puts the value to the end of the list
	 * @param e the value to put onto the list
	 * @return true
	 */
	public boolean put(T e){
		data[head] = e;
		head = (head+1) % data.length;
		arrayCacheInvalid = true;
		return true;
	}
	/**
	 * Adds the value to the end of the list
	 * @param e the value to put onto the list
	 * @return true
	 */
	public boolean add(T e){
		data[head] = e;
		head = (head+1) % data.length;
		arrayCacheInvalid = true;
		return true;
	}
	/**
	 * Checks if this circular buffer starts with the sequence in the array {@code s}
	 * @param s the array to compare
	 * @return true if this buffer starts with the sequence specified in array {@code s}
	 */
	public boolean startsWith(T[] s) {
		toArray();
		return s.length<cachedArray.length
				&& Arrays.mismatch(s, 0, s.length, cachedArray, 0, s.length)==-1;
	}
	/**
	 * Checks if this circular buffer ends with the sequence in the array {@code s}
	 * @param s the array to compare
	 * @return true if this buffer ends with the sequence specified in array {@code s}
	 */
	public boolean endsWith(T[] s) {
		toArray();
		return s.length<cachedArray.length
				&& Arrays.mismatch(s, 0, s.length, cachedArray, cachedArray.length-s.length, cachedArray.length)==-1;
	}
	
	public Iterator<T> iterator(){
		return iterator;
	}
}
