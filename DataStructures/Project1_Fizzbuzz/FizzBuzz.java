package fizzbuzz;

public class FizzBuzz {
	private final int fizzNumber;
	private final int buzzNumber;
	private final String fizzString;
	private final String buzzString;
	/**
	 * Construct an object that can compute fizzbuzz values for a game of 
	 * Fizz and Buzz.
	 * 
	 * @param fizzNumber: an integer between 1 and 9
	 * @param buzzNumber: an integer between 1 and 9
	 */
	public FizzBuzz(int fizzNumber, int buzzNumber) {
		this.fizzNumber = fizzNumber;
		this.buzzNumber = buzzNumber;
		this.fizzString = Integer.toString(fizzNumber);
		this.buzzString = Integer.toString(buzzNumber);
	}

	/**
	 * Returns the fizzbuzz value for n. The rules are:
	 * - if n is divisible by fizzNumber, or contains the digit fizzNumber, return "fizz" 
	 * - if n is divisible by buzzNumber, or contains the digit buzzNumber, return "buzz"
	 * - however, if both the above conditions are true, you must return "fizzbuzz"
	 * - if none of the above conditions is true, return the number itself.
	 *
	 * For example, getValue(1) returns "1".
	 * 
	 * @param n: a positive integer
	 * @return the fizzbuzz value, as a String
	 */
	public String getValue(int n) {
		String nString = Integer.toString(n);
		if((n % fizzNumber == 0 && n % buzzNumber == 0) ||
				(nString.contains(fizzString) && nString.contains(buzzString)) ||
				(n % fizzNumber == 0 && nString.contains(buzzString)) ||
				(nString.contains(fizzString) && n % buzzNumber == 0)) {
			return "fizzbuzz";
		}
		if(n % fizzNumber == 0  || nString.contains(fizzString)) {
			return "fizz";
		}
		else if(n % buzzNumber == 0 || nString.contains(buzzString)) {
			return "buzz";
		}
		else {
			return Integer.toString(n); // return the number itself as a String
		}
	}

	/**
	 * Returns an array of the fizzbuzz values from start to end, inclusive.
	 * 
	 * For example, if the fizz number is 3 and buzz number is 4,
	 * getValues(2,6) should return an array of Strings:
	 * 
	 * {"2", "fizz", "buzz", "5", "fizz"}
	 * 
	 * @param start
	 *            the number to start from; start > 0
	 * @param end
	 *            the number to end at; end >= start
	 * @return the array of fizzbuzz values
	 */
	public String[] getValues(int start, int end) {
		String[] values = new String[end - start + 1];
		for(int i = start; i <= end; i++) {
			values[i - start] = this.getValue(i);
		}
		return values;
	}
}
