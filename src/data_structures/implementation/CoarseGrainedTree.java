package data_structures.implementation;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import data_structures.Sorted;

public class CoarseGrainedTree<T extends Comparable<T>> implements Sorted<T> {
    private TreeNode<T> root;
    private Lock lock = new ReentrantLock();

    public void add(T t) {
        TreeNode<T> current;
        int key = t.hashCode();
        lock.lock();

        try{
            current = root;
            if(current == null){
                root = new TreeNode<>(t);
                return;
            }

            TreeNode<T> parent = null;

            while(true){
                parent = current;
                if(current.getKey() > key){
                    current = current.getLeftChild();
                    if(current == null){
                        parent.setLeftChild(new TreeNode<T>(t));
                        return;
                    }
                } else {
                    current = current.getRightChild();
                    if(current == null){
                        parent.setRightChild(new TreeNode<T>(t));
                        return;
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void remove(T t) { throw new UnsupportedOperationException(); }

    public ArrayList<T> toArrayList() {
        throw new UnsupportedOperationException();
    }
}
