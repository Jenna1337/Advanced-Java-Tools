package utils.collections;

import java.util.Arrays;
import utils.Utils;

/**
 * 
 * See {@link #getCasted(int)}
 * 
 * @author Jenna
 *
 */
public class GenericList extends java.util.ArrayList<Object>
{
	/**
	 * Constructs a list containing the specified elements.
	 *
	 * @param objs the objects to be placed into this list
	 * @throws NullPointerException if <i>objs</i> is null
	 */
	public GenericList(Object... objs){
		this.addAll(Arrays.asList(objs));
	}
	
	/**
	 * Returns the element at the specified position in this list, casted to its
	 * original type.
	 *
	 * @param index index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	public <T> T getCasted(int index){
		return Utils.castToOriginalType(super.get(index));
	}
}
