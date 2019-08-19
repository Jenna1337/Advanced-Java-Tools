package utils.tuples;

public class Triplet<A,B,C> extends Pair<A,B>
{
	public Triplet(A a, B b, C c){
		super(a,b);
		list.add(c);
	}
	
	public C getThird(){
		return list.getCasted(2);
	}
}
