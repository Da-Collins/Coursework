package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RecursiveList<T> implements ListInterface<T> {
	private LLNode<T> head = new LLNode<>();
	private LLNode<T> tail = new LLNode<>();
	private int size;
	
	public RecursiveList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new RecursiveListIterator<>(this);
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public ListInterface<T> insertFirst(T elem) {
		if(this.head == null) {
			this.head = new LLNode(elem, null);
			this.tail = this.head;
			size++;
			return this;
		}
		LLNode<T> first = new LLNode<>(elem, this.head);
		this.head = first;
		size++;
		return this;
	}

	@Override
	public ListInterface<T> insertLast(T elem) {
		if(this.head == null) {
			this.head = new LLNode(elem, null);
			this.tail = this.head;
			size++;
			return this;
		}
		LLNode<T> last = new LLNode<>(elem, null);
		this.tail.next = last;
		this.tail = last;
		size++;
		return this;
	}

	@Override
	public ListInterface<T> insertAt(int index, T elem) {
		if(index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		if(index == 0) {
			return insertFirst(elem);
		}
		if(index == size) {
			return insertLast(elem);
		}
		else {
			insertAt(index, elem, 0, this.head);
			size++;
			return this;
		}
	}
	
	private void insertAt(int index, T elem, int currIndex, LLNode<T> currNode){
		if(index - 1 == currIndex) {
			LLNode<T> insert = new LLNode<>(elem, currNode.next);
			currNode.next = insert;
			return;
		}
		else {
			currIndex = currIndex + 1;
			insertAt(index, elem, currIndex, currNode.next);
			return;
		}
	}

	@Override
	public T removeFirst() {
		if(this.head == null) {
			throw new IllegalStateException();
		}
		if(head.next == null) {
			T dat = this.head.data;
			this.head = null;
			size--;
			return dat;
		}
		T dat = this.head.data;
		this.head = this.head.next;
		size--;
		return dat;
	}

	@Override
	public T removeLast() {
		if(this.head == null) {
			throw new IllegalStateException();
		}
		if(this.head == this.tail) {
			T dat = this.head.data;
			this.head = null;
			this.tail = null;
			size--;
			return dat;
		}
		size--;
		return removeLast(this.head);
	}
	
	private T removeLast(LLNode<T> currNode) {
		if(currNode.next == this.tail) {
			T dat = currNode.next.data;
			currNode.next = null;
			this.tail = currNode;
			return dat;
		}
		else {
			return this.removeLast(currNode.next);
		}
	}

	@Override
	public T removeAt(int i) {
		if(this.size <= i || i < 0) {
			throw new IndexOutOfBoundsException();
		}
		if(i == 0) {
			return removeFirst();
		}
		size--;
		return this.removeAt(i, 0, this.head, null);
	}
	
	private T removeAt(int i, int currIndex, LLNode<T> currNode, LLNode<T> prevNode) {
		if(currIndex == i && currNode == this.tail) {
			T dat = currNode.data;
			prevNode.next = null;
			this.tail = prevNode;
			return dat;
		}
		else if(currIndex == i) {
			T dat = currNode.data;
			prevNode.next = currNode.next;
			return dat;
		}
		else {
			currIndex = currIndex + 1;
			return this.removeAt(i, currIndex, currNode.next, currNode);
		}
	}

	@Override
	public T getFirst() {
		if(this.head == null) {
			throw new IllegalStateException();
		}
		return head.data;
	}

	@Override
	public T getLast() {
		if(this.head == null) {
			throw new IllegalStateException();
		}
		return tail.data;
	}

	@Override
	public T get(int i){
		if(this.size <= i || i < 0) {
			throw new IndexOutOfBoundsException();
		}
		return this.get(i, 0, this.head);
	}
	
	private T get(int i, int currIndex, LLNode<T> currNode) {
		if(i == currIndex) {
			return currNode.data;
		}
		else {
			currIndex = currIndex + 1;
			return this.get(i, currIndex, currNode.next);
		}
	}

	@Override
	public boolean remove(T elem) {
		if(indexOf(elem) == -1) {
			return false;
		}
		this.removeAt(indexOf(elem));
		return true;
	}

	@Override
	public int indexOf(T elem) {
		return indexOf(elem, 0, this.head);
	}
	
	private int indexOf(T elem, int currIndex, LLNode<T> currNode) {
		if(currNode.data == elem) {
			return currIndex;
		}
		else if(currNode.next == null) {
			return - 1;
		}
		else {
			currIndex = currIndex + 1;
			return indexOf(elem, currIndex, currNode.next);
		}		
	}

	@Override
	public boolean isEmpty() {
		return (this.size == 0);
	}
	
}
