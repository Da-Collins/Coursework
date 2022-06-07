package stack;

/**
 * A {@link LinkedStack} is a generic stack that is implemented using
 * a Linked List structure to allow for unbounded size.
 */
public class LinkedStack<T> {
	LLNode<T> head;
	int size;
	
	// TODO: define class variables here

	/**
	 * Remove and return the top element on this stack.
	 * If stack is empty, return null (instead of throw exception)
	 */
	public T pop() {
		if(head == null) {
			return null;
		}
		T info = this.head.info;
		this.head = this.head.link;
		this.size--;
		return info;
	}

	/**
	 * Return the top element of this stack (do not remove the top element).
	 * If stack is empty, return null (instead of throw exception)
	 */
	public T top() {
		if(head == null) {
			return null;
		}
		return head.info;
	}

	/**
	 * Return true if the stack is empty and false otherwise.
	 */
	public boolean isEmpty() {
		return (head == null);
	}

	/**
	 * Return the number of elements in this stack.
	 */
	public int size() {
		return size;
	}

	/**
	 * Pushes a new element to the top of this stack.
	 */
	public void push(T elem) {
		LLNode<T> push = new LLNode<>(elem);
		
		push.link = this.head;
		this.head = push;
		size++;
	}

}
