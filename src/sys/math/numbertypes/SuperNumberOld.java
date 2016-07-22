package sys.math.numbertypes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

@Deprecated
public final class SuperNumberOld extends Number implements Comparable<Number>//, MathFunctions
{
	public SuperNumberOld(){this(1);}
	private BigDecimal value;
	public final static int 
	ROUND_CEILING = (java.math.BigDecimal.ROUND_CEILING),
	ROUND_FLOOR = (java.math.BigDecimal.ROUND_FLOOR),
	ROUND_HALF_UP = (java.math.BigDecimal.ROUND_HALF_UP),
	ROUND_HALF_DOWN = (java.math.BigDecimal.ROUND_HALF_DOWN);
	protected static final int SCALE = 1000;
	protected static final int ROUNDING_MODE = ROUND_HALF_DOWN;
	protected static final MathContext context = new MathContext(SCALE, RoundingMode.valueOf(ROUNDING_MODE));
	public SuperNumberOld(Number n)
	{
		this(n.toString());
	}
	public SuperNumberOld(String val)
	{
		value=new BigDecimal(val);
	}
	public static SuperNumberOld parse(String val)
	{
		return new SuperNumberOld(val);
	}
	public static SuperNumberOld parse(Number n)
	{
		return parse(n.toString());
	}
	public SuperNumberOld add(Number n)
	{
		return parse(value.add(new BigDecimal(n.toString())).round(context).stripTrailingZeros());
	}
	public SuperNumberOld subtract(Number n)
	{
		return parse(value.subtract(new BigDecimal(n.toString())).round(context).stripTrailingZeros());
	}
	public SuperNumberOld multiply(Number n)
	{
		return parse(value.multiply(new BigDecimal(n.toString())).round(context).stripTrailingZeros());
	}
	public SuperNumberOld divide(Number n)
	{
		return parse(value.divide(new BigDecimal(n.toString()), SCALE, ROUNDING_MODE).stripTrailingZeros());
	}
	public SuperNumberOld remainder(Number n)
	{
		return parse(value.remainder(new BigDecimal(n.toString())));
	}
	public SuperNumberOld mod(Number n)
	{
		return remainder(n);//parse(value.mod(new BigDecimal(n.toString())));
	}
	public SuperNumberOld negate()
	{
		return this.multiply(-1);
	}
	public SuperNumberOld abs()
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
	public SuperNumberOld max(Number n)
	{
		return this.greaterThanEquals(n) ? parse(""+this) : parse(n);
	}
	public SuperNumberOld min(Number n)
	{
		return this.lessThanEquals(n) ? parse(""+this) : parse(n);
	}
	public double doubleValue()
	{
		return value.doubleValue();
	}
	public float floatValue()
	{
		return value.floatValue();
	}
	public byte byteValue()
	{
		return value.byteValue();
	}
	public short shortValue()
	{
		return value.shortValue();
	}
	public int intValue()
	{
		return value.intValue();
	}
	public long longValue()
	{
		return value.longValue();
	}
	public int compareTo(Number n)
	{
		return value.compareTo(new BigDecimal(n.toString()));
	}
	public SuperNumberOld pow(Number num)
	{
		return powroot(num, true);
	}
	public SuperNumberOld root(Number num)
	{
		return powroot(num, false);
	}
	private SuperNumberOld powroot(Number n, boolean ispow)
	{
		int[] fraction = fractionize(n);
		//System.out.println(this+" "+(n)+" "+ispow+" "+java.util.Arrays.toString(fraction)+"\n"+this.intpow(fraction[ispow ? 0 : 1]));
		return this.intpow(fraction[ispow ? 0 : 1]).round(context)
				.introot(fraction[ispow ? 1 : 0]).round(context);
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
	private SuperNumberOld intpow(int num)
	{
		try{
			return parse(new BigDecimal(this.round(context).toString()).pow(num, context));
		}catch(Exception e){return null;}
	}
	private SuperNumberOld introot(int num)
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
	
	public String toString()
	{
		return value.toString();
	}
	public SuperNumberOld ceil()
	{
		return round(ROUND_CEILING);
	}
	public SuperNumberOld floor()
	{
		return round(ROUND_FLOOR);
	}
	public SuperNumberOld round()
	{
		return round(ROUND_HALF_UP);
	}
	public SuperNumberOld round(int roundtype)
	{
		return round(new MathContext(SCALE, RoundingMode.valueOf(roundtype)));
	}
	public SuperNumberOld round(MathContext roundmode)
	{
		return parse(value.round(roundmode));
	}
	public BigInteger toBigInteger()
	{
		return value.toBigInteger();
	}
	public BigDecimal toBigDecimal()
	{
		return value;
	}
	public SuperNumberOld sqrt()
	{
		return this.root(2);
	}
	public SuperNumberOld cbrt()
	{
		return this.root(3);
	}
	@Deprecated
	public SuperNumberOld factorial()
	{
		return this;
	}
}
