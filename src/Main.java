import java.io.File;
import javax.swing.JFrame;
import sys.System;
import sys.math.Math11;
import sys.math.numbertypes.SuperNumber;
import utils.ClassUtils;
import utils.DLLUtils;



/***************************
 * WF AP CS
 * Jonah Sloan
 * Class: main
 * Project: 
 **************************/
@SuppressWarnings("unused")
public class Main
{
	public static void main(String[] args) throws Exception
	{
		File dll = new File("C:/Windows/System32/imageres.dll");
		
		DLLUtils.copyPNGsFromDLL(dll, "imageres/", false);
		
		System.exit(0);
		SuperNumber E = new SuperNumber(SuperNumber.E);
		SuperNumber Pi = new SuperNumber(SuperNumber.Pi);
		System.out.println(Math11.eval("trunc("+Pi+")"));
		//KeyGetter kg = new KeyGetter(new JFrame(),l, test);
		//test = kg.getNewKeys();
		//kg.close();
		//System.out.println(java.util.Arrays.toString(test));
	}
}
class ExitHookC extends Thread
{
	long minPrime;
	ExitHookC(long minPrime)
	{
		this.minPrime = minPrime;
	}
	public void run()
	{
		System.log("Exiting");
	}
}
class h
{
	public static void w()
	{
		String text ="\u0048\u0065\u006c\u006c\u006f\u0020\u0057\u006f\u0072\u006c\u0064\u0021";
		System.out.println(text);
		System.log("Printed \""+text+"\"");
	}
}
