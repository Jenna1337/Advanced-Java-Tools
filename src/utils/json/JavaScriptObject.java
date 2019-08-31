package utils.json;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import utils.Utils;

/**
 * 
 * @author Jenna
 *
 */
public class JavaScriptObject
{
	private Map<String, Object> data;
	public JavaScriptObject(){
		data = new LinkedHashMap<>();
	}
	
	public <T> T get(String key){
		return Utils.castToOriginalType(data.get(key));
	}
	public <T> T put(String key, T value){
		return Utils.castToOriginalType(data.put(key, value));
	}
	public boolean hasOwnProperty(String prop){
		return data.containsKey(prop);
	}
	
	public static Set<String> keys(JavaScriptObject obj){
		return obj.data.keySet();
	}
	public static Collection<Object> values(JavaScriptObject obj){
		return obj.data.values();
	}
	
	public String toString(){
		return toString(this);
	}
	public String toDebugString(){
		return toDebugString(this);
	}
	static String toString(Object obj){
		return export(obj);
	}
	static String toDebugString(Object value){
		return exportDebug(value);
	}
	private static String toString(JavaScriptObject obj){
		return obj.data.entrySet().stream().map((entry) -> {
			return export(entry.getKey()) + ": " + export(entry.getValue());
		}).collect(Collectors.joining(", ", "{", "}"));
	}
	private static String toDebugString(JavaScriptObject obj){
		return obj.data.entrySet().stream().map((entry) -> {
			return exportDebug(entry.getKey()) + ": "
					+ exportDebug(entry.getValue());
		}).collect(Collectors.joining(", ", "Object({", "})"));
	}
	private static String export(Object value){
		if(value == null)
			return "null";
		Class<?> cls = value.getClass();
		if(JavaScriptObject.class.isAssignableFrom(cls))
			return toString((JavaScriptObject)value);
		if(cls.isArray())
			return Arrays.stream((Object[])value).map(JavaScriptObject::export)
					.collect(Collectors.joining(", ", "[", "]"));
		if(CharSequence.class.isAssignableFrom(cls)
				|| CharSequence.class.isAssignableFrom(cls)
				|| Character.class.isAssignableFrom(cls)
				|| char.class.isAssignableFrom(cls))
			return "\"" + Utils.escapeString("" + value) + "\"";
		if(cls.isAssignableFrom(Boolean.class)
				|| cls.isAssignableFrom(boolean.class))
			return (boolean)value ? "true" : "false";
		if(cls.isAssignableFrom(Number.class)
				|| cls.isAssignableFrom(long.class)
				|| cls.isAssignableFrom(int.class)
				|| cls.isAssignableFrom(short.class))
			return value.toString();
		return value.toString();
	}
	private static String exportDebug(Object value){
		if(value == null)
			return "Null()";
		Class<?> cls = value.getClass();
		if(JavaScriptObject.class.isAssignableFrom(cls))
			return toDebugString((JavaScriptObject)value);
		if(cls.isArray())
			return Arrays.stream((Object[])value)
					.map(JavaScriptObject::exportDebug)
					.collect(Collectors.joining(", ", "Array([", "])"));
		return cls.getSimpleName() + "(" + export(value) + ")";
	}
}
