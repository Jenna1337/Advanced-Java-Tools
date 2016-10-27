package utils;

public class BooleanUtils
{
	private BooleanUtils(){}
	
	public static boolean or(boolean par1, boolean par2){return par1||par2;}
	public static boolean nor(boolean par1, boolean par2){return !(par1||par2);}
	public static boolean xor(boolean par1, boolean par2){return par1!=par2;}
	public static boolean and(boolean par1, boolean par2){return par1&&par2;}
	public static boolean nand(boolean par1, boolean par2){return !(par1&&par2);}
	public static boolean xand(boolean par1, boolean par2){return par1==par2;}
	public static boolean not(boolean par0){return !par0;}
	/**Returns true if par0 equals 1**/
	public static boolean parseBoolean(int par0){return par0==1;}
	
	public static boolean implies(boolean par1, boolean par2){return !par1||par2;}
}
