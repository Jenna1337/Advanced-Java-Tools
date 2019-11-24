package utils.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import utils.Utils;

/**
 * 
 * @author Jenna
 *
 */
public class JSON
{
	private JSON(){}
	
	public static String stringify(Object obj){
		return JavaScriptObject.toString(obj);
	}
	/**
	 * <style>
	 * table,tr,th,td {
	 *   border: 1px solid black;
	 *   border-collapse: collapse;
	 * }
	 * th,td {
	 *   padding: 4px;
	 *   font-family: monospace;
	 * }
	 * </style>
	 * Converts a String in JSON to a Java object.<br>
	 * <br>
	 * The following conversions will take place:<br>
	 * <br><table>
	 * <tr><th> JS Type </th><th>           Java Type           </th></tr>
	 * <tr><td> Object  </td><td> {@linkplain JavaScriptObject} </td></tr>
	 * <tr><td> Array   </td><td> {@linkplain Object[]}         </td></tr>
	 * <tr><td> String  </td><td> {@linkplain String}           </td></tr>
	 * <tr><td> Number  </td><td> {@linkplain Double}           </td></tr>
	 * <tr><td> boolean </td><td> {@linkplain Boolean}          </td></tr>
	 * <tr><td> null    </td><td> {@linkplain null}             </td></tr>
	 * </table>
	 * @param json The String representing a JSON object
	 * @return The parsed object
	 * @throws MalformedJSONException
	 */
	public static <T> T parse(String json) throws MalformedJSONException{
		return Parser.parse(json);
	}
	private static enum ExpectedOutcome{
		Success,
		Fail,
		Impl
	}
	public static void testParser() throws IOException{
		testParser(true,true,true);
	}
	public static void testParser(final boolean showResults_Passed,
			final boolean showResults_Failed,
			final boolean showResults_ImplementationDependent)
					throws IOException{
		int totalPassed=0,totalFailed=0,totalImplPassed=0,totalImplFailed=0;
		forloop: for(Path path : Files.list(Paths.get("json_test_parsing")).toArray(Path[]::new))
		{
			String filename = path.getFileName().toString();
			ExpectedOutcome expected = ExpectedOutcome.Impl;
			switch(filename.charAt(0)){
				case 'y':
					if(!(showResults_Passed || showResults_Failed))
						continue forloop;
					expected = ExpectedOutcome.Success;
					break;
				case 'n':
					if(!(showResults_Passed || showResults_Failed))
						continue forloop;
					expected = ExpectedOutcome.Fail;
					break;
				case 'i':
				default:
					if(!showResults_ImplementationDependent)
						continue forloop;
					expected = ExpectedOutcome.Impl;
					break;
			}
			boolean valid;
			String result="";
			String text = Utils.rawBytesToString(Files.readAllBytes(path));
			Exception e = null;
			try{
				result = JSON.stringify(JSON.parse(text));
				valid=true;
			}catch(MalformedJSONException e2){
				valid=false;
				result = text;
				e=e2;
			}catch(StackOverflowError e2){
				valid=false;
				result="[ERROR]";
			}
			System.out.flush();
			System.err.flush();
			String s1 = text,
					s2 = result;
			if(s1.length()>50) s1=s1.substring(0, 50)+"...";
			if(s2.length()>50) s2=s2.substring(0, 50)+"...";
			boolean j=false,k=false,l=false;
			if(j=((valid && expected.equals(ExpectedOutcome.Success))
					|| (!valid && expected.equals(ExpectedOutcome.Fail)))){
				if(j&=showResults_Passed)
					System.out.print("Pass");
				totalPassed++;
			}
			else if(k=((!valid && expected.equals(ExpectedOutcome.Success))
					|| (valid && expected.equals(ExpectedOutcome.Fail)))){
				if(k&=showResults_Failed)
					System.err.print("Fail");
				totalFailed++;
			}
			else if(l=expected.equals(ExpectedOutcome.Impl)){
				System.out.flush();
				System.err.flush();
				if(valid){
					if(l&=showResults_ImplementationDependent)
						System.out.print("Pass");
					totalImplPassed++;
				}
				else{
					if(l&=showResults_ImplementationDependent)
						System.out.print("Fail");
					totalImplFailed++;
				}
			}
			if(j||k||l)
				System.out.println(valid + "\t\t\t" + filename + "\t\t\t" + s1 + "\t\t" + s2 + "");
			
			System.out.flush();
			if(e!=null
					&& ((!valid && expected.equals(ExpectedOutcome.Success))
							|| (valid && expected.equals(ExpectedOutcome.Fail))))
				e.printStackTrace();
			System.err.flush();
		}
		System.out.println();
		System.out.println("Results:");
		if(showResults_Passed || showResults_Failed){
			System.out.println("Passed: "+totalPassed);
			System.out.println("Failed: "+totalFailed);
		}
		if(showResults_ImplementationDependent){
			System.out.println("Implementation Dependent:");
			System.out.println("Passed: "+totalImplPassed);
			System.out.println("Failed: "+totalImplFailed);
		}
	}
}
