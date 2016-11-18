package data_structures.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import data_structures.Sorted;

public class CoarseGrainedList<T extends Comparable<T>> implements Sorted<T> {
    private Node<T> head;
    private Lock lock = new ReentrantLock();

    public CoarseGrainedList() {
        head = new Node(Integer.MIN_VALUE);
        head.setNext(new Node(Integer.MAX_VALUE));
    }

    public void add(T t) {
        Node<T> predecessor, current;
        int key = t.hashCode();
        lock.lock();

        try {
            predecessor = head;
            current = predecessor.getNext();
            while(current.getKey() < key){
                predecessor = current;
                current = current.getNext();
            }
            
            Node<T> node = new Node<>(t);
            node.setNext(current);
            predecessor.setNext(node);
        } finally {
            lock.unlock();
        }
    }

    public void remove(T t) {
        Node<T> predecessor, current;
        int key = t.hashCode();
        lock.lock();

        try{
            predecessor = head;
            current = predecessor.getNext();
            while(current.getKey() < key){
                predecessor = current;
                current = current.getNext();
            }
            if(key == current.getKey()){
                predecessor.setNext(current.getNext());
            }
            
        } finally {
            lock.unlock();
        }
    }

    public ArrayList<T> toArrayList() {
        Node<T> current = head;
        ArrayList<T> list = new ArrayList<>();
        while(current != null){
            list.add((T) current.getData());
            current = current.getNext();
        }

        return list;
    }
}
