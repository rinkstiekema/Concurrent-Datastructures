package data_structures.implementation;

public class Node<T> {

    private Node next;
    private T data;

    public Node(){
        next = null;
        data = null;
    }

    public Node(T data){
        this.data = data;
        next = null;
    }

    public Node(T data, Node next){
        this.data = data;
        this.next = next;
    }

    public Node getNext(){
        return next;
    }

    public void setNext(Node node){
        this.next = node;
    }

    public int getKey(){
        return data.hashCode();
    }

    public T getData(){
        return data;
    }
}
