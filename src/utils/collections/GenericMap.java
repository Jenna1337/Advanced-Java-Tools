package utils.collections;

import java.util.Arrays;
import java.util.HashMap;
import utils.tuples.Pair;

/**
 * 
 * See {@link #getCasted(Object)}
 * 
 * @author Jenna
 *
 */
public class GenericMap extends HashMap<Object, Object>
{
	/**
	 * Constructs a map containing the specified elements.
	 *
	 * @param objs the objects to be placed into this map
	 * @throws NullPointerException if <i>objs</i> is null
	 */
	public GenericMap(Pair<?,?>... entries){
		Arrays.stream(entries)
				.forEach(entry -> this.put(entry.getFirst(), entry.getFirst()));
	}
	
	/**
	 * Returns the element at the specified position in this map, casted to its
	 * original type.
	 *
	 * @param key index of the element to return
	 * @return the element at the specified position in this map
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public <T,K> T getCasted(K key){
		Object obj = super.get(key);
		return obj == null ? null : (T)obj.getClass().cast(obj);
	}
}
