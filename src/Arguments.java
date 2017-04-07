/**
 * This class contains various useful methods related to checking
 * the validity of arguments.
 * @author Han
 * @version Spring 2017
 */
public final class Arguments {
	// private constructor ensures that the class cannot be instantiated
	private Arguments() {}
	
	/**
	 * Throws an illegal argument exception if the given object is null.
	 * @param o object to be checked for nullness
	 * @throws IllegalArgumentException if o is null
	 */
	public static void ensureNotNull(Object o) {
		if (o == null) {
			throw new IllegalArgumentException("cannot pass null");
		}
	}
	
	/**
	 * Throws an illegal argument exception if any object in the given array is null.
	 * @param a array whose elements are to be checked for nullness (var-args)
	 * @throws IllegalArgumentException if any object in a is null
	 */
	public static void ensureNotNull(Object... a) {
		for (Object o : a) {
			if (o == null) {
				throw new IllegalArgumentException("cannot pass null");
			}
		}
	}
	
	/**
	 * Throws an illegal argument exception if the given string is null or the empty string.
	 * @param s string to be checked for nullness/emptiness
	 * @throws IllegalArgumentException if s is null or ""
	 */
	public static void ensureNotNullOrEmpty(String s) {
		if (s == null || s.isEmpty()) {
			throw new IllegalArgumentException("cannot pass null or empty string");
		}
	}
	
	/**
	 * Throws an illegal argument exception if the given integer is not
	 * greater than or equal to the given minimum value.
	 * @param n integer to be checked for its range
	 * @throws IllegalArgumentException if n < min
	 */
	public static void ensureAtLeast(int n, int min) {
		if (n < min) {
			throw new IllegalArgumentException("must be at least " + min + ": " + n);
		}
	}
	
	/**
	 * Throws an illegal argument exception if the given integer is not
	 * less than or equal to the given maximum value.
	 * @param n integer to be checked for its range
	 * @throws IllegalArgumentException if n > max
	 */
	public static void ensureAtMost(int n, int max) {
		if (n < max) {
			throw new IllegalArgumentException("must be at most " + max + ": " + n);
		}
	}
	
	/**
	 * Throws an illegal argument exception if the given integer is not
	 * between the given minimum and maximum values inclusive.
	 * @param n integer to be checked for its range
	 * @throws IllegalArgumentException if n < min or n > max
	 */
	public static void ensureInRange(int n, int min, int max) {
		if (n < min || n > max) {
			throw new IllegalArgumentException("must be between " + min + " and " + max + ": " + n);
		}
	}
	
	/**
	 * Throws an illegal argument exception if the given integer is negative.
	 * @param n integer to be checked for its range
	 * @throws IllegalArgumentException if n < 0
	 */
	public static void ensureNonNegative(int n) {
		ensureAtLeast(n, 0);
	}
	
	/**
	 * Throws an illegal argument exception if the given real number is negative.
	 * @param d number to be checked for its range
	 * @throws IllegalArgumentException if d < 0.0
	 */
	public static void ensureNonNegative(double d) {
		if (d < 0.0) {
			throw new IllegalArgumentException("must be non-negative: " + d);
		}
	}

	/**
	 * Throws an illegal argument exception if the given integer is not positive (> 0).
	 * @param n integer to be checked for its range
	 * @throws IllegalArgumentException if n <= 0
	 */
	public static void ensurePositive(int n) {
		ensureAtLeast(n, 1);
	}
}
