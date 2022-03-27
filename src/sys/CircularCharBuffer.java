package sys;

public class CircularCharBuffer //extends java.nio.CharBuffer
{
	char[] data;
	int head = 0;
	private boolean stringCacheInvalid = true;
	private String cachedString;
	
	public CircularCharBuffer(int size){
		data = new char[size];
	}
	
	public int size(){
		return data.length;
	}
	public CircularCharBuffer put(char item){
		data[head] = item;
		head = (head+1) % data.length;
		stringCacheInvalid = true;
		return this;
	}
	@Override
	public boolean equals(Object obj){
		return obj != null
				&& this.toString().equals(obj.toString());
	}
	@Override
	public String toString(){
		if(stringCacheInvalid){
			cachedString = new StringBuilder(data.length)
					.append(data, head, data.length-head)
					.append(data, 0, head)
					.toString();
			stringCacheInvalid = false;
		}
		return cachedString;
	}
}
