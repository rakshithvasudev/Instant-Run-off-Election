/**
 * This class contains various useful methods related to manipulating Strings.
 * @author Han
 * @version Spring 2017
 */
public final class StringUtils {
	// private constructor ensures that the class cannot be instantiated
	private StringUtils() {}
	
	/**
	 * Combines the given array of strings into a single string, separating
	 * neighboring elements by the given delimiter text.
	 * @param tokens array of tokens to combine
	 * @param delimiter separator to place between neighboring tokens
	 * @return "" if tokens is null or has no elements
	 */
	public static String join(String[] tokens, String delimiter) {
		return join(tokens, delimiter, (tokens == null ? 0 : tokens.length));
	}

	/**
	 * Combines the given array of strings into a single string, separating
	 * neighboring elements by the given delimiter text.
	 * @param tokens array of tokens to combine
	 * @param delimiter separator to place between neighboring tokens
	 * @param limit max # of lines to use from the array (rest are truncated)
	 * @return "" if tokens is null or has no elements
	 */
	public static String join(String[] tokens, String delimiter, int limit) {
		if (tokens == null || tokens.length == 0 || limit <= 0) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(tokens[0]);
		limit = Math.min(limit, tokens.length);
		for (int i = 1; i < limit; i++) {
			buffer.append(delimiter);
			buffer.append(tokens[i]);
		}
		return buffer.toString();
	}

	/**
	 * A string that contains s repeated n times.
	 * @param s string to repeat.  if s is null, returns null.
	 * @param n number of repetitions.  if 0 or less, returns "".
	 * @return null if s == null
	 */
	public static String nCopies(String s, int n) {
		if (s == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(s.length() * n + 8);
		for (int i = 1; i <= n; i++) {
			sb.append(s);
		}
		return sb.toString();
	}
	
	/**
	 * Pads the number by adding spaces to its left side until it is at least
	 * the given width in length.
	 * @param n number to pad
	 * @param width number of characters wide in which to display it
	 */
	public static String padL(int n, int width) {
		return padL(String.valueOf(n), width);
	}

	/**
	 * Pads the string by adding spaces to its left side until it is at least
	 * the given width in length.
	 * @param s string to pad
	 * @param width number of characters wide in which to display it
	 * @return the padded string; guaranted to be the given width
	 *         if s.length() < width
	 */
	public static String padL(String s, int width) {
		if (s == null) {
			s = "null";
		}
		while (s.length() < width) {
			s = " " + s;
		}
		return s;
	}

	/**
	 * Pads the number by adding spaces to its right side until it is at least
	 * the given width in length.
	 * @param n number to pad
	 * @param width number of characters wide in which to display it
	 * @return the padded string; guaranted to be the given width
	 *         if s.length() < width
	 */
	public static String padR(int n, int width) {
		return padR(String.valueOf(n), width);
	}

	/**
	 * Pads the string by adding spaces to its right side until it is at least
	 * the given width in length.
	 * @param s string to pad
	 * @param width number of characters wide in which to display it
	 * @return the padded string; guaranted to be the given width
	 *         if s.length() < width
	 */
	public static String padR(String s, int width) {
		if (s == null) {
			s = "null";
		}
		while (s.length() < width) {
			s += " ";
		}
		return s;
	}
	
	/**
	 * Trims out any leading/trailing whitespace from all lines of string s.
	 * @param s string to examine
	 * @return null if s == null
	 */
	public static String trimLines(String s) {
		if (s == null) {
			return null;
		} else {
			return s.replaceAll("[ \t]*\r?\n[ \t]*", "\n").trim();
		}
	}
	
	/**
	 * Trims out any trailing whitespace from all lines of string s.
	 * @param s string to examine
	 * @return null if s == null
	 */
	public static String trimEndsOfLines(String s) {
		if (s == null) {
			return null;
		} else {
			return s.replaceAll("\\s+\r?\n", "\n");
		}
	}
	
	/**
	 * Trims the given string to be at most the given number of characters in length.
	 * Similar to fitToWidth, but doesn't put ... at end.
	 * @param s string to truncate
	 * @param length number of characters, maximum, to retain from s
	 */
	public static String truncate(String s, int length) {
		if (s == null || s.length() <= length) {
			return s;
		} else {
			return s.substring(0, length);
		}
	}
}
