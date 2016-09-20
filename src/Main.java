import javax.swing.JFrame;
import sys.System;
import sys.math.Math11;
import sys.math.numbertypes.SuperNumber;
import utils.ClassUtils;



/***************************
 * WF AP CS
 * Jonah Sloan
 * Class: main
 * Project: 
 **************************/
@SuppressWarnings("unused")
public class Main
{
	public static void main(String[] args)
	{
		SuperNumber E = new SuperNumber(SuperNumber.E);
		SuperNumber Pi = new SuperNumber(SuperNumber.Pi);
		System.out.println(Math11.eval("trunc("+Pi+")"));
		//int i=0, n=2, k=n;
		//while(n/2!=n)
		//	System.out.println(++i+" "+(n/=2));
		//System.out.println((double)i/k);
		//System.logStart();
		//                0 1 2 3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27
		//Integer[] ints = {1,2,6,9,13,15,16,19,24,31,56,58,61,68,75,76,77,79,81,83,84,86,89,90,93,94,98,99};
		//System.out.println(System.binarySearch(ints, 78));
		//h.w();
		//Paint.colorTest();;
		//EasyPanelTest.test();
		//Paint.colorTest();
		//new Paint(256, 255);
		//int[] test = {1,2,3,4,5};
		//String[] l = {"1","2","3","4","5"};
		//System.out.println(java.util.Arrays.toString(test));
		//KeyGetter kg = new KeyGetter(new JFrame(),l, test);
		//test = kg.getNewKeys();
		//kg.close();
		//System.out.println(java.util.Arrays.toString(test));
		//new EtchASketch(256, 255);
		//new BitNumber("3");
		//System.out.println("1234"+'\u9829');
		//System.logExit(0);
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
