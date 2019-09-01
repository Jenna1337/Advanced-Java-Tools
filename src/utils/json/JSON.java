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
		forloop: for(Path path : Files.list(Paths.get("json_test_parsing")).toArray(Path[]::new))
		{
			String filename = path.getFileName().toString();
			ExpectedOutcome expected = ExpectedOutcome.Impl;
			switch(filename.charAt(0)){
				case 'y':
					expected = ExpectedOutcome.Success;
					break;
				case 'n':
					expected = ExpectedOutcome.Fail;
					break;
				case 'i':
				default:
					expected = ExpectedOutcome.Impl;
					continue forloop;
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
				//TODO
				valid=false;
				System.err.print("SOE");
			}
			System.out.flush();
			System.err.flush();
			if(showResults_Passed && ((valid && expected.equals(ExpectedOutcome.Success))
					|| (!valid && expected.equals(ExpectedOutcome.Fail))))
				System.out.println("Pass\t\t\t" + filename + "\t\t\t" + result);
			else if(showResults_Failed && ((!valid && expected.equals(ExpectedOutcome.Success))
					|| (valid && expected.equals(ExpectedOutcome.Fail))))
				System.err.println("Fail\t\t\t" + filename + "\t\t\t\"" + Utils.escapeString(text) + "\"\t\t\t\"" + Utils.escapeString(result) + "\"");
			else if(showResults_ImplementationDependent){
				System.out.flush();
				System.err.flush();
				System.out.println((valid ? "Pass" : "Fail")+"\t\t\t" + filename + "\t\t\t" + result);
			}
			System.out.flush();
			if(e!=null
					&& ((!valid && expected.equals(ExpectedOutcome.Success))
							|| (valid && expected.equals(ExpectedOutcome.Fail))))
				e.printStackTrace();
			System.err.flush();
		};
	}
}
