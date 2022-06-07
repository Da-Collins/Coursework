package largeinteger;

import largeinteger.LLNode;

/** The LargeInteger class
 *  This class represents a large, non-negative integer using a linked list.
 *  Each node stores a single digit. The nodes represent all digits in *reverse* order:
 *  the least significant digit is the first node, and the most significant the last node.
 *  For example, 135642 is represented as 2->4->6->5->3->1 in that order.
 */
public class LargeInteger {
	private LLNode<Integer> head;	// head of the list
	private int size;				// size (i.e. number of digits)
	
	// Returns size
	public int size() { return size; }
	// Returns the linked list (used only for JUnit test purpose)
	public LLNode<Integer> getList() { return head; }
	
	public LargeInteger() {
		head = null; size = 0;
	}
	
	/** Constructor that takes a String as input and constructs the linked list.
	 *  You can assume that the input is guaranteed to be valid: i.e. every character
	 *  in the string is between '0' and '9', and the first character is never '0'
	 *  (unless '0' is the only character in the string). You can use input.charAt(i)-'0'
	 *  to convert the character at index i to the integer value of that digit.
	 *  Remember: the list nodes must be in reverse order as the characters in the string.
	 */
	public LargeInteger(String input) {
		this.size = input.length();
		this.head = new LLNode<Integer>(input.charAt(input.length() - 1)-'0', null);
		LLNode<Integer> curr = this.head;
		for(int i = input.length() - 2; i >= 0; i = i - 1) {
			int data = input.charAt(i)-'0';
			curr.link = new LLNode<Integer>(data, null);
			curr = curr.link;
		}
	}
	
	/** Divide *this* large integer by 10 and return this.
	 *  Assume integer division: for example, 23/10 = 2, 8/10 = 0 and so on.
	 */
	public LargeInteger divide10() {
		if(this.head.data == 0) {
			return this;
		}
		else if(size == 1){
			this.head.data = 0;
			return this;
		}
		this.head = this.head.link;
		size = size - 1;
		return this;
	}
	
	/** Multiply *this* large integer by 10 and return this.
	 *  For example, 23*10 = 230, 0*10 = 0 etc.
	 */
	public LargeInteger multiply10() {
		if(this.head.data == 0) {
			return this;
		}
		LLNode<Integer> top = new LLNode<>(0, this.head);
		this.head = top;
		this.size++;
		return this;
	}
	
	/** Returns a *new* LargeInteger object representing the sum of this large integer
	 *  and another one (given by that). Your code must correctly handle cases such as
	 *  the two input integers have different sizes (e.g. 2+1000=1002), or there is a
	 *  carry over at the highest digit (e.g. 9999+2=10001).
	 */
	public LargeInteger add(LargeInteger that) {
		String added = "";
		if(that.getList().data == 0 && that.getList().link == null) {
			added = this.toString();
			return new LargeInteger(added);
		}
		else if(this.head.data == 0 && this.head.link == null) {
			added = that.toString();
			return new LargeInteger(added);
		}
		LLNode<Integer> first = that.getList();
		LLNode<Integer> second = this.head;
		int remainder = 0;
		while(first != null && second != null) {
			int add = first.data + second.data + remainder;
			remainder = 0;
			if(add >= 10) {
				remainder = add / 10;
				add = add % 10;
			}
			added = "" + add + added;
			first = first.link;
			second = second.link;
		}
		if(first == null && second != null) {
			while(second != null) {
				int add = second.data + remainder;
				remainder = 0;
				if(add >= 10) {
					remainder = add / 10;
					add = add % 10;
				}
				added = "" + add + added;
				second = second.link;
			}
		}
		else if(first != null && second == null) {
			while(first != null) {
				int add = first.data + remainder;
				remainder = 0;
				if(add >= 10) {
					remainder = add / 10;
					add = add % 10;
				}
				added = "" + add + added;
				first = first.link;
			}
		}
		if(remainder > 0 && first == null && second == null) {
			added = "" + remainder + added;
		}
		return new LargeInteger(added);
	}
	
	/** Returns a new LargeInteger object representing the result of multiplying
	 *  this large integer with a non-negative integer x. You can assume x is either
	 *  a positive integer or 0. Hint: you can use a loop and call the 'add' method
	 *  above to accomplish the 'multiply'.
	 */
	public LargeInteger multiply(int x) {
		if(x == 0) {
			return new LargeInteger("0");
		}
		LargeInteger multiply = new LargeInteger(this.toString());
		for(int i = 1; i < x; i++) {
			multiply = multiply.add(this);
		}
		return multiply;
	}

	/** Recursive method that converts the list referenced by curr_node back to
	 *  a string representing the integer. Think about what's the base case and
	 *  what it should return. Then think about what it should return in non-base case.
	 *  Hint: refer to the 'printing a list backwards' example we covered in lectures.
	 */
	private String toString(LLNode<Integer> curr_node) {
		if(curr_node.link == null) {
			return curr_node.data.toString();
		}
		else {
			return toString(curr_node.link) + curr_node.data.toString();
		}
	}
	
	/** Convert this list back to a string representing the large integer.
	 *  This is a public method that jump-starts the call to the recursive method above.
	 */
	public String toString() {
		return toString(head);
	}
	
	// Recursive method to compute factorial
	public static LargeInteger factorial(int n) {
		if(n==0) return new LargeInteger("1");
		return factorial(n-1).multiply(n);
	}
	
	// Recursive method to compute power
	public static LargeInteger pow(int x, int y) {
		if(y==0) return new LargeInteger("1");
		return pow(x, y-1).multiply(x);
	}
}
