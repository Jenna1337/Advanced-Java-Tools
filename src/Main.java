import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JFrame;
import sys.DataBuffer;
import sys.System;
import sys.math.Math11;
import sys.math.numbertypes.SuperNumber;
import utils.CharacterUtils;
import utils.ClassUtils;
import utils.DLLUtils;
import utils.FileUtils;

@SuppressWarnings("unused")
public class Main
{
	public static void main(String[] args) throws Exception
	{
		new DataBuffer().toArray(new Integer[0]);
		System.exit(0);
		SuperNumber E = new SuperNumber(SuperNumber.E);
		SuperNumber Pi = new SuperNumber(SuperNumber.Pi);
		System.out.println(Math11.eval("trunc(" + Pi + ")"));
		// KeyGetter kg = new KeyGetter(new JFrame(),l, test);
		// test = kg.getNewKeys();
		// kg.close();
		// System.out.println(java.util.Arrays.toString(test));
	}
}
