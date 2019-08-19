package utils.tuples;

import utils.GenericList;

public class Pair<A,B>
{
	protected GenericList list;
	
	public Pair(A a, B b){
		list = new GenericList(a,b);
	}
	
	public A getFirst(){
		return list.getCasted(0);
	}
	public B getSecond(){
		return list.getCasted(1);
	}
}
