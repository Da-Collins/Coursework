package structures;

import java.util.NoSuchElementException;

/**************************************************************************************
 * NOTE: before starting to code, check support/structures/UnboundedQueueInterface.java
 * for detailed explanation of each interface method, including the parameters, return
 * values, assumptions, and requirements
 ***************************************************************************************/
public class Queue<T> implements UnboundedQueueInterface<T> {
	private Node<T> head;
	private Node<T> tail;
	private int size;
	
	public Queue() {		
		this.head = null;
		this.tail = null;
        this.size = 0;
    }
	
	public Queue(Queue<T> other) {
		if(other.isEmpty()) {
			this.head = null;
			this.tail = null;
			this.size = 0;
			return;
		}
		Node<T> curr = other.getHead();
		while(curr != null) {
			this.enqueue(curr.data);
			curr = curr.next;
		}
	}
	
	public Node<T> getHead(){
		return this.head;
	}
	
	@Override
	public boolean isEmpty() {
		return (this.head == null);
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public void enqueue(T element) {
		if(head == null) {
			this.head = new Node<>(element, null);
			this.tail = this.head;
			size += 1;
			return;
		}
		Node<T> enq = new Node<>(element, null);
		this.tail.next = enq;
		this.tail = enq;
        size = size + 1;
	}

	@Override
	public T dequeue() throws NoSuchElementException {
        if(head == null) {
        	throw new NoSuchElementException();
        }
        if(this.head.next == null) {
        	T dat = this.head.data;
        	this.head = null;
        	size = size - 1;
        	return dat;
        }
        T dat = this.head.data;
        this.head = this.head.next;
        size = size - 1;
        return dat;
	}

	@Override
	public T peek() throws NoSuchElementException {
		if(head == null) {
			throw new NoSuchElementException();
		}
        return this.tail.data;
	}

	@Override
	public UnboundedQueueInterface<T> reversed() {
		if(this.head == null) {
			return new Queue<>();
		}
		return nodesReversed(this.head);
	}
	
	public Queue<T> nodesReversed(Node<T> node){
		if(node.next == null) {
			Queue<T> last = new Queue<>();
			last.enqueue(node.data);
			return last;
		}
		else {
			Queue<T> uh = new Queue(nodesReversed(node.next));
			uh.enqueue(node.data);
			return uh;
		}
	}
}

class Node<T> {
	public T data;
	public Node<T> next;
	public Node(T data) { this.data=data;}
	public Node(T data, Node<T> next) {
		this.data = data; this.next=next;
	}
}

