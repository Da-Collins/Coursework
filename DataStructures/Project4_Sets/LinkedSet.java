package sets;

import java.util.Iterator;

/**
 * A LinkedList-based implementation of Set
 */

  /********************************************************
   * NOTE: Before you start, check the Set interface in
   * Set.java for detailed description of each method.
   *******************************************************/
  
  /********************************************************
   * NOTE: for this project you must use linked lists
   * implemented by yourself. You are NOT ALLOWED to use
   * Java arrays of any type, or any Collection-based class 
   * such as ArrayList, Vector etc. You will receive a 0
   * if you use any of them.
   *******************************************************/ 

  /********************************************************
   * NOTE: you are allowed to add new methods if necessary,
   * but do NOT add new files (as they will be ignored).
   *******************************************************/

public class LinkedSet<E> implements Set<E> {
  private LinkedNode<E> head = null;

  // Constructors
  public LinkedSet() {
  }

  public LinkedSet(E e) {
    this.head = new LinkedNode<E>(e, null);
  }

  private LinkedSet(LinkedNode<E> head) {
    this.head = head;
  }

  @Override
  public int size() {
    int size = 0;
    Set<E> set = new LinkedSet<>(head);
    for(E e: set) {
    	size++;
    }
    return size;
  }

  @Override
  public boolean isEmpty() {
    return head == null;
  }

  @Override
  public Iterator<E> iterator() {
    return new LinkedNodeIterator<E>(this.head);
  }

  @Override
  public boolean contains(Object o) {
    Set<E> contain = new LinkedSet<>(head);
    for(E e: contain) {
    	if(e.equals(o)) {
    		return true;
    	}
    }
    return false;
  }

  @Override
  public boolean isSubset(Set<E> that) {
    Set<E> subset = new LinkedSet<>(head);
    for(E e: subset) {
    	if(that.contains(e)) {
    		continue;
    	}
    	return false;
    }
    return true;
  }

  @Override
  public boolean isSuperset(Set<E> that) {
    Set<E> supa = new LinkedSet<>(head);
    return that.isSubset(supa);
  }

  @Override
  public Set<E> adjoin(E e) {
    Set<E> adjoin = new LinkedSet<>(head);
    if(adjoin.contains(e)) {
    	return adjoin;
    }
    return new LinkedSet<>(new LinkedNode<>(e, head));
  }

  @Override
  public Set<E> union(Set<E> that) {
    Set<E> union = that;
    Set<E> uh = new LinkedSet<>(head);
    for(E e: uh) {
    	union = union.adjoin(e);
    }
    return union;
  }

  @Override
  public Set<E> intersect(Set<E> that) {
    Set<E> intersect = new LinkedSet<E>();
    Set<E> uh = new LinkedSet<>(head);
    for(E e: uh) {
    	if(that.contains(e)) {
    		intersect = intersect.adjoin(e);
    	}
    }
    return intersect;
  }

  @Override
  public Set<E> subtract(Set<E> that) {
	Set<E> subtract = new LinkedSet<E>();
    Set<E> uh = new LinkedSet(head);
    for(E e: uh) {
    	if(!that.contains(e)) {
    		subtract = subtract.adjoin(e);
    	}
    }
    return subtract;
  }

  @Override
  public Set<E> remove(E e) {
    Set<E> removed = new LinkedSet<E>(head);
    if(!removed.contains(e)) {
    	return removed;
    }
    Set<E> second = new LinkedSet<E>();
    for(E i: removed) {
    	if(!i.equals(e)) {
    		second = second.adjoin(i);
    	}
    }
    return second;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object o) {
    if (! (o instanceof Set)) {
      return false;
    }
    Set<E> that = (Set<E>)o;
    return this.isSubset(that) && that.isSubset(this);
  }

  @Override
    public int hashCode() {
    int result = 0;
    for (E e : this) {
      result += e.hashCode();
    }
    return result;
  }
}
