package guessme;

/**
 * A LinkedList-based implementation of the Guess-A-Number game
 */
public class LinkedListGame {

	private int guess;
	private int numGuesses;
	private boolean isOver;
	private LLIntegerNode head;
	private LLIntegerNode headPrior;
	private LLIntegerNode priorTail;

	
	/********************************************************
	 * NOTE: for this project you must use linked lists
	 * implemented by yourself. You are NOT ALLOWED to use
	 * Java arrays of any type, or any class in the java.util
	 * package (such as ArrayList).
	 *******************************************************/	 
	
	/********************************************************
	 * NOTE: you are allowed to add new methods if necessary,
	 * but DO NOT remove any provided method, and do NOT add
	 * new files (as they will be ignored by the autograder).
	 *******************************************************/
	
	// LinkedListGame constructor method
	public LinkedListGame() {
		this.guess = 0;
		this.headPrior = null;
		this.priorTail = null;
		this.numGuesses = 0;
		this.isOver = false;
		this.head = new LLIntegerNode(1000, null);
		int i = 1001;
		LLIntegerNode previous = this.head;
		while(i < 10000) {
			LLIntegerNode temp = new LLIntegerNode(i, null);
			previous.setLink(temp);
			previous = temp;
			i++;
		}
	}
	
	// Resets data members and game state so we can play again
	public void reset() {
		this.guess = 0;
		this.isOver = false;
		this.numGuesses = 0;
		this.headPrior = null;
		this.priorTail = null;
		this.head = new LLIntegerNode(1000, null);
		int i = 1001;
		LLIntegerNode previous = head;
		while(i < 10000) {
			LLIntegerNode temp = new LLIntegerNode(i, null);
			previous.setLink(temp);
			previous = temp;
			i++;
		}
	}
	
	// Returns true if n is a prior guess; false otherwise.
	public boolean isPriorGuess(int n) {
		LLIntegerNode current = headPrior;
		while(current != null) {
			if(current.getInfo() == n) {
				return true;
			}
			current = current.getLink();
		}
		return false;
	}
	
	// Returns the number of guesses so far.
	public int numGuesses() {
		// TODO
		return this.numGuesses;
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
	public static int numMatches(int a, int b) {
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
	 * or if no candidate is left.
	 */
	public boolean isOver() {
		return this.isOver;
	}
	
	/**
	 * Returns the guess number and adds it to the list of prior guesses.
	 * The insertion should occur at the end of the prior guesses list,
	 * so that the order of the nodes follow the order of prior guesses.
	 */	
	public int getGuess() {
		int guess = this.head.getInfo();
		if(this.headPrior == null) {
			this.headPrior = new LLIntegerNode(this.head.getInfo(), null);
			this.priorTail = this.headPrior;
		}
		else {
			LLIntegerNode temp = new LLIntegerNode(guess, null);
			priorTail.setLink(temp);
			this.priorTail = temp;
		}
		this.head = head.getLink();
		numGuesses++;
		this.guess = guess;
		return guess;
	}
	
	/**
	 * Updates guess based on the number of matches of the previous guess.
	 * If nmatches is 4, the previous guess is correct and the game is over.
	 * Check project description for implementation details.
	 * 
	 * Returns true if the update has no error; false if no candidate 
	 * is left (indicating a state of error);
	 */
	public boolean updateGuess(int nmatches) {
		boolean success = false;
		if(nmatches == 4) {
			this.isOver = true;
			return true;
		}
		LLIntegerNode previous = null;
		LLIntegerNode current = this.head;
		while(current != null) {
			if(numMatches(current.getInfo(), guess) ==  nmatches) {
				success = true;
				previous = current;
				current = current.getLink();
				continue;
			}
			else if(current.getInfo() == this.head.getInfo()) {
					this.head = this.head.getLink();
					current = this.head;
					continue;
			}
			else {
				previous.setLink(current.getLink());
				current = current.getLink();
				continue;
			}
		}
		return success;
	}
	
	// Returns the head of the prior guesses list.
	// Returns null if there hasn't been any prior guess
	public LLIntegerNode priorGuesses() {
		return this.headPrior;
	}
	
	/**
	 * Returns the list of prior guesses as a String. For example,
	 * if the prior guesses are 1000, 2111, 3222, in that order,
	 * the returned string should be "1000, 2111, 3222", in the same order,
	 * with every two numbers separated by a comma and space, except the
	 * last number (which should not be followed by either comma or space).
	 *
	 * Returns an empty string if here hasn't been any prior guess
	 */
	public String priorGuessesString() {
		if(headPrior == null) {
			return "";
		}
		String prior = "" + headPrior.getInfo();
		LLIntegerNode current = this.headPrior.getLink();
		while(current != null) {
			prior += ", " + current.getInfo();
			current = current.getLink();
		}
		return prior;
	}
	
}
