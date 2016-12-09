package data_structures.implementation;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineNode<T>{

    private FineNode<T> next;
    private T data;
    public Lock lock;

    public FineNode(){
        data = null;
        next = null;
        lock = new ReentrantLock();
    }

    public FineNode(T data){
        this.data = data;
        next = null;
        lock = new ReentrantLock();
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public FineNode<T> getNext(){
        return next;
    }

    public void setNext(FineNode<T> node){
        this.next = node;
    }

    public T getData(){ return data; }
}
