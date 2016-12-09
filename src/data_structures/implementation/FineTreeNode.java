package data_structures.implementation;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineTreeNode<T> {
    private T data;
    private Lock lock;
    private FineTreeNode<T> leftChild;
    private FineTreeNode<T> rightChild;

    public FineTreeNode(T data){
        this.data = data;
        lock = new ReentrantLock();
        this.leftChild = null;
        this.rightChild = null;
    }

    public T getData(){
        return data;
    }

    public FineTreeNode<T> getLeftChild() { return leftChild; }

    public FineTreeNode<T> getRightChild() { return rightChild; }

    public void setLeftChild(FineTreeNode<T> treeNode) { this.leftChild = treeNode; }

    public void setRightChild(FineTreeNode<T> treeNode) { this.rightChild = treeNode; }

    public void lock(){
        lock.lock();
    }

    public void unlock(){
        lock.unlock();
    }

    public void setData(T t) { this.data = t; }

    public boolean isLeaf(){
        if(leftChild == null && rightChild == null){
            return true;
        } else {
            return false;
        }
    }

    public boolean hasOneChild(){
        if(leftChild != null && rightChild == null || leftChild == null && rightChild != null){
            return true;
        } else {
            return false;
        }
    }
}

