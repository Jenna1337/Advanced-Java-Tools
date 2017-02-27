package tools;

/**<pre>
Structure             | Order   | Unique | Associative | Bounded | Null   | Thread-safe | Blocking | Type
----------------------+---------+--------+-------------+---------+--------+-------------+----------+---------------
AbstractCollection    | varies  | varies | varies      | varies  | varies | varies      | varies   | &lt;E>
AbstractList          | yes     | varies | no          | varies  | varies | varies      | varies   | &lt;E>
AbstractMap           | no      | Key    | yes         | varies  | varies | varies      | varies   | &lt;K,V>
AbstractQueue         | yes     | varies | no          | varies  | varies | varies      | varies   | &lt;E>
AbstractSequentialList| varies  | varies | no          | varies  | varies | varies      | varies   | &lt;E>
AbstractSet           | varies  | yes    | no          | varies  | varies | varies      | varies   | &lt;E>
ArrayBlockingQueue    | FIFO    | no     | no          | yes     | no     | yes         | yes      | &lt;E>
ArrayDeque            | deque   | no     | no          | no      | no     | no          | no       | &lt;E>
ArrayList             | no      | no     | no          | no      | yes    | no          | no       | &lt;E>
BitSet                | no      | no     | no          | no      | no     | no          | no       | boolean
ConcurrentHashMap     | no      | Key    | yes         | no      | no     | yes         | no       | &lt;K,V>
ConcurrentLinkedDeque | deque   | no     | no          | no      | no     | yes         | no       | &lt;E>
ConcurrentLinkedQueue | FIFO    | no     | no          | no      | no     | yes         | no       | &lt;E>
ConcurrentSkipListMap | Natural | Key    | yes         | no      | no     | yes         | no       | &lt;K,V>
ConcurrentSkipListSet | Natural | yes    | no          | no      | no     | yes         | no       | &lt;E>
CopyOnWriteArrayList  | no      | no     | no          | no      | yes    | yes         | N/A      | &lt;E>
CopyOnWriteArraySet   | no      | yes    | no          | no      | yes    | yes         | N/A      | &lt;E>
DelayQueue            | delay   | no     | no          | no      | no     | yes         | yes      | &lt;Delayed>
EnumMap               | Declare | Key    | yes         | fixed   | no     | no          | no       | &lt;Enum,V>
EnumSet               | Declare | yes    | no          | fixed   | no     | no          | no       | &lt;Enum>
HashMap               | no      | Key    | yes         | no      | yes    | no          | no       | &lt;K,V>
HashSet               | no      | yes    | no          | no      | yes    | no          | no       | &lt;E>
Hashtable             | no      | Key    | yes         | no      | no     | no          | no       | &lt;K,V>
IdentityHashMap       | no      | Key == | yes         | 1 << 29 | yes    | no          | no       | &lt;K,V>
LinkedBlockingDeque   | deque   | no     | no          | depends | no     | yes         | yes      | &lt;E>
LinkedBlockingQueue   | FIFO    | no     | no          | depends | no     | yes         | yes      | &lt;E>
LinkedHashMap         | Insert  | yes    | yes         | no      | yes    | no          | no       | &lt;K,V>
LinkedHashSet         | Insert  |        | no          | no      | yes    | no          | no       | &lt;E>
LinkedList            | no      | no     | no          | no      | yes    | no          | no       | &lt;E>
LinkedTransferQueue   | FIFO    | no     | no          | no      | no     | no          | no       | &lt;E>
PriorityBlockingQueue | Natural | no     | no          | no      | no     | yes         | yes      | &lt;E>
PriorityQueue         | Natural | no     | no          | no      | no     | no          | no       | &lt;E>
Properties            | N/A     | Key    | yes         | fixed   | no     | yes         | no       | String,String
Stack                 | LIFO    | no     | no          | no      | yes    | no          | no       | &lt;E>
SynchronousQueue      | N/A     | N/A    | N/A         | 0       | no     | yes         | yes      | &lt;E>
TreeMap               | Natural | Key    | yes         | no      | no     | no          | no       | &lt;K,V>
TreeSet               | Natural | yes    | no          | no      | no     | no          | no       | &lt;E>
Vector                | no      | no     | no          | no      | yes    | yes         |          | &lt;E>
WeakHashMap           | no      | no     | yes         | no      | yes    | no          | no       | &lt;K,V>
----------------------+---------+--------+-------------+---------+--------+-------------+----------+---------------
Structure             | Ordered | Unique | Associative | Bounded | Null   | Thread-safe | Blocking | Type
</pre>*/
public class CollectionTools
{
	private CollectionTools()
	{
	}
}
