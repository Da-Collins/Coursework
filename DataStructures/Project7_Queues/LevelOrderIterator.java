package filesystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import structures.Queue;


/**
 * An iterator to perform a level order traversal of part of a 
 * filesystem. A level-order traversal is equivalent to a breadth-
 * first search.
 */
public class LevelOrderIterator extends FileIterator<File> {
	private Queue<File> files = new Queue<>();
	/**
	 * Instantiate a new LevelOrderIterator, rooted at the rootNode.
	 * @param rootNode
	 * @throws FileNotFoundException if the rootNode does not exist
	 */
	public LevelOrderIterator(File rootNode) throws FileNotFoundException {
		if(!rootNode.exists()) {
			throw new FileNotFoundException();
		}
		this.files.enqueue(rootNode);
	}
	
	@Override
	public boolean hasNext() {
        return !files.isEmpty();
	}

	@Override
	public File next() throws NoSuchElementException {
		if(files.isEmpty()) {
			throw new NoSuchElementException();
		}
		File top = files.dequeue();
		if(top.isDirectory()) {
			File[] children = top.listFiles();
			Arrays.sort(children);
			for(File f: children) {
				files.enqueue(f);
			}
		}
		return top;
	}

	@Override
	public void remove() {
		// Leave this one alone.
		throw new UnsupportedOperationException();		
	}

}
