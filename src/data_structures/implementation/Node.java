package data_structures.implementation;

public class Node<T> {

    private Node<T> next;
    private T data;

    public Node(){
    	data = null;
    	next = null;
    }
    
    public Node(T data){
        this.data = data;
        next = null;
    }

    public Node(T data, Node<T> next){
        this.data = data;
        this.next = next;
    }

    public Node<T> getNext(){
        return next;
    }

    public void setNext(Node<T> node){
        this.next = node;
    }

    public int getKey(){
        return data.hashCode();
    }

    public T getData(){ return data; }
}
