package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements
		BSTInterface<T> {
	protected BSTNode<T> root;

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return subtreeSize(root);
	}

	protected int subtreeSize(BSTNode<T> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + subtreeSize(node.getLeft())
					+ subtreeSize(node.getRight());
		}
	}
	
	//I implemented
	protected void setRoot(BSTNode<T> root) {
		this.root = root;
	}

	//I implemented this
	public boolean contains(T t) {
		return (this.get(t) != null);
	}

	public boolean remove(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		boolean result = contains(t);
		if (result) {
			root = removeFromSubtree(root, t);
		}
		return result;
	}

	private BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
		// node must not be null
		int result = t.compareTo(node.getData());
		if (result < 0) {
			node.setLeft(removeFromSubtree(node.getLeft(), t));
			return node;
		} else if (result > 0) {
			node.setRight(removeFromSubtree(node.getRight(), t));
			return node;
		} else { // result == 0
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else { // neither child is null
				T predecessorValue = getHighestValue(node.getLeft());
				node.setLeft(removeRightmost(node.getLeft()));
				node.setData(predecessorValue);
				return node;
			}
		}
	}

	private T getHighestValue(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} else {
			return getHighestValue(node.getRight());
		}
	}

	private BSTNode<T> removeRightmost(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} else {
			node.setRight(removeRightmost(node.getRight()));
			return node;
		}
	}

	//I implemented This
	public T get(T t) {
		if(t == null) {
			throw new NullPointerException();
		}
		else if(this.root == null) {
			return null;
		}
		Iterator<T> treeIt = this.inorderIterator();
		while(treeIt.hasNext()) {
			if(treeIt.next().equals(t)) {
				return t;
			}
		}
		return null;
	}

	public void add(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		root = addToSubtree(root, new BSTNode<T>(t, null, null));
	}

	protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
		if (node == null) {
			return toAdd;
		}
		int result = toAdd.getData().compareTo(node.getData());
		if (result <= 0) {
			node.setLeft(addToSubtree(node.getLeft(), toAdd));
		} else {
			node.setRight(addToSubtree(node.getRight(), toAdd));
		}
		return node;
	}

	//I implemented this
	@Override
	public T getMinimum() {
		if(this.root == null) {
			return null;
		}
		return this.getMinimum(this.root);
	}
	
	private T getMinimum(BSTNode<T> node) {
		if(node.getLeft() == null) {
			return node.getData();
		}
		else {
			return getMinimum(node.getLeft());
		}			
	}

	//I implemented this
	@Override
	public T getMaximum() {
		if(this.root == null) {
			return null;
		}
		return this.getMaximum(this.root);
	}

	private T getMaximum(BSTNode<T> node) {
		if(node.getRight() == null) {
			return node.getData();
		}
		else {
			return getMaximum(node.getRight());
		}			
	}

	//I implemented this WRONG
	@Override
	public int height() {
		return this.height(this.root, -1, 0);
	}
	
	private int height(BSTNode<T> node, int max, int curr) {
		if(node == null) {
			if(curr > max) {
				return curr - 1;
			}
			return max;
		}
		else {
			curr += 1;
			int left = this.height(node.getLeft(), max, curr);
			if(left > max) { max = left;}
			int right = this.height(node.getRight(), max, curr);
			if(right > max) { max = right;}
			return max;
		}
	}

	//I implemented this
	public Iterator<T> preorderIterator() {
		Queue<T> queue = new LinkedList<>();
		preorderTraverse(queue, this.root);
		return queue.iterator();
	}
	
	private void preorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if(node != null) {
			queue.add(node.getData());
			preorderTraverse(queue, node.getLeft());
			preorderTraverse(queue, node.getRight());
		}
	}


	public Iterator<T> inorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, root);
		return queue.iterator();
	}


	private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			inorderTraverse(queue, node.getLeft());
			queue.add(node.getData());
			inorderTraverse(queue, node.getRight());
		}
	}
	
	//I implemented this
	public Iterator<T> postorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		postorderTraverse(queue, root);
		return queue.iterator();
	}
	
	private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if(node != null) {
			postorderTraverse(queue, node.getLeft());
			postorderTraverse(queue, node.getRight());
			queue.add(node.getData());
		}
	}


	//I implemented think its wrong
	@Override
	public boolean equals(BSTInterface<T> other) {
		if(other.size() != this.size()) {return false;}
		return this.equals(this.root, other.getRoot(), true);
	}
	
	private boolean equals(BSTNode<T> one, BSTNode<T> two, boolean equal) {
		if(!equal) {return equal;}
		else if((one == null && two != null) || one != null && two == null) {
			equal = false;
			return equal;
		}
		else if(one == null && two == null) {
			return equal;
		}
		else {
			if(!one.getData().equals(two.getData())) {
				equal = false;
				return equal;
			}
			equals(one.getLeft(), two.getLeft(), equal);
			equals(one.getRight(), two.getRight(), equal);
			return equal;
		}
	}


	//I implemented this
	@Override
	public boolean sameValues(BSTInterface<T> other) {
		if(this.root == null && other.getRoot() == null) {
			return true;
		}
		if(this.size() != other.size()) {
			return false;
		}
		Iterator<T> one = this.inorderIterator();
		Iterator<T> two = this.inorderIterator();
		while(one.hasNext() && two.hasNext()) {
			if(one.next().compareTo(two.next()) == 0) {
				continue;
			}
			else {
				return false;
			}
		}
		return true;
	}

	// I implemented this
	@Override
	public boolean isBalanced() {
		return (Math.pow(2, this.height()) <= this.size() && this.size() <= Math.pow(2, this.height() + 1));
	}

	//I implemented this
	@Override
    @SuppressWarnings("unchecked")

	public void balance() {
		Queue<T> balance = new LinkedList<>();
		this.inorderTraverse(balance, root);
		Iterator<T> balanceIT = balance.iterator();
		T[] sortedArray = (T[]) new Comparable[this.size()];
		int i = 0;
		while(balanceIT.hasNext()) {
			sortedArray[i] = balanceIT.next();
			i += 1;
		}
		this.root = sortedArray2BST(sortedArray, 0, sortedArray.length - 1);
	}
	
	public BSTNode<T> sortedArray2BST(T[] sorted, int lower, int upper){
		if(lower > upper) {
			return null;
		}
		int mid = (upper + lower)/2;
		BSTNode<T> node = new BSTNode<T>(sorted[mid], null, null);
		node.setLeft(sortedArray2BST(sorted, lower, mid - 1));
		node.setRight(sortedArray2BST(sorted, mid + 1, upper));
		return node;
	}


	@Override
	public BSTNode<T> getRoot() {
        // DO NOT MODIFY
		return root;
	}

	public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
		// header
		int count = 0;
		String dot = "digraph G { \n";
		dot += "graph [ordering=\"out\"]; \n";
		// iterative traversal
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(root);
		BSTNode<T> cursor;
		while (!queue.isEmpty()) {
			cursor = queue.remove();
			if (cursor.getLeft() != null) {
				// add edge from cursor to left child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getLeft().getData().toString() + ";\n";
				queue.add(cursor.getLeft());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}
			if (cursor.getRight() != null) {
				// add edge from cursor to right child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getRight().getData().toString() + ";\n";
				queue.add(cursor.getRight());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}

		}
		dot += "};";
		return dot;
	}

	public static void main(String[] args) {
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			BSTInterface<String> tree = new BinarySearchTree<String>();
			for (String s : new String[] { "d", "b", "a", "c", "f", "e", "g" }) {
				tree.add(s);
			}
			Iterator<String> iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.preorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.postorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();

			System.out.println(tree.remove(r));

			iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
		}

		BSTInterface<String> tree = new BinarySearchTree<String>();
		for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			tree.add(r);
		}
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
		tree.balance();
		System.out.println(tree.size());
		System.out.print(BinarySearchTree.toDotFormat(tree.getRoot()));
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
	}
}