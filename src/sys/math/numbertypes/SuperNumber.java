package sys.math.numbertypes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import sys.math.SuperNumberConstants;
import sys.math.bigdecimal.BigDecimalMath;
import sys.math.interfaces.MathFunctions;
import sys.math.interfaces.TrigFunctions;

public final class SuperNumber extends Number implements Comparable<Number>, MathFunctions, TrigFunctions
{
	public final static int 
	ROUND_CEILING = (java.math.BigDecimal.ROUND_CEILING),
	ROUND_FLOOR = (java.math.BigDecimal.ROUND_FLOOR),
	ROUND_HALF_UP = (java.math.BigDecimal.ROUND_HALF_UP),
	ROUND_HALF_DOWN = (java.math.BigDecimal.ROUND_HALF_DOWN);
	protected static final int SCALE = 100;//TODO make this work with 1000
	protected static final int ROUNDING_MODE = ROUND_HALF_DOWN;
	protected static final MathContext context = new MathContext(SCALE, RoundingMode.valueOf(ROUNDING_MODE));
	public static final SuperNumber GoldenRatio = new SuperNumber(SuperNumberConstants.GoldenRatio),
			Pi = new SuperNumber(SuperNumberConstants.Pi.round(context)),
			E = new SuperNumber(SuperNumberConstants.E.round(context)),
			EulerGamma = new SuperNumber(SuperNumberConstants.EulerGamma.round(context)),
			DegreesInRadian = new SuperNumber(SuperNumberConstants.DegreesInRadian.round(context)),
			RadiansInDegree = new SuperNumber(SuperNumberConstants.RadiansInDegree.round(context));
	
	private final BigDecimal value;
	
