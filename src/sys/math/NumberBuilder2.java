package sys.math;

import java.math.BigInteger;
import utils.StringUtils;

public class NumberBuilder2
{
	private NumberBuilder2(){}
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
		@SuppressWarnings("hiding")
		ZERO,
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
			return this.equals(ZERO) ? "" : this.name();
		}
	}
	private static enum MilOnes{
		@SuppressWarnings("hiding")
		ZERO,
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
			return this.equals(ZERO) ? "" : this.name();
		}
	}
	private static enum MilTens{
		@SuppressWarnings("hiding")
		ZERO,
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
			return this.equals(ZERO) ? "" : this.name();
		}
	}
	private static enum MilHundreds{
		@SuppressWarnings("hiding")
		ZERO,
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
			return this.equals(ZERO) ? "" : this.name();
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
				out+=Ones.values()[hundred]+" "+getSetMil(NEGONE)+" ";
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
	public static final BigInteger ONE = BigInteger.ONE,
			ZERO = BigInteger.ZERO,
			NEGONE = ONE.negate(),
			TEN = BigInteger.TEN,
			HUNDRED = TEN.pow(2),
			THOUSAND = TEN.pow(3);
	private static String getSetMil(BigInteger zero3)
	{
		try{
			if(zero3.compareTo(NEGONE)<0)
				throw new IllegalArgumentException(""+zero3);
			if(zero3.compareTo(ONE)<0)
				return MilSpec.values()[zero3.intValueExact()+1].toString();
			if(zero3.compareTo(TEN)<0)
				return MilFirst.values()[zero3.intValueExact()].toString()+"llion";
		}
		catch(ArithmeticException ae){}
		String ostr="";
		do
		{
			int o=zero3.remainder(TEN).intValue();
			int t=zero3.divide(TEN).remainder(TEN).intValue();
			int h=zero3.divide(HUNDRED).remainder(TEN).intValue();
			//System.out.println(h+""+t+""+o);
			//hot
			String word = MilHundreds.values()[h].toString()+
					MilOnes.values()[o].toString()+
					MilTens.values()[t].toString();
			ostr=word+ostr;
			if(zero3.compareTo(THOUSAND)>=0)
				ostr=MilSpec.values()[2]+ostr;
			zero3=zero3.divide(THOUSAND);
		}
		while(zero3.compareTo(BigInteger.ZERO)>0);
		
		ostr=ostr.toLowerCase().replaceAll("unmillia", "millia");
		if(!ostr.toLowerCase().endsWith("dec"))
			ostr+="t";
		return StringUtils.titleCase(ostr+"illion");
	}
	public static String getName(java.math.BigDecimal val)
	{
		String pln=val.toPlainString();
		if(!pln.contains("."))
			return getName(new BigInteger(pln));
		
		String[] vals = pln.split("\\.");
		
		BigInteger ival = new BigInteger(vals[0]);
		BigInteger dval = new BigInteger(vals[1]);
		String zval = "1"+vals[1].replaceAll("\\d", "0");
		/*for(int i=0; i<vals[1].length(); ++i)
			zval+="0";*/
		if(zval.equals("1"))
			return getName(ival);
		String zres = getName(new BigInteger(zval)).replaceAll(" ", "-").trim()+"th";
		if(!dval.equals(ONE))
			zres+="s";
		return StringUtils.titleCase(getName(ival)+" and "+getName(dval)+" "+zres);
	}
	public static String getName(BigInteger val)
	{
		if(val.equals(ZERO))
			return "Zero";
		String text=""+val.toString();
		String ostr="";
		BigInteger z=ZERO;
		for(int i=text.length();; i-=3, z=z.add(ONE))
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
		return StringUtils.titleCase(ostr.trim());
	}
	public static String getName(Number val)
	{
		return getName(new java.math.BigDecimal(val.toString()));
	}
}
