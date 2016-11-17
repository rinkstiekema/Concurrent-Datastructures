package data_structures.implementation;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import data_structures.Sorted;

public class CoarseGrainedList<T extends Comparable<T>> implements Sorted<T> {
    private Node head;
    private Lock lock = new ReentrantLock();

    public CoarseGrainedList() {
        Node head = new Node();
    }

    public void add(T t) {
        Node predecessor, current;
        int key = t.hashCode();
        lock.lock();

        try {
            predecessor = head;
            current = head.getNext();
            while(current.getKey() < key && !current.equals(null)){
                predecessor = current;
                current = current.getNext();
            }

            Node node = new Node(t);
            node.setNext(current);
            predecessor.setNext(node);
        } finally {
            lock.unlock();
        }
    }

    public void remove(T t) {
        Node predecessor, current;
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
        Node current = head;
        ArrayList<T> list = new ArrayList<>();
        while(!current.equals(null)){
            list.add(current.getData());
        }

        return list;
    }
}
