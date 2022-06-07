package guessme;

/**
 * This class defines a linked list node storing an integer.
 * Use primitive type int (do not use wrapper class Integer)
 * You must provide the following methods:
 * - a constructor
 * - a setInfo method and a getInfo method
 * - a setLink method and a getLink method
 */
public class LLIntegerNode {
	private int info;
	private LLIntegerNode link;
	
	public LLIntegerNode(int info, LLIntegerNode link) {
		this.info = info;
		this.link = link;
	}
	
	public void setInfo(int info) {
		this.info = info;
	}
	
	public int getInfo() {
		return this.info;
	}
	
	public void setLink(LLIntegerNode link) {
		this.link = link;
	}
	
	public LLIntegerNode getLink() {
		return this.link;
	}
}

