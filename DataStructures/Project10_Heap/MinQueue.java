package structures;

import comparators.ReverseIntegerComparator;

import java.util.Comparator;
import java.util.Iterator;

public class MinQueue<V> implements PriorityQueue<Integer, V> {

	StudentArrayHeap<Integer, V> queue = new StudentArrayHeap<Integer, V>(new ReverseIntegerComparator());
	
	@Override
	public PriorityQueue<Integer, V> enqueue(Integer priority, V value) {
		queue.add(priority,  value);
		return this;
	}

	@Override
	public V dequeue() {
		if(queue.isEmpty()) {
			throw new IllegalStateException();
		}
		return queue.remove();
	}

	@Override
	public V peek() {
		if(queue.isEmpty()) {
			throw new IllegalStateException();
		}
		return queue.peek();
	}

	@Override
	public Iterator<Entry<Integer, V>> iterator() {
		// TODO Auto-generated method stub
		return queue.heap.iterator();
	}

	@Override
	public Comparator<Integer> getComparator() {
		// TODO Auto-generated method stub
		return queue.getComparator();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return queue.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return queue.isEmpty();
	}
}

