package utils;

import java.io.PrintStream;
import java.lang.reflect.*;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import sys.math.numbertypes.SuperNumber;

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
	
	public static <C, X extends Throwable> Object invokeMethod(C obj,
			String name, Object... refpairs) throws X{
		return invokeMethod(obj.getClass(), obj, name, refpairs);
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
	 * Tests the methods in the class.
	 * 
	 * @param clazz The {@link Class}
	 * @return {@code true} if all the methods completed successfully.
	 * @deprecated This method may call class methods with values that are
	 *             illegal for that method.
	 */
	@Deprecated
	public static boolean TestClass(Class<?> clazz)
	{
		boolean result = true;
		List<Method> methods = Arrays.asList(clazz.getMethods());
		methods.removeAll(Arrays.asList(clazz.getConstructors()));
		for(Method method : methods)
		{
			System.out
					.println(method.toString().replaceAll("[A-Za-z\\.]+\\.", "")
							.replaceAll(" throws [\\w ,]+", "")
							.replace("public ", "").replace("static ", "")
							.replace("final ", "").replace("native ", ""));
			result &= testMethod(method, clazz);
		}
		return result;
	}
	/**
	 * Tests the method in the class.
	 * 
	 * @param method The {@link Method} to test
	 * @param clazz The {@link Class} that contains {@code method}
	 * @return {@code true} if the method completed successfully.
	 * @deprecated This method may call the class method it is given with values
	 *             that are illegal for that method.
	 */
	@Deprecated
	public static boolean testMethod(Method method, Class<?> clazz)
	{
		// System.out.println(method.getDeclaringClass().getName());
		// assume these work properly
		if(method.toString().contains("wait")
				|| method.toString().contains("notify"))
			return true;
		try
		{
			Class<?>[] types = method.getParameterTypes();
			Object[] pars = new Object[types.length];
			System.out.println(Arrays.toString(types));
			for(int i = 0; i < pars.length; ++i)
			{
				try
				{
					pars[i] = types[i].newInstance();
				}
				catch(InstantiationException ie)
				{
					pars[i] = 3;
				}
				if(types[i].equals(String.class))
					pars[i] = "2";
				else if(types[i].equals(SuperNumber.class))
					pars[i] = new SuperNumber("2");
				else if(types[i].equals(Number.class))
					pars[i] = (Number) Integer.parseInt("2");
				else if(types[i].equals(MathContext.class))
					pars[i] = SuperNumber.ROUND_HALF_UP;
				else if(types[i].equals(Object.class))
					pars[i] = (Object) clazz.newInstance();
			}
			method.invoke(clazz.newInstance(), pars);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
			return false;
		}
	}
}
