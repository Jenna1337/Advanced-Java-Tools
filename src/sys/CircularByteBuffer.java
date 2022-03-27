package sys;

import java.util.Arrays;
import java.util.Iterator;

public class CircularByteBuffer implements Iterable<Byte>
{
	/**
	 * The backing array
	 */
	byte[] data;
	/**
	 * The index where the next added value will be written
	 */
	int head = 0;
	private boolean arrayCacheInvalid = true;
	private byte[] cachedArray;
	
	private final Iterator<Byte> iterator = new Iterator<Byte>(){
		int i=head;
		@Override
		public boolean hasNext(){
			return (i+1) == head;
		}
		@Override
		public Byte next(){
			byte r = data[i];
			i = (i+1) % data.length;
			return r;
		}
	}; 
	
	public CircularByteBuffer(int size){
		data = new byte[size];
		cachedArray = Arrays.copyOf(data, size);
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
								&& Arrays.equals(this.toArray(), (((CircularByteBuffer)obj).toArray()))
								)
						|| (
								objclazz.isArray() 
								&& objclazz.componentType().equals(data.getClass().componentType())
								&& Arrays.equals(this.toArray(), (byte[])obj)
								)
						);
	}
	/**
	 * Converts this circular buffer to an array with the elements specified by the current head position
	 * @return
	 */
	public byte[] toArray(){
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
	public boolean put(byte e){
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
	public boolean add(byte e){
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
	public boolean startsWith(byte[] s) {
		toArray();
		return s.length<=cachedArray.length
				&& Arrays.mismatch(s, 0, s.length, cachedArray, 0, s.length)==-1;
	}
	/**
	 * Checks if this circular buffer ends with the sequence in the array {@code s}
	 * @param s the array to compare
	 * @return true if this buffer ends with the sequence specified in array {@code s}
	 */
	public boolean endsWith(byte[] s) {
		toArray();
		return s.length<=cachedArray.length
				&& Arrays.mismatch(s, 0, s.length, cachedArray, cachedArray.length-s.length, cachedArray.length)==-1;
	}
	
	public Iterator<Byte> iterator(){
		return iterator;
	}
}
