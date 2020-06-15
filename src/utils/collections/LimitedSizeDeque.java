/**
 * 
 */
package utils.collections;

import java.util.ArrayDeque;

/**
 * @author Jenna Sloan
 *
 */
public class LimitedSizeDeque<E> extends ArrayDeque<E>
{
	private int capacity;
	public LimitedSizeDeque(int capacity){
		super(capacity);
	}
	/**
	 * Inserts the specified element at the end of this deque, removing
	 * the element at the beginning of this deque if at capacity.
	 *
	 * <p>This method is equivalent to {@link #addLast}.
	 *
	 * @param e the element to add
	 * @return true
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean add(E e){
		addLast(e);
		return true;
	}
	/**
	 * Inserts the specified element at the end of this deque, removing
	 * the element at the beginning of this deque if at capacity.
	 *
	 * <p>This method is equivalent to {@link #offerLast}.
	 *
	 * @param e the element to add
	 * @return true
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean offer(E e){
		addLast(e);
		return true;
	}
	/**
	 * Inserts the specified element at the front of this deque, removing
	 * the element at the end of this deque if at capacity.
	 *
	 * @param e the element to add
	 * @throws NullPointerException if the specified element is null
	 */
	public void addFirst(E e){
		if(size()>=capacity)
			removeLast();
		super.addFirst(e);
	}
	/**
	 * Inserts the specified element at the end of this deque, removing
	 * the element at the beginning of this deque if at capacity.
	 *
	 * <p>This method is equivalent to {@link #add}.
	 *
	 * @param e the element to add
	 * @throws NullPointerException if the specified element is null
	 */
	public void addLast(E e){
		if(size()>=capacity)
			removeFirst();
		super.addLast(e);
	}
	/**
	 * Inserts the specified element at the front of this deque, removing
	 * the element at the end of this deque if at capacity.
	 *
	 * @param e the element to add
	 * @return true
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean offerFirst(E e){
		addFirst(e);
		return true;
	}
	/**
	 * Inserts the specified element at the end of this deque, removing
	 * the element at the beginning of this deque if at capacity.
	 *
	 * @param e the element to add
	 * @return true
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean offerLast(E e){
		addLast(e);
		return true;
	}
	/**
	 * {@inheritDoc}
	 */
	public void push(E e){
		addFirst(e);
	}
	
}
