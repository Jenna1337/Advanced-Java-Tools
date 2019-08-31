package utils.json;

public interface JSONParsingFunction<T, R>{
	/**
	 * Applies this function to the given argument.
	 *
	 * @param t the function argument
	 * @return the function result
	 */
	R apply(T t) throws MalformedJSONException;
}
