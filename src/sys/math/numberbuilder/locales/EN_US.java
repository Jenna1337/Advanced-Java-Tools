package sys.math.numberbuilder.locales;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sys.math.numberbuilder.AbstractNumberBuilder;

public class EN_US extends AbstractNumberBuilder
{
	/*
	 * zero
 one two three four five six seven eight nine
 ten eleven twelve thirteen fourteen fifteen sixteen seventeen eighteen nineteen
 twenty thirty forty fifty sixty seventy eighty ninety hundred thousand
 mi bi tri quadri quinti sexti septi octi noni
 un duo tre quattuor quin sex septen octo novem
 dec vigin trigin quadragin quinquagin sexagin septuagin octogin nonagin
 cen duocen trecen quadringen quingen sescen septingen octingen nongen
 millia

	 */
	private static enum Ones{
		Zero,
		One,
		Two,
		Three,
		Four,
		Five,
		Six,
		Seven,
		Eight,
		Nine,
		Ten,
		Eleven,
		Twelve,
		Thirteen,
		Fourteen,
		Fifteen,
		Sixteen,
		Seventeen,
		Eighteen,
		Nineteen;
		
		public String toString()
		{
			return this.equals(Zero) ? "" : this.name();
		}
	}
	private static enum Tens{
		NONE,
		NULL,
		Twenty,
		Thirty,
		Fourty,
		Fifty,
		Sixty,
		Seventy,
		Eighty,
		Ninety;
		public String toString()
		{
			if(this.equals(NULL))
				throw new NullPointerException();
			return this.equals(NONE) ? "" : this.name();
		}
	}
	private static enum MilSpec{
		Hundred,
		Thousand,
		Millia;
	}
	private static enum MilFirst{
		Error,
		Mi,
		Bi,
		Tri,
		Quadri,
		Quinti,
		Sexti,
		Septi,
		Octi,
		Noni;
		public String toString()
		{
			return this.equals(Error) ? "" : this.name();
		}
	}
	private static enum MilOnes{
		Error,
		Un,
		Duo,
		Tre,
		Quattuor,
		Quin,
		Sex,
		Septen,
		Octo,
		Novem;
		public String toString()
		{
			return this.equals(Error) ? "" : this.name();
		}
	}
	private static enum MilTens{
		Error,
		Dec,
		Vigin,
		Trigin,
		Quadragin,
		Quinquagin,
		Sexagin,
		Septuagin,
		Octogin,
		Nonagin;
		public String toString()
		{
			return this.equals(Error) ? "" : this.name();
		}
	}
	private static enum MilHundreds{
		Error,
		Cen,
		Duocen,
		Trecen,
		Quadringen,
		Quingen,
		Sescen,
		Septingen,
		Octingen,
		Nongen;
		public String toString()
		{
			return this.equals(Error) ? "" : this.name();
		}
	}
	private static String getSet3(String str3)
	{
		if(str3.length()!=3)
			throw new IllegalArgumentException();
		int hundred=Character.digit(str3.charAt(0),10),
				ten=Character.digit(str3.charAt(1),10),
				one=Character.digit(str3.charAt(2),10);
		assert hundred>=0 && hundred<10;
		assert ten>=0 && ten<10;
		assert one>=0 && one<10;
		String out="";
		try{
			if(hundred!=0)
				out+=Ones.values()[hundred]+" "+getSetMil(-1)+" ";
		}
		catch(ArrayIndexOutOfBoundsException aioobe){
			
		}
		try{
			out+=Ones.values()[ten*10+one].toString();
		}
		catch(ArrayIndexOutOfBoundsException aioobe){
			out+=Tens.values()[ten].toString()+" "+Ones.values()[one].toString();
		}
		return out.trim();
	}
	private static String getSetMil(int z){
		try{
			if(z<-1)
				throw new IllegalArgumentException(""+z);
			if(z<1)
				return MilSpec.values()[z+1].toString();
			if(z<10)
				return MilFirst.values()[z].toString()+"llion";
		}
		catch(ArithmeticException ae){}
		String ostr="";
		do
		{
			int o=z%10;
			int t=z/10%10;
			int h=z/100%10;
			String word = MilHundreds.values()[h].toString()+
					MilOnes.values()[o].toString()+
					MilTens.values()[t].toString();
			ostr=word+ostr;
			if(z>=1000)
				ostr=MilSpec.values()[2]+ostr;
			z=z/1000;
		}
		while(z>0);
		
		ostr=ostr.toLowerCase().replaceAll("unmillia", "millia");
		if(!ostr.toLowerCase().endsWith("dec"))
			ostr+="t";
		return (ostr+"illion");
	}
	private static Matcher REGEX_ZERO = Pattern.compile("0*").matcher(""),
		REGEX_ZEROS_ONE = Pattern.compile("0*1").matcher(""),
		REGEX_NUMBER = Pattern.compile("-?\\d*\\.?\\d*").matcher("");
	private static boolean matchesRegex(String text, Matcher regx) {
		return regx.reset(text).matches();
	}
	public String wordify(String val)
	{
		if(!matchesRegex(val, REGEX_NUMBER))
			throw new NumberFormatException("\""+val+"\" is not a valid number.");
		boolean isNegative;
		if(isNegative = (val.charAt(0)=='-'))
			val=val.substring(1);
		String[] parts = val.split("\\.");
		return (isNegative?"negative ":"")+getNamePart(parts[0])+(parts.length>1?getNamePartDecimal(parts[1]):"");
	}
	private static String getNamePart(String pln)
	{
		if(matchesRegex(pln,REGEX_ZERO))
			return "zero";
		String text=""+pln.toString();
		String ostr="";
		for(int i=text.length(), z=0;; i-=3, ++z)
		{
			try{
				ostr=getSet3(text.substring(i-3, i))+ostr;
				if(i-3>0)
				{
					String test;
					try{
						test=text.substring(i-6, i-3);
					}
					catch(StringIndexOutOfBoundsException sioobe){
						try{
							test="0"+text.substring(i-5, i-2);
						}
						catch(StringIndexOutOfBoundsException sioobe2){
							try{
								test="00"+text.substring(i-4, i-1);
							}
							catch(StringIndexOutOfBoundsException sioobe3){
								test="000";
							}
						}
					}
					if(!test.equalsIgnoreCase("000"))
						ostr=" "+getSetMil(z)+" "+ostr;
				}
			}
			catch(StringIndexOutOfBoundsException sioobe){
				try{
					ostr=getSet3("0"+text.substring(i-2, i))+ostr;
				}
				catch(StringIndexOutOfBoundsException sioobe2){
					try{
						ostr=getSet3("00"+text.substring(i-1, i))+ostr;
					}
					catch(StringIndexOutOfBoundsException sioobe3){
						break;
					}
				}
			}
		}
		return ostr.trim().toLowerCase();
	}
	private String getNamePartDecimal(String val) {
		if(val.length()<=0)
			return "";
		String zval;
		{
			char[]  chrs = new char[val.length()+1];
			chrs[0] = '1';
			for (int i = 1, len = chrs.length; i < len; i++)
				chrs[i] = '0';
			zval = new String(chrs);
		}
		String zres = getName(zval).replaceFirst("^one ", "").replaceAll(" ", "-").trim()+"th";
		if(!matchesRegex(val, REGEX_ZEROS_ONE))
			zres+="s";
		return " and "+getName(val)+" "+zres;
	}
}
