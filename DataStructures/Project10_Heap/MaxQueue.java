package structures;

import comparators.IntegerComparator;

import java.util.Comparator;
import java.util.Iterator;

public class MaxQueue<V> implements PriorityQueue<Integer, V> {
	private StudentArrayHeap<Integer, V> queue = new StudentArrayHeap<Integer, V>(new IntegerComparator());
	
	@Override
	public PriorityQueue<Integer, V> enqueue(Integer priority, V value) {
		queue.add(priority, value);
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
		return queue.getComparator();
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}
}
