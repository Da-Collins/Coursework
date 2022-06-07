package sets;

import java.util.Iterator;
import java.util.NoSuchElementException;

class LinkedNodeIterator<E> implements Iterator<E> {
    LinkedNode<E> next;
  
  // Constructors
  public LinkedNodeIterator(LinkedNode<E> head) {
     this.next = head;
  }

  @Override
  public boolean hasNext() {
    return next != null;
  }

  @Override
  public E next() {
	if(!hasNext()) {
		throw new NoSuchElementException();
	}
	E data = next.getData();
	next = next.getNext();
	return data;
  }

  @Override
  public void remove() {
    // Nothing to change for this method
    throw new UnsupportedOperationException();
  }
}
