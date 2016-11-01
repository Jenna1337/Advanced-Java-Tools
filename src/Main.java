import java.io.File;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JFrame;
import sys.System;
import sys.math.Math11;
import sys.math.numbertypes.SuperNumber;
import utils.ClassUtils;
import utils.DLLUtils;
import utils.FileUtils;



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
		ArrayList<AccessDeniedException> failed = new ArrayList<AccessDeniedException>();
		ArrayList<File> dlls = new ArrayList<File>();
		//dlls.add(new File("C:/Windows/CCM/RCConfigRes.dll"));
		//dlls.addAll(java.util.Arrays.asList(FileUtils.getAllSubfiles(new File("C:/Program Files"), "res.dll")));
		//dlls.addAll(java.util.Arrays.asList(FileUtils.getAllSubfiles(new File("C:/Program Files (x86)"), "res.dll")));
		//dlls.addAll(java.util.Arrays.asList(FileUtils.getAllSubfiles(new File("C:/Windows"), "res.dll")));
		dlls.add(new File("C:/Program Files (x86)/Steam/steamapps/common/Risk of Rain/data.win"));
		for(File dll : dlls)
		{
			System.out.println(dll.getAbsolutePath());
			String copyto = "dll/"+dll.getAbsolutePath().substring(3).replaceAll("\\\\", "_");
			try
			{
				File topath = new File(copyto);
				DLLUtils.copyPNGsFromDLL(dll, copyto, false);
				if(topath.isDirectory() && topath.list().length<=0)
					topath.delete();
			}
			catch(AccessDeniedException e)
			{
				failed.add(e);
			}
		}
		for(AccessDeniedException reason : failed)
			System.out.println(reason);
		
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
