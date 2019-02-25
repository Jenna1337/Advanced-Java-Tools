package io;

import java.io.Flushable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class TablePrinter implements Flushable
{
	private final String collumnSeparator;
	Queue<String[]> queue = new LinkedList<String[]>();
	int maxlen = 0;
	ArrayList<Integer> maxlengths = new ArrayList<>();
	private Consumer<String> lineConsumer;
	
	public TablePrinter(Consumer<String> lineConsumer, String collumnSeparator){
		this.lineConsumer = lineConsumer;
		this.collumnSeparator = collumnSeparator;
	}
	
	public void println(Object... stuff){
		synchronized (this) {
			if(stuff==null)
				stuff = new Object[]{null};
			final int l = stuff.length;
			String[] strarr = new String[l];
			for(int i=0;i<l;++i){
				strarr[i] = stuff[i]==null ? "null" : stuff[i].toString();
				if(i>=maxlengths.size())
					maxlengths.add(i, strarr[i].length());
				else
					maxlengths.set(i, Math.max(maxlengths.get(i), strarr[i].length()));
			}
			queue.add(strarr);
			if(l>maxlen)
				maxlen = l;
		}
	}
	private String get(String[] a, int i){
		String str = a.length>i ? a[i] : "";
		int maxlen = maxlengths.get(i);
		while(str.length()<maxlen)
			str += ' ';
		return str;
	}
	public void flush(){
		synchronized (this) {
			queue.forEach(a->{
				String s = get(a, 0);
				for(int i=1;i<maxlen;++i)
					s += collumnSeparator + get(a, i);
				lineConsumer.accept(s);
			});
			queue.clear();
			maxlengths.clear();
		}
	}
}