	/*public SuperNumber()
	{
		this(1);
	}*/
	public SuperNumber(Number n)
	{
		this(n.toString());
	}
	public SuperNumber(String val)
	{
		value=new BigDecimal(val);
	}
	public static SuperNumber parse(String val)
	{
		return new SuperNumber(val);
	}
	public static SuperNumber parse(Number n)
	{
		return parse(n.toString());
	}
	public SuperNumber add(Number n)
	{
		return parse(this.toBigDecimal().add(new BigDecimal(n.toString())));
	}
	public SuperNumber subtract(Number n)
	{
		return parse(this.toBigDecimal().subtract(new BigDecimal(n.toString())));
	}
	public SuperNumber multiply(Number n)
	{
		return parse(this.toBigDecimal().multiply(new BigDecimal(n.toString())));
	}
	public SuperNumber divide(Number n)
	{
		return parse(this.toBigDecimal().divide(new BigDecimal(n.toString()), SCALE, ROUNDING_MODE).stripTrailingZeros());
	}
	public SuperNumber remainder(Number n)
	{
		return parse(this.toBigDecimal().remainder(new BigDecimal(n.toString())));
	}
	public SuperNumber mod(Number n)
	{
		return remainder(n);//parse(this.toBigDecimal().mod(new BigDecimal(n.toString())));
	}
	public SuperNumber negate()
	{
		return this.multiply(-1);
	}
	public SuperNumber abs()
	{
		return this.lessThan(0) ? this.negate() : this;
	}
	public int signum()
	{
		return this.equals(0)?0:(this.greaterThan(0)?1:-1);
	}
	public boolean equals(Number n)
	{
		return this.compareTo(n)==0;
	}
	public boolean greaterThan(Number n)
	{
		return this.compareTo(parse(n))>0;
	}
	public boolean lessThan(Number n)
	{
		return this.compareTo(parse(n))<0;
	}
	public boolean greaterThanEquals(Number n)
	{
		return this.compareTo(parse(n))>=0;
	}
	public boolean lessThanEquals(Number n)
	{
		return this.compareTo(parse(n))<=0;
	}
	public SuperNumber max(Number n)
	{
		return this.greaterThanEquals(n) ? parse(""+this) : parse(n);
	}
	public SuperNumber min(Number n)
	{
		return this.lessThanEquals(n) ? parse(""+this) : parse(n);
	}
	public double doubleValue()
	{
		return this.toBigDecimal().doubleValue();
	}
	public float floatValue()
	{
		return this.toBigDecimal().floatValue();
	}
	public byte byteValue()
	{
		return this.toBigDecimal().byteValue();
	}
	public short shortValue()
	{
		return this.toBigDecimal().shortValue();
	}
	public int intValue()
	{
		return this.toBigDecimal().intValue();
	}
	public long longValue()
	{
		return this.toBigDecimal().longValue();
	}
	/**
	 * @return {@code true} if this is equal to 0.
	 */
	public boolean booleanValue()
	{
		return this.equals(0);
	}
	public char charValue()
	{
		return (char)this.toBigDecimal().shortValue();
	}
	public int compareTo(Number n)
	{
		return this.toBigDecimal().compareTo(new BigDecimal(n.toString()));
	}
	public SuperNumber pow(Number num)
	{
		if(E.equals(num))
			return parse(num).exp();
		return parse(BigDecimalMath.pow(this.toBigDecimal(), parse(num).toBigDecimal()));
		//return powroot(num, true);
	}
	public SuperNumber root(Number num)
	{
		return powroot(num, false);
	}
	private SuperNumber powroot(Number n, boolean ispow)
	{
		int[] fraction = fractionize(n);
		//System.out.println((this.equals(E)?"E":this)+" "+(n.equals(E)?"E":n)+" "+ispow+" "+java.util.Arrays.toString(fraction)+"\n"+this.intpow(fraction[ispow ? 0 : 1]));
		return this.intpow(fraction[ispow ? 0 : 1])
				.introot(fraction[ispow ? 1 : 0]);
	}
	private int[] fractionize(Number num)
	{
		BigDecimal n=new BigDecimal(num.toString());
		String s = ""+n;
		int digitsDec = s.length() - 1 - s.indexOf('.');
		BigInteger bot = BigInteger.ONE;
		for (int i = 0; i < digitsDec; i++) {
			n=n.multiply(new BigDecimal("10"));
			bot=bot.multiply(new BigInteger("10"));
		}
		BigInteger top = n.round(new MathContext(SCALE, RoundingMode.valueOf(ROUND_HALF_UP))).toBigInteger();
		BigInteger g = top.gcd(bot);
		return new int[]{top.divide(g).intValue(), bot.divide(g).intValue()};
	}
	private SuperNumber intpow(int num)
	{
		try{
			return parse(new BigDecimal(this.round(context).toString()).pow(num, context));
		}catch(Exception e){return null;}
	}
	private SuperNumber introot(int num)
	{
		return parse(nthRoot(num, new BigDecimal(this.toString())).stripTrailingZeros());
	}
	//   snippet from http://stackoverflow.com/questions/22695654/computing-the-nth-root-of-p-using-bigdecimals 
	private static BigDecimal nthRoot(final int n, final BigDecimal a) {
		return nthRoot(n, a, BigDecimal.valueOf(.1).movePointLeft(SCALE));
	}
	private static BigDecimal nthRoot(final int n, final BigDecimal a, final BigDecimal p) {
		if (a.compareTo(BigDecimal.ZERO) < 0)
		{
			if(a.remainder(new BigDecimal("2")).compareTo(BigDecimal.ZERO)!=0)
				return nthRoot(n, a.negate(), p).negate();
			else
				throw new IllegalArgumentException("nth root can only be calculated for positive numbers");
		}
		if (n<=0)
			throw new IllegalArgumentException("nth root can only be calculated for positive numbers");
		if (a.equals(BigDecimal.ZERO))
			return BigDecimal.ZERO;
		BigDecimal xPrev = a;
		BigDecimal x = a.divide(new BigDecimal(n), SCALE, ROUNDING_MODE);
		while (x.subtract(xPrev).abs().compareTo(p) > 0) 
		{
			xPrev = x;
			x = BigDecimal.valueOf(n - 1.0).multiply(x).add(a.divide(x.pow(n - 1), SCALE, ROUNDING_MODE)).divide(new BigDecimal(n), SCALE, ROUNDING_MODE);
		}
		return x;
	}
	public SuperNumber sqrt()//square root
	{
		return root(2);
	}
	public SuperNumber cbrt()//cube root
	{
		return root(3);
	}
	public SuperNumber qrrt()//4th root
	{
		return root(4);
	}
	
	public String toString()
	{
		return this.toBigDecimal().toString();
	}
	public SuperNumber ceil()
	{
		return round(ROUND_CEILING);
	}
	public SuperNumber floor()
	{
		return round(ROUND_FLOOR);
	}
	public SuperNumber round()
	{
		return round(ROUND_HALF_UP);
	}
	public SuperNumber round(int roundtype)
	{
		return round(new MathContext(SCALE, RoundingMode.valueOf(roundtype)));
	}
	public SuperNumber round(MathContext roundmode)
	{
		return parse(this.toBigDecimal().round(roundmode));
	}
	public BigInteger toBigInteger()
	{
		return value.toBigInteger();
	}
	public BigDecimal toBigDecimal()
	{
		return value;
	}
	public SuperNumber factorial()
	{
		return this.add(1).gamma();
	}
	
