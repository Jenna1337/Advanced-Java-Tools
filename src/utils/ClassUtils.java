package utils;

import java.io.PrintStream;
import java.lang.reflect.*;
import java.util.ArrayList;

public class ClassUtils
{
	private ClassUtils()
	{
	}
	public static Field[] getFieldsOfType(Class<?> clazz, Class<?> type)
	{
		Field[] flds = clazz.getFields();
		ArrayList<Field> lst = new ArrayList<Field>();
		for(Field fld : flds)
			if(fld.getType().isAssignableFrom(type))
				lst.add(fld);
		return lst.toArray(new Field[lst.size()]);
		
	}
	public static void printFieldsAndValue(Object instance, Field[] fields, PrintStream out) throws IllegalArgumentException, IllegalAccessException, InstantiationException
	{
		for(Field f : fields)
			out.println(f + " = " + f.get(instance));
	}
	public static void printFieldsAndValue(Object instance, Field[] fields) throws IllegalArgumentException, IllegalAccessException, InstantiationException
	{
		printFieldsAndValue(instance, fields, System.out);
	}
	public static void printFieldsAndValue(Field[] fields, PrintStream out) throws IllegalArgumentException, IllegalAccessException, InstantiationException
	{
		printFieldsAndValue(null, fields, out);
	}
	public static void printFieldsAndValue(Field[] fields) throws IllegalArgumentException, IllegalAccessException, InstantiationException
	{
		printFieldsAndValue(null, fields, System.out);
	}
	
	@SuppressWarnings("unchecked")
	public static <C, X extends Throwable> Object invokeMethod(Class<? extends C> clazz,
			C obj, String name, Object... refpairs) throws X{
		ArrayList<Class<?>> clss = new ArrayList<>();
		ArrayList<Object> args = new ArrayList<>();
		for(int i = 0; i < refpairs.length; ++i){
			if(i % 2 == 0)
				clss.add((Class<?>)refpairs[i]);
			else
				args.add(refpairs[i]);
		}
		Class<?>[] aclss = clss.toArray(new Class<?>[clss.size()]);
		Object[] aargs = args.toArray(new Object[args.size()]);
		try{
			Object result = clazz.getMethod(name, aclss).invoke(obj,
					aargs);
			return result;
		}
		catch(java.lang.reflect.InvocationTargetException e){
			Throwable t = e.getCause();
			throw (X)t;
		}
		catch(IllegalAccessException | IllegalArgumentException
				| NoSuchMethodException | SecurityException e){
			throw new InternalError(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T, C, X extends Throwable> T invokeMethod(C obj,
			String name, Object... refpairs) throws X{
		return (T)invokeMethod(obj.getClass(), obj, name, refpairs);
	}
	
	public static <C, X extends Throwable> Object invokeStaticMethod(Class<? extends C> clazz,
			String name, Object... refpairs) throws X{
		return invokeMethod(clazz, null, name, refpairs);
	}
	
	@SuppressWarnings("unchecked")
	public static <C, X extends Throwable> C invokeConstructor(Class<C> clazz, Object... refpairs) throws X{
		ArrayList<Class<?>> clss = new ArrayList<>();
		ArrayList<Object> args = new ArrayList<>();
		for(int i = 0; i < refpairs.length; ++i){
			if(i % 2 == 0)
				clss.add((Class<?>)refpairs[i]);
			else
				args.add(refpairs[i]);
		}
		Class<?>[] aclss = clss.toArray(new Class<?>[clss.size()]);
		Object[] aargs = args.toArray(new Object[args.size()]);
		try{
			return clazz.getConstructor(aclss).newInstance(aargs);
		}
		catch(java.lang.reflect.InvocationTargetException e){
			Throwable t = e.getCause();
			throw (X)t;
		}
		catch(IllegalAccessException | IllegalArgumentException
				| NoSuchMethodException | SecurityException
				| InstantiationException e){
			throw new InternalError(e);
		}
	}
	
	/**
	 * Returns the value of the field in {@code rcls} that has the same name as the {@code enumVal} name.
	 * 
	 * @return The resulting enum value in the specified enum type.
	 * @param enumVal The initial enum value.
	 * @param rcls The resulting enum type class.
	 * @param <R> The resulting enum type. 
	 * @throws InternalError If an exception occurred. 
	 */
	@SuppressWarnings("unchecked")
	public static <R> R castEnumConstant(Object enumVal, Class<R> rcls){
		try{
			return (R)rcls.getDeclaredField(Enum.class.cast(enumVal).name()).get(null);
		}
		catch(IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e){
			// TODO Auto-generated catch block
			throw new InternalError(e);
		}
	}
}
