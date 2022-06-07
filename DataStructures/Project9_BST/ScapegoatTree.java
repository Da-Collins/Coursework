package structures;

import java.util.Iterator;
import java.util.Queue;

public class ScapegoatTree<T extends Comparable<T>> extends
		BinarySearchTree<T> {
	private int upperBound;


	@Override
	public void add(T t) {
		if(t == null) {
			throw new NullPointerException();
		}
		upperBound += 1;
		super.add(t);
		double heightCond = Math.log(upperBound) / Math.log(3.0/2.0);
		System.out.println(t);
		if(this.height() > heightCond) {
			System.out.println("heightCond violated");
			//get a level order traversed array of the nodes in the tree
			BSTNode<T>[] levelOrder = new BSTNode[(int) (Math.pow(2, this.height() + 1) - 1)];
			this.levelOrderTraversal(levelOrder, this.root, 0);
			//find the index of the newly added node in the levek ordered array
			int index = -1;
			for(int i = 0; i < levelOrder.length; i++) {
				if(levelOrder[i] != null && levelOrder[i].getData().equals(t)) {
					index = i; 
					break;
				}
			}
			//find the scapegoat and balance that subtree
			int scapeIndex = this.findScapeGoat(levelOrder, levelOrder[index], (index - 1) / 2);
			BSTNode<T> scapeGoat = levelOrder[scapeIndex];
			if(scapeGoat.equals(this.getRoot())) {
				this.balance();
				return;
			}
			//everything good up until here
			else {
				BSTNode parent = levelOrder[(scapeIndex - 1) / 2];//this is an issue
				BinarySearchTree<T> scapeTree = new BinarySearchTree<>();
				scapeTree.setRoot(scapeGoat);
				scapeTree.balance();
				if(scapeTree.getRoot().getData().compareTo((T) parent.getData()) < 0) {
					parent.setLeft(scapeTree.root);
				}
				else {
					parent.setRight(scapeTree.root);
				}
			}
		}
	}
	
	private void levelOrderTraversal(BSTNode<T>[] array, BSTNode<T> node, int index) {
		if(node != null) {
			array[index] = node;
			int left = (2 * index) + 1;
			int right = (2 * index) + 2;
			levelOrderTraversal(array, node.getLeft(), left);
			levelOrderTraversal(array, node.getRight(), right);	
		}
	}
	
	private int findScapeGoat(BSTNode<T>[] levelOrder, BSTNode<T> child, int index) {
		double childSize = this.subtreeSize(child);
		double parentSize = this.subtreeSize(levelOrder[index]);
		if(childSize / parentSize > 2.0/3.0) {
			return index;
		}
		else {
			return this.findScapeGoat(levelOrder, levelOrder[index], (index - 1) / 2);
		}
		
	}

	@Override
	public boolean remove(T element) {
		boolean removed = super.remove(element);
		if(this.upperBound > this.size() * 2) {
			this.balance();
			this.upperBound = this.size();
		}
		return removed;
	}
}
