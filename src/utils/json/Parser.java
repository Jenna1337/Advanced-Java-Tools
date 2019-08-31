package utils.json;

import java.math.BigDecimal;
import java.util.HashMap;
import utils.Utils;
import utils.collections.GenericList;
import utils.tuples.Pair;

/**
 * 
 * @author Jenna
 *
 */
class Parser
{
	private static final HashMap<String,Object> literals = new HashMap<>();
	static {
		literals.put("true",true);
		literals.put("false",false);
		literals.put("null",null);
	}
	private enum ParentDataType{
		OBJECT,
		ARRAY,
		TOP
	}
	
	/* @formatter:off */
	
	public static <T> T parse(String jsonstring) throws MalformedJSONException{
		if(jsonstring.isEmpty())
			throw new MalformedJSONException(0, "Unexpected end of data");
		return parseValue(jsonstring.toCharArray());
	}
	private static <T> T parseValue(char[] json) throws MalformedJSONException{
		return Utils.castToOriginalType(parseValue(0, json, ParentDataType.TOP).getSecond());
	}
	/**
	 * @return A {@link Pair} containing the new value of {@code i} and the parsed value.
	 * @throws MalformedJSONException 
	 */
	private static Pair<Integer, ?> parseValue(int i, char[] json, ParentDataType parentType) throws MalformedJSONException{
		int ch=json[i=skipWhitespace(i, json, parentType)];
		
		Pair<Integer,?> valpair = null;
		switch(ch){
			case '[':
				//array
				valpair = parseArray(i, json);
				break;
			case '{':
				//object
				valpair = parseObject(i, json);
				break;
			case '\"':
				//string
				valpair = stringParse(i, json);
				break;
			default:
				if(ch=='-' || (ch>='0' && ch<='9'))
					valpair = parseNumber(i, json);
				else
					valpair = parseLiteral(i, json);
		}
		if(valpair==null)
			throw new MalformedJSONException(i, "Unexpected character");
		
		i=valpair.getFirst();
		if(i>=json.length){
			switch(parentType){
				case ARRAY:
					throw new MalformedJSONException(i, "End of data when ',' or ']' was expected");
				case OBJECT:
					throw new MalformedJSONException(i, "End of data after property value");
				case TOP:
					return valpair;
				default:
					throw new InternalError("This should never happen.");
			}
		}
		i=skipWhitespace(i, json, parentType);
		ch = i>=json.length ? -1 : json[i];
		switch(parentType){
			case OBJECT:
				if(ch!=',' && ch!='}')
					throw new MalformedJSONException(i, "Expected property name or '}'");
				break;
			case ARRAY:
				if(ch!=',' && ch!=']')
					throw new MalformedJSONException(i, "Expected property name or '}'");
				break;
			case TOP:
				if(ch!=-1)
					throw new MalformedJSONException(i, "Unexpected character");
				break;
			default:
				throw new InternalError("This should never happen.");
		}
		return valpair;
	}
	private static Pair<Integer, JavaScriptObject> parseObject(int start, char[] json) throws MalformedJSONException{
		JavaScriptObject obj = new JavaScriptObject();
		
		String currentkey = null;
		int i,ch;
		forloop: for(i=start+1; i<json.length; ++i){
			ch=json[i=skipWhitespace(i, json, ParentDataType.OBJECT)];
			if(ch=='}')
				break forloop;
			if(ch!='\"')
				throw new MalformedJSONException(i, "Expected property name or '}'");
			{
				Pair<Integer,String> keypair = stringParse(i,json);
				i=keypair.getFirst();
				if(i>=json.length)
					throw new MalformedJSONException(i, "End of data after property name when ':' was expected");
				ch=json[i];
				currentkey = keypair.getSecond();
			}
			
			//colon
			i=skipWhitespace(i, json, ParentDataType.OBJECT);
			ch=json[i];
			if(ch!=':')
				throw new MalformedJSONException(i, "Expected ':' after property name in object");
			i=skipWhitespace(i+1, json, ParentDataType.OBJECT);
			if(i>=json.length)
				throw new MalformedJSONException(i, "Unexpected end of data");
			ch=json[i];
			
			//value
			Pair<Integer, ?> valpair = parseValue(i, json, ParentDataType.OBJECT);
			i = valpair.getFirst();
			ch=json[i];
			obj.put(currentkey, valpair.getSecond());
			
			i=skipWhitespace(i, json, ParentDataType.OBJECT);
			ch=json[i];
			
			if(ch=='}')
				return new Pair<>(i+1, obj);
			if(ch==',')
				continue;
			throw new MalformedJSONException(i, "Expected ',' or '}' after property value in object");
		}
		throw new MalformedJSONException(i, "End of data while reading object contents");
	}
	private static Pair<Integer, ?> parseLiteral(int start, char[] json) throws MalformedJSONException{
		if(literals.keySet().parallelStream().noneMatch(keyword->keyword.toString().charAt(0)==json[start]))
			throw new MalformedJSONException(start, "Unexpected character");
		int ch,i=start;
		do{
			++i;
			if(i>=json.length){
				i+=1;
				break;
			}
			ch=json[i];
		}
		while(ch>='a' && ch <='z');
		String key = extractData(json, start, i-1, String::new, 0).getSecond();
		
		if(!literals.containsKey(key))
			throw new MalformedJSONException(start, "Unexpected keyword");
		Object literal = literals.get(key);
		return new Pair<Integer, Object>(i-1, literal);
	}
	private static Pair<Integer, Double> parseNumber(int start, char[] json) throws MalformedJSONException{
		int i=start, ch;
		
		ch = json[i];
		if(ch=='-'){
			++i;
			if(i>=json.length)
				throw new MalformedJSONException(i, "No number after minus sign");
			ch = json[i];
		}
		
		if(ch=='0'){
			++i;
			ch= i<json.length ? json[i] : -1;
		}
		else if(ch>='1' && ch<='9'){
			++i;
			if(i>=json.length)
				ch=-2;
			else{
				ch=json[i];
				while(ch>='0' && ch<='9'){
					++i;
					ch= i<json.length ? json[i] : -1;
				}
			}
		}
		else
			throw new MalformedJSONException(i, "Unexpected non-digit");
		
		if(ch=='.'){
			++i;
			if(i>=json.length)
				throw new MalformedJSONException(i, "Missing digits after decimal point");
			ch=json[i];
			if(ch>='0' && ch<='9'){
				while(ch>='0' && ch<='9'){
					++i;
					ch= i<json.length ? json[i] : -1;
				}
			}
			else
				throw new MalformedJSONException(i, "Unterminated fractional number");
		}
		
		if(ch=='e' || ch=='E'){
			++i;
			if(i>=json.length)
				throw new MalformedJSONException(i, "Missing digits after exponent indicator");
			ch=json[i];
			if(ch=='+' || ch=='-'){
				++i;
				if(i>=json.length)
					throw new MalformedJSONException(i, "Exponent part is missing a number");
				ch=json[i];
			}
			if(ch>='0' && ch<='9'){
				while(ch>='0' && ch<='9'){
					++i;
					if(i>=json.length)
						ch=-2;
					else
						ch= i<json.length ? json[i] : -1;
				}
			}
			else
				throw new MalformedJSONException(i, "Exponent part is missing a number");
		}
		
		return extractData(json, start, ch==-1 ? i-1 : i, chars -> new Double(new String(chars)), 0);
	}
	private static Pair<Integer, Object[]> parseArray(int start, char[] json) throws MalformedJSONException{
		GenericList list = new GenericList();
		int i=start+1;
		if(i>=json.length)
			throw new MalformedJSONException(i,"Unexpected end of data");
		int ch=json[i];
		
		for(; i<json.length; ++i){
			i=skipWhitespace(i, json, ParentDataType.ARRAY);
			ch=json[i];
			if(ch==']' && list.size()==0){
				return new Pair<Integer, Object[]>(i+1, list.toArray(new Object[list.size()]));
			}
			Pair<Integer, ?> value = parseValue(i,json,ParentDataType.ARRAY);
			list.add(value.getSecond());
			i=value.getFirst();
			if(i>=json.length)
				throw new MalformedJSONException(i,"End of data when ',' or ']' was expected");
			i=skipWhitespace(i, json, ParentDataType.ARRAY);
			ch=json[i];
			if(ch==']'){
				return new Pair<Integer, Object[]>(i+1, list.toArray(new Object[list.size()]));
			}
			if(ch!=',')
				throw new MalformedJSONException(i,"End of data when ',' or ']' was expected");
		}
		throw new MalformedJSONException(i,"Unexpected end of data");
	}
	private static Pair<Integer, String> stringParse(int start, char[] json) throws MalformedJSONException{
		int end=-1;
		boolean escaped = false;
		start+=1;
		
		if(start+1>json.length)
			throw new MalformedJSONException(end,"Unterminated string literal");
		
		for(end=start;end<json.length;++end){
			char ch=json[end];
			if(escaped)
				escaped = false;
			else if(ch=='\\' && !escaped)
				escaped=true;
			else if(ch=='\"' && !escaped)
				return extractData(json, start, end, String::new, 1);
		}
		throw new MalformedJSONException(end,"Unterminated string");
	}
	private static <T> Pair<Integer, T> extractData(char[] json, int start, int end, JSONParsingFunction<char[], T> func, int endoffset) throws MalformedJSONException{
		int length = end-start;
		char[] chardata = new char[length];
		System.arraycopy(json, start, chardata, 0, length);
		return new Pair<Integer, T>(end+endoffset, func.apply(chardata));
	}
	private static int skipWhitespace(int i, char[] json, ParentDataType parentType) throws MalformedJSONException{
		if(i>=json.length)
			return i;
		char ch=json[i];
		while(ch=='\t' || ch=='\n' || ch=='\r' || ch==' '){
			++i;
			if(i>=json.length){
				if(parentType.equals(ParentDataType.TOP))
					throw new MalformedJSONException(i,"Unexpected end of data");
				return i;
			}
			ch=json[i];
		}
		return i;
	}
	
	/* @formatter:on */
}