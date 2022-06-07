package structures;

import java.util.Comparator;
import java.util.Iterator;

import comparators.StringLengthComparator;

public class StudentArrayHeap<P, V> extends AbstractArrayHeap<P, V> {

	
	public StudentArrayHeap(Comparator<P> comparator) {
		super(comparator);
	}

	@Override
	protected int getLeftChildOf(int index) {
		if(index < 0) {
			throw new IndexOutOfBoundsException();
		}
		return (index * 2) + 1;
	}

	@Override
	protected int getRightChildOf(int index) {
		if(index < 0) {
			throw new IndexOutOfBoundsException();
		}
		return (index * 2) + 2;
	}

	@Override
	protected int getParentOf(int index) {
		if(index < 1) {
			throw new IndexOutOfBoundsException();
		}
		return (index - 1) / 2;
	}

	@Override
	protected void bubbleUp(int index) {
		Entry<P, V> elem = super.heap.get(index);
		int parent = (index - 1) / 2;
		while(index > 0 && super.comparator.compare(elem.getPriority(), super.heap.get(parent).getPriority()) > 0) {
			super.heap.set(index, super.heap.get(parent));
			index = parent;
			parent = (parent -1) / 2;
		}
		super.heap.set(index, elem);
	}

	@Override
	protected void bubbleDown(int index) {
		Entry<P, V> elem = super.heap.get(index);
		int left = (index * 2) + 1;
		int right = (index * 2) + 2;
		while(index < super.heap.size() / 2 && right < super.heap.size()) {
			Entry<P, V> largerChild;
			int largerIndex;
			P leftPr = super.heap.get(left).getPriority();
			P rightPr = super.heap.get(right).getPriority();
			if(super.comparator.compare(rightPr, leftPr) > 0) {
				largerIndex = right;
				largerChild = super.heap.get(right);				
			}
			else {
				largerIndex = left;
				largerChild = super.heap.get(left);
			}
			if(super.comparator.compare(elem.getPriority(), largerChild.getPriority()) < 0) {
				super.heap.set(index, largerChild);
				index = largerIndex;
				left = (index * 2) + 1;
				right = (index * 2) + 2;
			}
			else {
				break;
			}
		}
		if(left < super.heap.size() && super.comparator.compare(super.heap.get(left).getPriority(), elem.getPriority()) > 0){
			super.heap.set(index, super.heap.get(left));
			super.heap.set(left,  elem);
		}
		else{
			super.heap.set(index, elem);
		}
	}
}