	//trigonomics
	public SuperNumber sin()
	{
		return parse(BigDecimalMath.sin(this.toBigDecimal()));
	}
	public SuperNumber cos()
	{
		return parse(BigDecimalMath.cos(this.toBigDecimal()));
	}
	public SuperNumber tan()
	{
		return this.sin().divide(this.cos());
	}
	public SuperNumber csc()
	{
		return new SuperNumber(1).divide(this.sin());
	}
	public SuperNumber sec()
	{
		return new SuperNumber(1).divide(this.cos());
	}
	public SuperNumber cot()
	{
		return new SuperNumber(1).divide(this.tan());
	}
	//hyperbolics
	public SuperNumber sinh()
	{
		return parse(BigDecimalMath.sinh(this.toBigDecimal()));
	}
	public SuperNumber cosh()
	{
		return parse(BigDecimalMath.cosh(this.toBigDecimal()));
	}
	public SuperNumber tanh()
	{
		return parse(BigDecimalMath.tanh(this.toBigDecimal()));
	}
	public SuperNumber csch()
	{
		// csch(x) = 2*sinh(x)/(cosh(2 x)-1)
		return this.sinh().multiply(2).divide(this.multiply(2).cosh().subtract(1));
	}
	public SuperNumber sech()
	{
		// sech(x) = 2*cosh(x)/(cosh(2 x)+1)
		return this.cosh().multiply(2).divide(this.multiply(2).cosh().add(1));
	}
	public SuperNumber coth()
	{
		//coth(x) = sinh(2 x)/(cosh(2 x)-1)
		SuperNumber x2=this.multiply(2);
		return x2.sinh().divide(x2.cosh().subtract(1));
	}
	//inverses
	public SuperNumber arcsin()
	{
		return parse(BigDecimalMath.asin(this.toBigDecimal()));
	}
	public SuperNumber arccos()
	{
		// (pi-2*asin(x))/2
		return Pi.subtract(this.arcsin().multiply(2)).divide(2);
	}
	public SuperNumber arctan()
	{
		return parse(BigDecimalMath.atan(this.toBigDecimal()));
	}
	public SuperNumber arccsc()
	{
		return new SuperNumber(1).divide(this).arcsin();
	}
	public SuperNumber arcsec()
	{
		return new SuperNumber(1).divide(this).arccos();
	}
	public SuperNumber arccot()
	{
		return new SuperNumber(1).divide(this).arctan();
	}
	//inverse hyperbolics
	public SuperNumber arcsinh()
	{
		// arcsinh(x) = ln(x+sqrt(1+x^2))
		return this.add(this.pow(2).add(1).root(2)).ln();
	}
	public SuperNumber arccosh()
	{
		// arccosh(x) = ln(x+(1+x)*sqrt((x-1)/(x+1)))
		SuperNumber t1 = this.add(1);
		return this.subtract(1).divide(t1).root(2).multiply(t1).add(this).ln();
	}
	public SuperNumber arctanh()
	{
		// arctanh(x) = ln(sqrt(1+x)/sqrt(1-x))
		return this.add(1).root(2).divide(this.negate().add(1).root(2)).ln();
	}
	public SuperNumber arccsch()
	{
		return new SuperNumber(1).divide(this).arcsinh();
	}
	public SuperNumber arcsech()
	{
		return new SuperNumber(1).divide(this).arccosh();
	}
	public SuperNumber arccoth()
	{
		return new SuperNumber(1).divide(this).arctanh();
	}
	
	public SuperNumber ln()
	{
		return parse(BigDecimalMath.log(this.toBigDecimal()));
	}
	public SuperNumber exp()
	{
		return parse(BigDecimalMath.exp(this.toBigDecimal()));
	}
	public SuperNumber logBase(Number n)
	{
		return this.ln().divide(parse(n).ln());
	}
	public SuperNumber log10()
	{
		return this.logBase(10);
	}
	public SuperNumber gamma()
	{
		return parse(BigDecimalMath.Gamma(this.toBigDecimal()));
	}
}
