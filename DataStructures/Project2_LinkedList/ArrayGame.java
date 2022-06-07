package guessme;

/**
 * An Array-based implementation of the Guess-A-Number game
 */
public class ArrayGame {

	// stores the next number to guess
	private int guess;
	private int numGuesses;
	private boolean isOver;
	
	// TODO: declare additional data members, such as arrays that store
	// prior guesses, eliminated candidates etc.
	private boolean[] guesses;
	private int[] priorGuesses;

	// NOTE: only primitive type arrays are allowed, such as int[], boolean[] etc.
	// You MAY NOT use any Collection type (such as ArrayList) provided by Java.
	
	/********************************************************
	 * NOTE: you are allowed to add new methods if necessary,
	 * but DO NOT remove any provided method, otherwise your
	 * code will fail the JUnit tests!
	 * Also DO NOT create any new Java files, as they will
	 * be ignored by the autograder!
	 *******************************************************/
	
	// ArrayGame constructor method
	public ArrayGame() {
		this.guess = 1000;
		this.numGuesses = 0;
		this.isOver = false;
		this.guesses = new boolean[9000];
		this.priorGuesses = new int[18];
	}
	
	// Resets data members and game state so we can play again
	public void reset() {
		this.guess = 1000;
		this.numGuesses = 0;
		this.isOver = false;
		this.guesses = new boolean[9000];
	}
	
	// Returns true if n is a prior guess; false otherwise.
	public boolean isPriorGuess(int n) {
		if(guesses[n - 1000] == false) {
			return false;
		}
		return true;
	}
	
	// Returns the number of guesses so far.
	public int numGuesses() {
		// TODO
		return numGuesses;
	}
	
	/**
	 * Returns the number of matches between integers a and b.
	 * You can assume that both are 4-digits long (i.e. between 1000 and 9999).
	 * The return value must be between 0 and 4.
	 * 
	 * A match is the same digit at the same location. For example:
	 *   1234 and 4321 have 0 match;
	 *   1234 and 1114 have 2 matches (1 and 4);
	 *   1000 and 9000 have 3 matches (three 0's).
	 */
	public static int numMatches(int a, int b) { // DO NOT remove the static qualifier
		int matches = 0;
		if(a % 10 == b % 10) {
			matches++;
		}
		if((a%100)/10 == (b%100)/10) {
			matches++;
		}
		if((a%1000)/100 == (b%1000)/100) {
			matches++;
		}
		if(a/1000 == b/1000) {
			matches++;
		}
		return matches;
	}
	
	/**
	 * Returns true if the game is over; false otherwise.
	 * The game is over if the number has been correctly guessed
	 * or if all candidates have been eliminated.
	 */
	public boolean isOver() {
		return isOver;
	}
	
	// Returns the guess number and adds it to the list of prior guesses.
	public int getGuess() {
		int guess = 0;
		for(int i = 0; i < guesses.length; i++) {
			if(!guesses[i]) {
				guess = i + 1000;
				this.guess = i + 1000;
				//System.out.println(guess);
				priorGuesses[numGuesses] = i + 1000;
				guesses[i] = true;
				break;
			}
		}
		// TODO: add guess to the list of prior guesses.
		numGuesses++;
		return guess;
	}
	
	/**
	 * Updates guess based on the number of matches of the previous guess.
	 * If nmatches is 4, the previous guess is correct and the game is over.
	 * Check project description for implementation details.
	 * 
	 * Returns true if the update has no error; false if all candidates
	 * have been eliminated (indicating a state of error);
	 */
	public boolean updateGuess(int nmatches) {
		if(nmatches == 4) {
			this.isOver = true;
			return true;
		}
		boolean success = false;
		System.out.println(guess);
		for(int i = 0; i < guesses.length; i++) {
			if(numMatches(i + 1000, guess) == nmatches && !guesses[i]) {
				success = true;
			}
			if((numMatches(i + 1000, guess) != nmatches) && (!guesses[i])) {
				guesses[i] = true;
			}
		}
		return success;
	}
	
	// Returns the list of guesses so far as an integer array.
	// The size of the array must be the number of prior guesses.
	// Returns null if there has been no prior guess
	public int[] priorGuesses() {
		if(numGuesses == 0) {
			return null;
		}
		int[] prior = new int[numGuesses];
		for(int i = 0; i < numGuesses; i++) {
				prior[i] = priorGuesses[i];
		}
		return prior;
	}
}
