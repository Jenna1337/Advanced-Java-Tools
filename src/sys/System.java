package sys;


import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import sys.math.Math11;

/***************************
 * <div class="block">The <code>System</code> class contains several useful class fields
 and methods. It cannot be instantiated.

 <p>Among the facilities provided by the <code>System</code> class
 are standard input, standard output, and error output streams;
 access to externally defined properties and environment
 variables; a means of loading files and libraries; and a utility
 method for quickly copying a portion of an array.</p></div>
 <dl><dt><span class="strong">Since:</span></dt>
  <dd>JDK1.0</dd></dl>
 **************************/
public class System
{
	protected static boolean logging=false;
	public static void logStart()
	{
		log = CreateLogPrintStream();
		logging = true;
		//log("Starting");
	}
	public static void logExit(int status)
	{
		//log("Exiting with exit code "+status);
		log.close();
		//System.wait(10.0);
		System.exit(status);
	}
	/**Time to wait, in seconds.**/
	public static void wait(Number d)
	{
		long l = d.longValue();
		long s = System.nanoTime();
		long c = System.nanoTime();
		while(s>=c-(l/0.000000001))
			c = System.nanoTime();
	}
	private static PrintStream log;
	/**Writes <code>text</code> to the log file.<br>
	 * <b>The method <code>logStart()</code> must be run before this method is run!</b>
	 * @throws Exception **/
	public static void log(String text) throws Exception
	{
		if(!logging)
			throw new Exception("Log not started. ");
		log.println(getDateTime()+" "+text);
	}
	public static String getDateTime()
	{
		return "";//new java.text.SimpleDateFormat("yyyyMMdd_HHmm-ss_SSS").format(System.currentTimeMillis());
	}
	private static PrintStream CreateLogPrintStream()
	{
		try
		{
			PrintStream logc = new PrintStream(new File("System_"+getDateTime()+".log"),"UTF-16");
			return logc;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	/**Prompts the user for input after printing the specified string.**/
	public static String prompt(String query) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(query);
		return reader.readLine();
	}
	
	/**Returns a random integer from the specified min value to the specified max value.**/
	public static int randomInt(int min, int max)
	{
		return (int)Math.floor(Math.random()*(max+1))+min;
	}
	
	/**Prints the current stack.**/
	public static void printStackTrace()
	{
		printStackTrace(out);
	}
	
	/**Prints the current stack.**/
	public static void printStackTrace(PrintWriter s)
	{
		StackTraceElement[] stack = new Throwable().getStackTrace();
		String str = "Current location:";
		for(int i=1; i<stack.length; ++i)
			str += System.lineSeparator() + "at " + stack[i].toString();
		s.println(str);
	}
	
	/**Prints the current stack.**/
	public static void printStackTrace(PrintStream s)
	{
		StackTraceElement[] stack = new Throwable().getStackTrace();
		String str = "Current location:";
		for(int i=1; i<stack.length; ++i)
			str += System.lineSeparator() + "at " + stack[i].toString();
		s.println(str);
	}
	
	/**Evaluates an equation.**/
	public static Object eval(String quest)
	{
		return Math11.eval(quest.trim()).toString();
	}
	
	
	
	
	
	
	
	/**
<h4>in</h4>
<pre>public static final&nbsp;<a title="class in java.io" href="../../java/io/InputStream.html">InputStream</a> in</pre>
<div class="block">The "standard" input stream. This stream is already
 open and ready to supply input data. Typically this stream
 corresponds to keyboard input or another input source specified by
 the host environment or user.</div>
	 **/
	public static final InputStream in = java.lang.System.in;
	/**
<h4>out</h4>
<pre>public static final&nbsp;<a title="class in java.io" href="../../java/io/PrintStream.html">PrintStream</a> out</pre>
<div class="block">The "standard" output stream. This stream is already
 open and ready to accept output data. Typically this stream
 corresponds to display output or another output destination
 specified by the host environment or user.
 <p>
 For simple stand-alone Java applications, a typical way to write
 a line of output data is:
 <blockquote><pre>     System.out.println(data)
 </pre></blockquote>
 <p>
 See the <code>println</code> methods in class <code>PrintStream</code>.</p></div>
<dl><dt><span class="strong">See Also:</span></dt><dd><a href="../../java/io/PrintStream.html#println()"><code>PrintStream.println()</code></a>, 
<a href="../../java/io/PrintStream.html#println(boolean)"><code>PrintStream.println(boolean)</code></a>, 
<a href="../../java/io/PrintStream.html#println(char)"><code>PrintStream.println(char)</code></a>, 
<a href="../../java/io/PrintStream.html#println(char[])"><code>PrintStream.println(char[])</code></a>, 
<a href="../../java/io/PrintStream.html#println(double)"><code>PrintStream.println(double)</code></a>, 
<a href="../../java/io/PrintStream.html#println(float)"><code>PrintStream.println(float)</code></a>, 
<a href="../../java/io/PrintStream.html#println(int)"><code>PrintStream.println(int)</code></a>, 
<a href="../../java/io/PrintStream.html#println(long)"><code>PrintStream.println(long)</code></a>, 
<a href="../../java/io/PrintStream.html#println(java.lang.Object)"><code>PrintStream.println(java.lang.Object)</code></a>, 
<a href="../../java/io/PrintStream.html#println(java.lang.String)"><code>PrintStream.println(java.lang.String)</code></a></dd></dl>
	 */
	public static final PrintStream out = new PrintStream(java.lang.System.out);
	/**
<h4>err</h4>
<pre>public static final&nbsp;<a title="class in java.io" href="../../java/io/PrintStream.html">PrintStream</a> err</pre>
<div class="block">The "standard" error output stream. This stream is already
 open and ready to accept output data.
 <p>
 Typically this stream corresponds to display output or another
 output destination specified by the host environment or user. By
 convention, this output stream is used to display error messages
 or other information that should come to the immediate attention
 of a user even if the principal output stream, the value of the
 variable <code>out</code>, has been redirected to a file or other
 destination that is typically not continuously monitored.</p></div>
	 */
	public static final PrintStream err = new PrintStream(java.lang.System.err);
	
	/**
<h4>setIn</h4>
<pre>public static&nbsp;void&nbsp;setIn(<a title="class in java.io" href="../../java/io/InputStream.html">InputStream</a>&nbsp;in)</pre>
<div class="block">Reassigns the "standard" input stream.

 <p>First, if there is a security manager, its <code>checkPermission</code>
 method is called with a <code>RuntimePermission("setIO")</code> permission
  to see if it's ok to reassign the "standard" input stream.
 <p></p></div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>in</code> - the new standard input stream.</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its
        <code>checkPermission</code> method doesn't allow
        reassigning of the standard input stream.</dd><dt><span class="strong">Since:</span></dt>
  <dd>JDK1.1</dd>
<dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/SecurityManager.html#checkPermission(java.security.Permission)"><code>SecurityManager.checkPermission(java.security.Permission)</code></a>, 
<a title="class in java.lang" href="../../java/lang/RuntimePermission.html"><code>RuntimePermission</code></a></dd></dl>
	 */
	public static void setIn(InputStream in)
	{
		java.lang.System.setIn(in);
	}
	/**
<h4>setOut</h4>
<pre>public static&nbsp;void&nbsp;setOut(<a title="class in java.io" href="../../java/io/PrintStream.html">PrintStream</a>&nbsp;out)</pre>
<div class="block">Reassigns the "standard" output stream.

 <p>First, if there is a security manager, its <code>checkPermission</code>
 method is called with a <code>RuntimePermission("setIO")</code> permission
  to see if it's ok to reassign the "standard" output stream.</p></div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>out</code> - the new standard output stream</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its
        <code>checkPermission</code> method doesn't allow
        reassigning of the standard output stream.</dd><dt><span class="strong">Since:</span></dt>
  <dd>JDK1.1</dd>
<dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/SecurityManager.html#checkPermission(java.security.Permission)"><code>SecurityManager.checkPermission(java.security.Permission)</code></a>, 
<a title="class in java.lang" href="../../java/lang/RuntimePermission.html"><code>RuntimePermission</code></a></dd></dl>
	 */
	public static void setOut(PrintStream out)
	{
		java.lang.System.setOut(out);
	}
	/**
<h4>setErr</h4>
<pre>public static&nbsp;void&nbsp;setErr(<a title="class in java.io" href="../../java/io/PrintStream.html">PrintStream</a>&nbsp;err)</pre>
<div class="block">Reassigns the "standard" error output stream.

 <p>First, if there is a security manager, its <code>checkPermission</code>
 method is called with a <code>RuntimePermission("setIO")</code> permission
  to see if it's ok to reassign the "standard" error output stream.</p></div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>err</code> - the new standard error output stream.</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its
        <code>checkPermission</code> method doesn't allow
        reassigning of the standard error output stream.</dd><dt><span class="strong">Since:</span></dt>
  <dd>JDK1.1</dd>
<dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/SecurityManager.html#checkPermission(java.security.Permission)"><code>SecurityManager.checkPermission(java.security.Permission)</code></a>, 
<a title="class in java.lang" href="../../java/lang/RuntimePermission.html"><code>RuntimePermission</code></a></dd></dl>
	 */
	public static void setErr(PrintStream err)
	{
		java.lang.System.setErr(err);
	}
	/**
<h4>console</h4>
<pre>public static&nbsp;<a title="class in java.io" href="../../java/io/Console.html">Console</a>&nbsp;console()</pre>
<div class="block">Returns the unique <a title="class in java.io" href="../../java/io/Console.html"><code>Console</code></a> object associated
 with the current Java virtual machine, if any.</div>
<dl><dt><span class="strong">Returns:</span></dt><dd>The system console, if any, otherwise <tt>null</tt>.</dd><dt><span class="strong">Since:</span></dt>
  <dd>1.6</dd></dl>
	 */
	public static Console console()
	{
		return java.lang.System.console();
	}
	/**
<h4>inheritedChannel</h4>
<pre>public static&nbsp;<a title="interface in java.nio.channels" href="../../java/nio/channels/Channel.html">Channel</a>&nbsp;inheritedChannel()
                                throws <a title="class in java.io" href="../../java/io/IOException.html">IOException</a></pre>
<div class="block">Returns the channel inherited from the entity that created this
 Java virtual machine.

 <p> This method returns the channel obtained by invoking the
 <a href="../../java/nio/channels/spi/SelectorProvider.html#inheritedChannel()"><code>inheritedChannel</code></a> method of the system-wide default
 <a title="class in java.nio.channels.spi" href="../../java/nio/channels/spi/SelectorProvider.html"><code>SelectorProvider</code></a> object. </p>

 <p> In addition to the network-oriented channels described in
 <a href="../../java/nio/channels/spi/SelectorProvider.html#inheritedChannel()"><code>inheritedChannel</code></a>, this method may return other kinds of
 channels in the future.</p></div>
<dl><dt><span class="strong">Returns:</span></dt><dd>The inherited channel, if any, otherwise <tt>null</tt>.</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.io" href="../../java/io/IOException.html">IOException</a></code> - If an I/O error occurs</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - If a security manager is present and it does not
          permit access to the channel.</dd><dt><span class="strong">Since:</span></dt>
  <dd>1.5</dd></dl>
	 */
	public static java.nio.channels.Channel inheritedChannel() throws IOException
	{
		return java.lang.System.inheritedChannel();
	}
	/**
<h4>setSecurityManager</h4>
<pre>public static&nbsp;void&nbsp;setSecurityManager(<a title="class in java.lang" href="../../java/lang/SecurityManager.html">SecurityManager</a>&nbsp;s)</pre>
<div class="block">Sets the System security.

 <p> If there is a security manager already installed, this method first
 calls the security manager's <code>checkPermission</code> method
 with a <code>RuntimePermission("setSecurityManager")</code>
 permission to ensure it's ok to replace the existing
 security manager.
 This may result in throwing a <code>SecurityException</code>.

 <p> Otherwise, the argument is established as the current
 security manager. If the argument is <code>null</code> and no
 security manager has been established, then no action is taken and
 the method simply returns.</p></div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>s</code> - the security manager.</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if the security manager has already
             been set and its <code>checkPermission</code> method
             doesn't allow it to be replaced.</dd><dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/System.html#getSecurityManager()"><code>getSecurityManager()</code></a>, 
<a href="../../java/lang/SecurityManager.html#checkPermission(java.security.Permission)"><code>SecurityManager.checkPermission(java.security.Permission)</code></a>, 
<a title="class in java.lang" href="../../java/lang/RuntimePermission.html"><code>RuntimePermission</code></a></dd></dl>
	 */
	public static void setSecurityManager(SecurityManager s)
	{
		java.lang.System.setSecurityManager(s);
	}
	/**
<h4>getSecurityManager</h4>
<pre>public static&nbsp;<a title="class in java.lang" href="../../java/lang/SecurityManager.html">SecurityManager</a>&nbsp;getSecurityManager()</pre>
<div class="block">Gets the system security interface.</div>
<dl><dt><span class="strong">Returns:</span></dt><dd>if a security manager has already been established for the
          current application, then that security manager is returned;
          otherwise, <code>null</code> is returned.</dd><dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/System.html#setSecurityManager(java.lang.SecurityManager)"><code>setSecurityManager(java.lang.SecurityManager)</code></a></dd></dl>
	 */
	public static SecurityManager getSecurityManager()
	{
		return java.lang.System.getSecurityManager();
	}
	/**
<h4>currentTimeMillis</h4>
<pre>public static&nbsp;long&nbsp;currentTimeMillis()</pre>
<div class="block">Returns the current time in milliseconds.  Note that
 while the unit of time of the return value is a millisecond,
 the granularity of the value depends on the underlying
 operating system and may be larger.  For example, many
 operating systems measure time in units of tens of
 milliseconds.

 <p> See the description of the class <code>Date</code> for
 a discussion of slight discrepancies that may arise between
 "computer time" and coordinated universal time (UTC).</p></div>
<dl><dt><span class="strong">Returns:</span></dt><dd>the difference, measured in milliseconds, between
          the current time and midnight, January 1, 1970 UTC.</dd><dt><span class="strong">See Also:</span></dt><dd><a title="class in java.util" href="../../java/util/Date.html"><code>Date</code></a></dd></dl>
	 */
	public static long currentTimeMillis()
	{
		return java.lang.System.currentTimeMillis();
	}
	/**
<h4>nanoTime</h4>
<pre>public static&nbsp;long&nbsp;nanoTime()</pre>
<div class="block">Returns the current value of the running Java Virtual Machine's
 high-resolution time source, in nanoseconds.

 <p>This method can only be used to measure elapsed time and is
 not related to any other notion of system or wall-clock time.
 The value returned represents nanoseconds since some fixed but
 arbitrary <i>origin</i> time (perhaps in the future, so values
 may be negative).  The same origin is used by all invocations of
 this method in an instance of a Java virtual machine; other
 virtual machine instances are likely to use a different origin.

 <p>This method provides nanosecond precision, but not necessarily
 nanosecond resolution (that is, how frequently the value changes)
 - no guarantees are made except that the resolution is at least as
 good as that of <a href="../../java/lang/System.html#currentTimeMillis()"><code>currentTimeMillis()</code></a>.

 <p>Differences in successive calls that span greater than
 approximately 292 years (2<sup>63</sup> nanoseconds) will not
 correctly compute elapsed time due to numerical overflow.

 <p>The values returned by this method become meaningful only when
 the difference between two such values, obtained within the same
 instance of a Java virtual machine, is computed.

 <p> For example, to measure how long some code takes to execute:
  <pre> <code>long startTime = System.nanoTime();
 // ... the code being measured ...
 long estimatedTime = System.nanoTime() - startTime;</code></pre>

 <p>To compare two nanoTime values
  <pre> <code>long t0 = System.nanoTime();
 ...
 long t1 = System.nanoTime();</code></pre>

 one should use <code>t1 - t0 &lt; 0</code>, not <code>t1 &lt; t0</code>,
 because of the possibility of numerical overflow.</div>
<dl><dt><span class="strong">Returns:</span></dt><dd>the current value of the running Java Virtual Machine's
         high-resolution time source, in nanoseconds</dd><dt><span class="strong">Since:</span></dt>
  <dd>1.5</dd></dl>
	 */
	public static long nanoTime()
	{
		return java.lang.System.nanoTime();
	}
	/**
<h4>arraycopy</h4>
<pre>public static&nbsp;void&nbsp;arraycopy(<a title="class in java.lang" href="../../java/lang/Object.html">Object</a>&nbsp;src,
             int&nbsp;srcPos,
             <a title="class in java.lang" href="../../java/lang/Object.html">Object</a>&nbsp;dest,
             int&nbsp;destPos,
             int&nbsp;length)</pre>
<div class="block">Copies an array from the specified source array, beginning at the
 specified position, to the specified position of the destination array.
 A subsequence of array components are copied from the source
 array referenced by <code>src</code> to the destination array
 referenced by <code>dest</code>. The number of components copied is
 equal to the <code>length</code> argument. The components at
 positions <code>srcPos</code> through
 <code>srcPos+length-1</code> in the source array are copied into
 positions <code>destPos</code> through
 <code>destPos+length-1</code>, respectively, of the destination
 array.
 <p>
 If the <code>src</code> and <code>dest</code> arguments refer to the
 same array object, then the copying is performed as if the
 components at positions <code>srcPos</code> through
 <code>srcPos+length-1</code> were first copied to a temporary
 array with <code>length</code> components and then the contents of
 the temporary array were copied into positions
 <code>destPos</code> through <code>destPos+length-1</code> of the
 destination array.
 <p>
 If <code>dest</code> is <code>null</code>, then a
 <code>NullPointerException</code> is thrown.
 <p>
 If <code>src</code> is <code>null</code>, then a
 <code>NullPointerException</code> is thrown and the destination
 array is not modified.
 <p>
 Otherwise, if any of the following is true, an
 <code>ArrayStoreException</code> is thrown and the destination is
 not modified:
 <ul>
 <li>The <code>src</code> argument refers to an object that is not an
     array.
 <li>The <code>dest</code> argument refers to an object that is not an
     array.
 <li>The <code>src</code> argument and <code>dest</code> argument refer
     to arrays whose component types are different primitive types.
 <li>The <code>src</code> argument refers to an array with a primitive
    component type and the <code>dest</code> argument refers to an array
     with a reference component type.
 <li>The <code>src</code> argument refers to an array with a reference
    component type and the <code>dest</code> argument refers to an array
     with a primitive component type.
 </li></ul>
 <p>
 Otherwise, if any of the following is true, an
 <code>IndexOutOfBoundsException</code> is
 thrown and the destination is not modified:
 <ul>
 <li>The <code>srcPos</code> argument is negative.
 <li>The <code>destPos</code> argument is negative.
 <li>The <code>length</code> argument is negative.
 <li><code>srcPos+length</code> is greater than
     <code>src.length</code>, the length of the source array.
 <li><code>destPos+length</code> is greater than
     <code>dest.length</code>, the length of the destination array.
 </li></ul>
 <p>
 Otherwise, if any actual component of the source array from
 position <code>srcPos</code> through
 <code>srcPos+length-1</code> cannot be converted to the component
 type of the destination array by assignment conversion, an
 <code>ArrayStoreException</code> is thrown. In this case, let
 <b><i>k</i></b> be the smallest nonnegative integer less than
 length such that <code>src[srcPos+</code><i>k</i><code>]</code>
 cannot be converted to the component type of the destination
 array; when the exception is thrown, source array components from
 positions <code>srcPos</code> through
 <code>srcPos+</code><i>k</i><code>-1</code>
 will already have been copied to destination array positions
 <code>destPos</code> through
 <code>destPos+</code><i>k</i><code>-1</code> and no other
 positions of the destination array will have been modified.
 (Because of the restrictions already itemized, this
 paragraph effectively applies only to the situation where both
 arrays have component types that are reference types.)</p></div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>src</code> - the source array.</dd><dd><code>srcPos</code> - starting position in the source array.</dd><dd><code>dest</code> - the destination array.</dd><dd><code>destPos</code> - starting position in the destination data.</dd><dd><code>length</code> - the number of array elements to be copied.</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/IndexOutOfBoundsException.html">IndexOutOfBoundsException</a></code> - if copying would cause
               access of data outside array bounds.</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/ArrayStoreException.html">ArrayStoreException</a></code> - if an element in the <code>src</code>
               array could not be stored into the <code>dest</code> array
               because of a type mismatch.</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/NullPointerException.html">NullPointerException</a></code> - if either <code>src</code> or
               <code>dest</code> is <code>null</code>.</dd></dl>
	 */
	public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
	{
		java.lang.System.arraycopy(src, srcPos, dest, destPos, length);
	}
	/**
<h4>identityHashCode</h4>
<pre>public static&nbsp;int&nbsp;identityHashCode(<a title="class in java.lang" href="../../java/lang/Object.html">Object</a>&nbsp;x)</pre>
<div class="block">Returns the same hash code for the given object as
 would be returned by the default method hashCode(),
 whether or not the given object's class overrides
 hashCode().
 The hash code for the null reference is zero.</div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>x</code> - object for which the hashCode is to be calculated</dd>
<dt><span class="strong">Returns:</span></dt><dd>the hashCode</dd><dt><span class="strong">Since:</span></dt>
  <dd>JDK1.1</dd></dl>
</li>
	 */
	public static int identityHashCode(Object x)
	{
		return java.lang.System.identityHashCode(x);
	}
	/**
<h4>getProperties</h4>
<pre>public static&nbsp;<a title="class in java.util" href="../../java/util/Properties.html">Properties</a>&nbsp;getProperties()</pre>
<div class="block">Determines the current system properties.
 <p>
 First, if there is a security manager, its
 <code>checkPropertiesAccess</code> method is called with no
 arguments. This may result in a security exception.
 <p>
 The current set of system properties for use by the
 <a href="../../java/lang/System.html#getProperty(java.lang.String)"><code>getProperty(String)</code></a> method is returned as a
 <code>Properties</code> object. If there is no current set of
 system properties, a set of system properties is first created and
 initialized. This set of system properties always includes values
 for the following keys:
 <table summary="Shows property keys and associated values">
 <tbody><tr><th>Key</th>
     <th>Description of Associated Value</th></tr>
 <tr><td><code>java.version</code></td>
     <td>Java Runtime Environment version</td></tr>
 <tr><td><code>java.vendor</code></td>
     <td>Java Runtime Environment vendor</td></tr><tr><td><code>java.vendor.url</code></td>
     <td>Java vendor URL</td></tr>
 <tr><td><code>java.home</code></td>
     <td>Java installation directory</td></tr>
 <tr><td><code>java.vm.specification.version</code></td>
     <td>Java Virtual Machine specification version</td></tr>
 <tr><td><code>java.vm.specification.vendor</code></td>
     <td>Java Virtual Machine specification vendor</td></tr>
 <tr><td><code>java.vm.specification.name</code></td>
     <td>Java Virtual Machine specification name</td></tr>
 <tr><td><code>java.vm.version</code></td>
     <td>Java Virtual Machine implementation version</td></tr>
 <tr><td><code>java.vm.vendor</code></td>
     <td>Java Virtual Machine implementation vendor</td></tr>
 <tr><td><code>java.vm.name</code></td>
     <td>Java Virtual Machine implementation name</td></tr>
 <tr><td><code>java.specification.version</code></td>
     <td>Java Runtime Environment specification  version</td></tr>
 <tr><td><code>java.specification.vendor</code></td>
     <td>Java Runtime Environment specification  vendor</td></tr>
 <tr><td><code>java.specification.name</code></td>
     <td>Java Runtime Environment specification  name</td></tr>
 <tr><td><code>java.class.version</code></td>
     <td>Java class format version number</td></tr>
 <tr><td><code>java.class.path</code></td>
     <td>Java class path</td></tr>
 <tr><td><code>java.library.path</code></td>
     <td>List of paths to search when loading libraries</td></tr>
 <tr><td><code>java.io.tmpdir</code></td>
     <td>Default temp file path</td></tr>
 <tr><td><code>java.compiler</code></td>
     <td>Name of JIT compiler to use</td></tr>
 <tr><td><code>java.ext.dirs</code></td>
     <td>Path of extension directory or directories</td></tr>
 <tr><td><code>os.name</code></td>
     <td>Operating system name</td></tr>
 <tr><td><code>os.arch</code></td>
     <td>Operating system architecture</td></tr>
 <tr><td><code>os.version</code></td>
     <td>Operating system version</td></tr>
 <tr><td><code>file.separator</code></td>
     <td>File separator ("/" on UNIX)</td></tr>
 <tr><td><code>path.separator</code></td>
     <td>Path separator (":" on UNIX)</td></tr>
 <tr><td><code>line.separator</code></td>
     <td>Line separator ("\n" on UNIX)</td></tr>
 <tr><td><code>user.name</code></td>
     <td>User's account name</td></tr>
 <tr><td><code>user.home</code></td>
     <td>User's home directory</td></tr>
 <tr><td><code>user.dir</code></td>
     <td>User's current working directory</td></tr>
 </tbody></table>
 <p>
 Multiple paths in a system property value are separated by the path
 separator character of the platform.
 <p>
 Note that even if the security manager does not permit the
 <code>getProperties</code> operation, it may choose to permit the
 <a href="../../java/lang/System.html#getProperty(java.lang.String)"><code>getProperty(String)</code></a> operation.</p></div>
<dl><dt><span class="strong">Returns:</span></dt><dd>the system properties</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its
             <code>checkPropertiesAccess</code> method doesn't allow access
              to the system properties.</dd><dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/System.html#setProperties(java.util.Properties)"><code>setProperties(java.util.Properties)</code></a>, 
<a title="class in java.lang" href="../../java/lang/SecurityException.html"><code>SecurityException</code></a>, 
<a href="../../java/lang/SecurityManager.html#checkPropertiesAccess()"><code>SecurityManager.checkPropertiesAccess()</code></a>, 
<a title="class in java.util" href="../../java/util/Properties.html"><code>Properties</code></a></dd></dl>
	 */
	public static java.util.Properties getProperties()
	{
		return java.lang.System.getProperties();
	}
	/**
<h4>lineSeparator</h4>
<pre>public static&nbsp;<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;lineSeparator()</pre>
<div class="block">Returns the system-dependent line separator string.  It always
 returns the same value - the initial value of the <a href="../../java/lang/System.html#getProperty(java.lang.String)">system property</a> <code>line.separator</code>.

 <p>On UNIX systems, it returns <code>"\n"</code>; on Microsoft
 Windows systems it returns <code>"\r\n"</code>.</p></div>
</li>
	 */
	public static String lineSeparator()
	{
		return java.lang.System.lineSeparator();
	}
	/**
<h4>setProperties</h4>
<pre>public static&nbsp;void&nbsp;setProperties(<a title="class in java.util" href="../../java/util/Properties.html">Properties</a>&nbsp;props)</pre>
<div class="block">Sets the system properties to the <code>Properties</code>
 argument.
 <p>
 First, if there is a security manager, its
 <code>checkPropertiesAccess</code> method is called with no
 arguments. This may result in a security exception.
 <p>
 The argument becomes the current set of system properties for use
 by the <a href="../../java/lang/System.html#getProperty(java.lang.String)"><code>getProperty(String)</code></a> method. If the argument is
 <code>null</code>, then the current set of system properties is
 forgotten.</p></div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>props</code> - the new system properties.</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its
             <code>checkPropertiesAccess</code> method doesn't allow access
              to the system properties.</dd><dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/System.html#getProperties()"><code>getProperties()</code></a>, 
<a title="class in java.util" href="../../java/util/Properties.html"><code>Properties</code></a>, 
<a title="class in java.lang" href="../../java/lang/SecurityException.html"><code>SecurityException</code></a>, 
<a href="../../java/lang/SecurityManager.html#checkPropertiesAccess()"><code>SecurityManager.checkPropertiesAccess()</code></a></dd></dl>
	 */
	public static void setProperties(java.util.Properties props)
	{
		java.lang.System.setProperties(props);
	}
	/**
<h4>getProperty</h4>
<pre>public static&nbsp;<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;getProperty(<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;key)</pre>
<div class="block">Gets the system property indicated by the specified key.
 <p>
 First, if there is a security manager, its
 <code>checkPropertyAccess</code> method is called with the key as
 its argument. This may result in a SecurityException.
 <p>
 If there is no current set of system properties, a set of system
 properties is first created and initialized in the same manner as
 for the <code>getProperties</code> method.</p></div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>key</code> - the name of the system property.</dd>
<dt><span class="strong">Returns:</span></dt><dd>the string value of the system property,
             or <code>null</code> if there is no property with that key.</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its
             <code>checkPropertyAccess</code> method doesn't allow
              access to the specified system property.</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/NullPointerException.html">NullPointerException</a></code> - if <code>key</code> is
             <code>null</code>.</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/IllegalArgumentException.html">IllegalArgumentException</a></code> - if <code>key</code> is empty.</dd><dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/System.html#setProperty(java.lang.String,%20java.lang.String)"><code>setProperty(java.lang.String, java.lang.String)</code></a>, 
<a title="class in java.lang" href="../../java/lang/SecurityException.html"><code>SecurityException</code></a>, 
<a href="../../java/lang/SecurityManager.html#checkPropertyAccess(java.lang.String)"><code>SecurityManager.checkPropertyAccess(java.lang.String)</code></a>, 
<a href="../../java/lang/System.html#getProperties()"><code>getProperties()</code></a></dd></dl>
	 */
	public static String getProperty(String key)
	{
		return java.lang.System.getProperty(key);
	}
	/**
<h4>getProperty</h4>
<pre>public static&nbsp;<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;getProperty(<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;key)</pre>
<div class="block">Gets the system property indicated by the specified key.
 <p>
 First, if there is a security manager, its
 <code>checkPropertyAccess</code> method is called with the key as
 its argument. This may result in a SecurityException.
 <p>
 If there is no current set of system properties, a set of system
 properties is first created and initialized in the same manner as
 for the <code>getProperties</code> method.</p></div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>key</code> - the name of the system property.</dd>
<dt><span class="strong">Returns:</span></dt><dd>the string value of the system property,
             or <code>null</code> if there is no property with that key.</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its
             <code>checkPropertyAccess</code> method doesn't allow
              access to the specified system property.</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/NullPointerException.html">NullPointerException</a></code> - if <code>key</code> is
             <code>null</code>.</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/IllegalArgumentException.html">IllegalArgumentException</a></code> - if <code>key</code> is empty.</dd><dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/System.html#setProperty(java.lang.String,%20java.lang.String)"><code>setProperty(java.lang.String, java.lang.String)</code></a>, 
<a title="class in java.lang" href="../../java/lang/SecurityException.html"><code>SecurityException</code></a>, 
<a href="../../java/lang/SecurityManager.html#checkPropertyAccess(java.lang.String)"><code>SecurityManager.checkPropertyAccess(java.lang.String)</code></a>, 
<a href="../../java/lang/System.html#getProperties()"><code>getProperties()</code></a></dd></dl>
	 */
	public static String getProperty(String key, String def)
	{
		return java.lang.System.getProperty(key, def);
	}
	/**
<h4>setProperty</h4>
<pre>public static&nbsp;<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;setProperty(<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;key,
                 <a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;value)</pre>
<div class="block">Sets the system property indicated by the specified key.
 <p>
 First, if a security manager exists, its
 <code>SecurityManager.checkPermission</code> method
 is called with a <code>PropertyPermission(key, "write")</code>
 permission. This may result in a SecurityException being thrown.
 If no exception is thrown, the specified property is set to the given
 value.
 <p></p></div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>key</code> - the name of the system property.</dd><dd><code>value</code> - the value of the system property.</dd>
<dt><span class="strong">Returns:</span></dt><dd>the previous value of the system property,
             or <code>null</code> if it did not have one.</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its
             <code>checkPermission</code> method doesn't allow
             setting of the specified property.</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/NullPointerException.html">NullPointerException</a></code> - if <code>key</code> or
             <code>value</code> is <code>null</code>.</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/IllegalArgumentException.html">IllegalArgumentException</a></code> - if <code>key</code> is empty.</dd><dt><span class="strong">Since:</span></dt>
  <dd>1.2</dd>
<dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/System.html#getProperty(java.lang.String)"><code>getProperty(java.lang.String)</code></a>, 
<a href="../../java/lang/System.html#getProperty(java.lang.String)"><code>getProperty(java.lang.String)</code></a>, 
<a href="../../java/lang/System.html#getProperty(java.lang.String,%20java.lang.String)"><code>getProperty(java.lang.String, java.lang.String)</code></a>, 
<a title="class in java.util" href="../../java/util/PropertyPermission.html"><code>PropertyPermission</code></a>, 
<a href="../../java/lang/SecurityManager.html#checkPermission(java.security.Permission)"><code>SecurityManager.checkPermission(java.security.Permission)</code></a></dd></dl>
	 */
	public static String setProperty(String key, String value)
	{
		return java.lang.System.setProperty(key, value);
	}
	/**
<h4>clearProperty</h4>
<pre>public static&nbsp;<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;clearProperty(<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;key)</pre>
<div class="block">Removes the system property indicated by the specified key.
 <p>
 First, if a security manager exists, its
 <code>SecurityManager.checkPermission</code> method
 is called with a <code>PropertyPermission(key, "write")</code>
 permission. This may result in a SecurityException being thrown.
 If no exception is thrown, the specified property is removed.
 <p></p></div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>key</code> - the name of the system property to be removed.</dd>
<dt><span class="strong">Returns:</span></dt><dd>the previous string value of the system property,
             or <code>null</code> if there was no property with that key.</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its
             <code>checkPropertyAccess</code> method doesn't allow
              access to the specified system property.</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/NullPointerException.html">NullPointerException</a></code> - if <code>key</code> is
             <code>null</code>.</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/IllegalArgumentException.html">IllegalArgumentException</a></code> - if <code>key</code> is empty.</dd><dt><span class="strong">Since:</span></dt>
  <dd>1.5</dd>
<dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/System.html#getProperty(java.lang.String)"><code>getProperty(java.lang.String)</code></a>, 
<a href="../../java/lang/System.html#setProperty(java.lang.String,%20java.lang.String)"><code>setProperty(java.lang.String, java.lang.String)</code></a>, 
<a title="class in java.util" href="../../java/util/Properties.html"><code>Properties</code></a>, 
<a title="class in java.lang" href="../../java/lang/SecurityException.html"><code>SecurityException</code></a>, 
<a href="../../java/lang/SecurityManager.html#checkPropertiesAccess()"><code>SecurityManager.checkPropertiesAccess()</code></a></dd></dl>
	 */
	public static String clearProperty(String key)
	{
		return java.lang.System.clearProperty(key);
	}
	/**
<h4>getenv</h4>
<pre>public static&nbsp;<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;getenv(<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;name)</pre>
<div class="block">Gets the value of the specified environment variable. An
 environment variable is a system-dependent external named
 value.
 <p>If a security manager exists, its
 <a href="../../java/lang/SecurityManager.html#checkPermission(java.security.Permission)"><code>checkPermission</code></a>
 method is called with a
 <code><a title="class in java.lang" href="../../java/lang/RuntimePermission.html"><code>RuntimePermission</code></a>("getenv."+name)</code>
 permission.  This may result in a <a title="class in java.lang" href="../../java/lang/SecurityException.html"><code>SecurityException</code></a>
 being thrown.  If no exception is thrown the value of the
 variable <code>name</code> is returned.
 
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>name</code> - the name of the environment variable</dd>
<dt><span class="strong">Returns:</span></dt><dd>the string value of the variable, or <code>null</code>
         if the variable is not defined in the system environment</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/NullPointerException.html">NullPointerException</a></code> - if <code>name</code> is <code>null</code></dd>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its
         <a href="../../java/lang/SecurityManager.html#checkPermission(java.security.Permission)"><code>checkPermission</code></a>
         method doesn't allow access to the environment variable
         <code>name</code></dd><dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/System.html#getenv()"><code>getenv()</code></a>, 
<a href="../../java/lang/ProcessBuilder.html#environment()"><code>ProcessBuilder.environment()</code></a></dd></dl>
	 */
	public static String getenv(String name)
	{
		return java.lang.System.getenv(name);
	}
	/**
<h4>getenv</h4>
<pre>public static&nbsp;<a title="interface in java.util" href="../../java/util/Map.html">Map</a>&lt;<a title="class in java.lang" href="../../java/lang/String.html">String</a>,<a title="class in java.lang" href="../../java/lang/String.html">String</a>&gt;&nbsp;getenv()</pre>
<div class="block">Returns an unmodifiable string map view of the current system environment.
 The environment is a system-dependent mapping from names to
 values which is passed from parent to child processes.

 <p>If the system does not support environment variables, an
 empty map is returned.

 <p>The returned map will never contain null keys or values.
 Attempting to query the presence of a null key or value will
 throw a <a title="class in java.lang" href="../../java/lang/NullPointerException.html"><code>NullPointerException</code></a>.  Attempting to query
 the presence of a key or value which is not of type
 <a title="class in java.lang" href="../../java/lang/String.html"><code>String</code></a> will throw a <a title="class in java.lang" href="../../java/lang/ClassCastException.html"><code>ClassCastException</code></a>.

 <p>The returned map and its collection views may not obey the
 general contract of the <a href="../../java/lang/Object.html#equals(java.lang.Object)"><code>Object.equals(java.lang.Object)</code></a> and
 <a href="../../java/lang/Object.html#hashCode()"><code>Object.hashCode()</code></a> methods.

 <p>The returned map is typically case-sensitive on all platforms.

 <p>If a security manager exists, its
 <a href="../../java/lang/SecurityManager.html#checkPermission(java.security.Permission)"><code>checkPermission</code></a>
 method is called with a
 <code><a title="class in java.lang" href="../../java/lang/RuntimePermission.html"><code>RuntimePermission</code></a>("getenv.*")</code>
 permission.  This may result in a <a title="class in java.lang" href="../../java/lang/SecurityException.html"><code>SecurityException</code></a> being
 thrown.

 <p>When passing information to a Java subprocess,
 <a href="#EnvironmentVSSystemProperties">system properties</a>
 are generally preferred over environment variables.</p></div>
<dl><dt><span class="strong">Returns:</span></dt><dd>the environment as a map of variable names to values</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its
         <a href="../../java/lang/SecurityManager.html#checkPermission(java.security.Permission)"><code>checkPermission</code></a>
         method doesn't allow access to the process environment</dd><dt><span class="strong">Since:</span></dt>
  <dd>1.5</dd>
<dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/System.html#getenv(java.lang.String)"><code>getenv(String)</code></a>, 
<a href="../../java/lang/ProcessBuilder.html#environment()"><code>ProcessBuilder.environment()</code></a></dd></dl>
	 */
	public static java.util.Map<String,String> getenv()
	{
		return java.lang.System.getenv();
	}
	/**
<h4>exit</h4>
<pre>public static&nbsp;void&nbsp;exit(int&nbsp;status)</pre>
<div class="block">Terminates the currently running Java Virtual Machine. The
 argument serves as a status code; by convention, a nonzero status
 code indicates abnormal termination.
 <p>
 This method calls the <code>exit</code> method in class
 <code>Runtime</code>. This method never returns normally.
 <p>
 The call <code>System.exit(n)</code> is effectively equivalent to
 the call:
 <blockquote><pre> Runtime.getRuntime().exit(n)
 </pre></blockquote></div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>status</code> - exit status.</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its <code>checkExit</code>
        method doesn't allow exit with the specified status.</dd><dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/Runtime.html#exit(int)"><code>Runtime.exit(int)</code></a></dd></dl>
	 */
	public static void exit(int status)
	{
		java.lang.System.exit(status);
	}
	/**
<h4>gc</h4>
<pre>public static&nbsp;void&nbsp;gc()</pre>
<div class="block">Runs the garbage collector.
 <p>
 Calling the <code>gc</code> method suggests that the Java Virtual
 Machine expend effort toward recycling unused objects in order to
 make the memory they currently occupy available for quick reuse.
 When control returns from the method call, the Java Virtual
 Machine has made a best effort to reclaim space from all discarded
 objects.
 <p>
 The call <code>System.gc()</code> is effectively equivalent to the
 call:
 <blockquote><pre> Runtime.getRuntime().gc()
 </pre></blockquote></div>
<dl><dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/Runtime.html#gc()"><code>Runtime.gc()</code></a></dd></dl>
	 */
	public static void gc()
	{
		java.lang.System.gc();
	}
	/**
<h4>runFinalization</h4>
<pre>public static&nbsp;void&nbsp;runFinalization()</pre>
<div class="block">Runs the finalization methods of any objects pending finalization.
 <p>
 Calling this method suggests that the Java Virtual Machine expend
 effort toward running the <code>finalize</code> methods of objects
 that have been found to be discarded but whose <code>finalize</code>
 methods have not yet been run. When control returns from the
 method call, the Java Virtual Machine has made a best effort to
 complete all outstanding finalizations.
 <p>
 The call <code>System.runFinalization()</code> is effectively
 equivalent to the call:
 <blockquote><pre> Runtime.getRuntime().runFinalization()
 </pre></blockquote></div>
<dl><dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/Runtime.html#runFinalization()"><code>Runtime.runFinalization()</code></a></dd></dl>
	 */
	public static void runFinalization()
	{
		java.lang.System.runFinalization();
	}
	/**
<h4>runFinalizersOnExit</h4>
<pre><a title="annotation in java.lang" href="../../java/lang/Deprecated.html">@Deprecated</a>
public static&nbsp;void&nbsp;runFinalizersOnExit(boolean&nbsp;value)</pre>
<div class="block"><span class="strong">Deprecated.</span>&nbsp;<i>This method is inherently unsafe.  It may result in
      finalizers being called on live objects while other threads are
      concurrently manipulating those objects, resulting in erratic
      behavior or deadlock.</i></div>
<div class="block">Enable or disable finalization on exit; doing so specifies that the
 finalizers of all objects that have finalizers that have not yet been
 automatically invoked are to be run before the Java runtime exits.
 By default, finalization on exit is disabled.

 <p>If there is a security manager,
 its <code>checkExit</code> method is first called
 with 0 as its argument to ensure the exit is allowed.
 This could result in a SecurityException.</p></div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>value</code> - indicating enabling or disabling of finalization</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its <code>checkExit</code>
        method doesn't allow the exit.</dd><dt><span class="strong">Since:</span></dt>
  <dd>JDK1.1</dd>
<dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/Runtime.html#exit(int)"><code>Runtime.exit(int)</code></a>, 
<a href="../../java/lang/Runtime.html#gc()"><code>Runtime.gc()</code></a>, 
<a href="../../java/lang/SecurityManager.html#checkExit(int)"><code>SecurityManager.checkExit(int)</code></a></dd></dl>
	 */
	@Deprecated
	public static void runFinalizersOnExit(boolean value)
	{
		java.lang.System.runFinalizersOnExit(value);
	}
	/**
<h4>load</h4>
<pre>public static&nbsp;void&nbsp;load(<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;filename)</pre>
<div class="block">Loads a code file with the specified filename from the local file
 system as a dynamic library. The filename
 argument must be a complete path name.
 <p>
 The call <code>System.load(name)</code> is effectively equivalent
 to the call:
 <blockquote><pre> Runtime.getRuntime().load(name)
 </pre></blockquote></div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>filename</code> - the file to load.</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its
             <code>checkLink</code> method doesn't allow
             loading of the specified dynamic library</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/UnsatisfiedLinkError.html">UnsatisfiedLinkError</a></code> - if the file does not exist.</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/NullPointerException.html">NullPointerException</a></code> - if <code>filename</code> is
             <code>null</code></dd><dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/Runtime.html#load(java.lang.String)"><code>Runtime.load(java.lang.String)</code></a>, 
<a href="../../java/lang/SecurityManager.html#checkLink(java.lang.String)"><code>SecurityManager.checkLink(java.lang.String)</code></a></dd></dl>
	 */
	public static void load(String filename)
	{
		java.lang.System.load(filename);
	}
	/**
<h4>loadLibrary</h4>
<pre>public static&nbsp;void&nbsp;loadLibrary(<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;libname)</pre>
<div class="block">Loads the system library specified by the <code>libname</code>
 argument. The manner in which a library name is mapped to the
 actual system library is system dependent.
 <p>
 The call <code>System.loadLibrary(name)</code> is effectively
 equivalent to the call
 <blockquote><pre> Runtime.getRuntime().loadLibrary(name)
 </pre></blockquote></div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>libname</code> - the name of the library.</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/SecurityException.html">SecurityException</a></code> - if a security manager exists and its
             <code>checkLink</code> method doesn't allow
             loading of the specified dynamic library</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/UnsatisfiedLinkError.html">UnsatisfiedLinkError</a></code> - if the library does not exist.</dd>
<dd><code><a title="class in java.lang" href="../../java/lang/NullPointerException.html">NullPointerException</a></code> - if <code>libname</code> is
             <code>null</code></dd><dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/Runtime.html#loadLibrary(java.lang.String)"><code>Runtime.loadLibrary(java.lang.String)</code></a>, 
<a href="../../java/lang/SecurityManager.html#checkLink(java.lang.String)"><code>SecurityManager.checkLink(java.lang.String)</code></a></dd></dl>
	 */
	public static void loadLibrary(String libname)
	{
		java.lang.System.loadLibrary(libname);
	}
	/**
<h4>mapLibraryName</h4>
<pre>public static&nbsp;<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;mapLibraryName(<a title="class in java.lang" href="../../java/lang/String.html">String</a>&nbsp;libname)</pre>
<div class="block">Maps a library name into a platform-specific string representing
 a native library.</div>
<dl><dt><span class="strong">Parameters:</span></dt><dd><code>libname</code> - the name of the library.</dd>
<dt><span class="strong">Returns:</span></dt><dd>a platform-dependent native library name.</dd>
<dt><span class="strong">Throws:</span></dt>
<dd><code><a title="class in java.lang" href="../../java/lang/NullPointerException.html">NullPointerException</a></code> - if <code>libname</code> is
             <code>null</code></dd><dt><span class="strong">Since:</span></dt>
  <dd>1.2</dd>
<dt><span class="strong">See Also:</span></dt><dd><a href="../../java/lang/System.html#loadLibrary(java.lang.String)"><code>loadLibrary(java.lang.String)</code></a>, 
<a href="../../java/lang/ClassLoader.html#findLibrary(java.lang.String)"><code>ClassLoader.findLibrary(java.lang.String)</code></a></dd></dl>
	 */
	public static String mapLibraryName(String libname)
	{
		return java.lang.System.mapLibraryName(libname);
	}
}
