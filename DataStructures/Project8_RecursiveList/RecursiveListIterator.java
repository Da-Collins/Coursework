package structures;

import java.util.Iterator;

public class RecursiveListIterator<T> implements Iterator {
	
	private int currIndex;
	private RecursiveList<T> list = new RecursiveList<>();
	
	public RecursiveListIterator(RecursiveList<T> list) {
		this.list = list;
		this.currIndex = 0;
	}

	@Override
	public boolean hasNext() {
		return (currIndex == list.size());
	}

	@Override
	public T next() {
		T next = list.get(currIndex);
		currIndex++;
		return next;
	}

}
