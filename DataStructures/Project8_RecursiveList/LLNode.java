package structures;

public class LLNode<T> {
	public T data;
	public LLNode<T> next;
	
	public LLNode(){
		this.data = null;
		this.next = null;
	}
	public LLNode(T data, LLNode<T> next){
		this.data = data;
		this.next = next;
	}
	
}
