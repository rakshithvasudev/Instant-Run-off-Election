import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class contains various useful methods related to manipulating
 * collections and arrays of data.
 * @author Han
 * @version Spring 2017
 */
public final class CollectionUtils {
	// private constructor ensures that the class cannot be instantiated
	private CollectionUtils() {}

	/**
	 * Converts the elements found in the given Iterator into a list.
	 * @param <T> type of elements found in iterator
	 * @param itr iterator of elements to convert
	 * @return the list of elements;  [] if itr has no next elements
	 */
	public static <T> List<T> asList(Iterator<T> itr) {
		Arguments.ensureNotNull(itr);
		List<T> list = new ArrayList<T>();
		while (itr != null && itr.hasNext()) {
			list.add(itr.next());
		}
		return list;
	}

	/**
	 * Converts the given values into a set.
	 * @return the set, or empty set if values == null
	 */
	public static <K> Set<K> asSet(K... values) {
		Set<K> set = new LinkedHashSet<K>();
		if (values == null) {
			return set;
		}
		
		for (K value : values) {
			set.add(value);
		}
		return set;
	}

	/**
	 * Converts the given values into a sorted set.
	 * @return the set, or empty set if values == null
	 */
	public static <K extends Comparable<K>> Set<K> asSetSorted(K... values) {
		Set<K> set = new TreeSet<K>();
		for (K value : values) {
			set.add(value);
		}
		return set;
	}
	
	/**
	 * Combines the elements of the given array or collection into a long
	 * String, separating each by a comma and space.
	 * @param collection the iterable entity (array or collection) to join
	 * @return the joined String, or "" if the array is empty
	 */
	public static String join(Iterable<?> collection) {
		return join(collection, ", ");
	}
	
	/**
	 * Combines the elements of the given array or collection into a long
	 * String, separating each by the given delimiter.
	 * @param collection the iterable entity (array or collection) to join
	 * @param delimiter the text to place between adjacent elements
	 * @return the joined String, or "" if the array is empty
	 */
	public static String join(Iterable<?> collection, String delimiter) {
		Arguments.ensureNotNull(collection, delimiter);
		StringBuilder sb = new StringBuilder();
		for (Object o : collection) {
			if (sb.length() > 0) {
				sb.append(delimiter);
			}
			sb.append(o);
		}
		return sb.toString();
	}
	
	/**
	 * Combines the elements of the given map into a string separated
	 * by an equals sign between keys and their values, and commas between each entry.
	 * @param map map to convert into string
	 * @return the joined string, such as "{a=b, c=d, e=f}"
	 */
	public static String join(Map<?, ?> map) {
		return join(map, "=", ", ");
	}
	
	/**
	 * Combines the elements of the given collection into a string separated
	 * by the given kv separator between keys and their values, and
	 * the given element separator commas between each entry.
	 * @param map map to convert into string
	 * @return the joined string, such as "{a=b, c=d, e=f}"
	 */
	public static String join(Map<?, ?> map, String kvSeparator, String elementSeparator) {
		Arguments.ensureNotNull(map, kvSeparator, elementSeparator);
		StringBuilder result = new StringBuilder();
		int i = 0;
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			if (i > 0) {
				result.append(elementSeparator);
			}
			result.append(entry.getKey());
			result.append(kvSeparator);
			result.append(entry.getValue());
			i++;
		}
		return result.toString();
	}

	/**
	 * Returns the maximum value of all the integers passed (must pass at least one).
	 * @param int1 first integer passed
	 * @param ints any additional integer passed (var-args)
	 * @return the largest value passed
	 */
	public static int max(int int1, int... ints) {
		int result = int1;
		for (int n : ints) {
			if (n > result) {
				result = n;
			}
		}
		return result;
	}
	
	/**
	 * Returns the minimum value of all the integers passed (must pass at least one).
	 * @param int1 first integer passed
	 * @param ints any additional integer passed (var-args)
	 * @return the smallest value passed
	 */
	public static int min(int int1, int... ints) {
		int result = int1;
		for (int n : ints) {
			if (n < result) {
				result = n;
			}
		}
		return result;
	}
	
	/**
	 * Reverses the given map, such that its keys become its values and vice
	 * versa.  Note that since keys must be unique and values can recur,
	 * we must may from values to -sets- of keys in the reversed result.
	 * @param <K> the type of the keys in the original map
	 * @param <V> the type of the keys in the original map
	 * @param map map to be reversed
	 * @pre key/value types in the given map must be Comparable
	 * @return the reversed map
	 */
	public static <K extends Comparable<K>, V extends Comparable<V>> SortedMap<V, Set<K>> reverseMap(Map<K, V> map) {
		Arguments.ensureNotNull(map);
		SortedMap<V, Set<K>> reversed = new TreeMap<V, Set<K>>();
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (!reversed.containsKey(entry.getValue())) {
				reversed.put(entry.getValue(), new LinkedHashSet<K>());
			}
			reversed.get(entry.getValue()).add(entry.getKey());
		}
		return reversed;
	}
}
