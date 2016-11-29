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
        head = null;
    }

    public void add(T t) {
        Node<T> predecessor, current;
        lock.lock();
        try {
        	if(head == null){
        		head = new Node<T>(t); 
        		return; 
        	} else if(head.getNext() == null){
        		if(head.getData().compareTo(t) < 0){
        			head.setNext(new Node<T>(t));
        		} else {
        			head = new Node<T>(t, head);
        		}
        		return;
        	}
        	
            predecessor = head;
            current = predecessor.getNext();

            while(current != null && current.getData().compareTo(t) < 0){
                predecessor = current;
                current = current.getNext();
            }
            
            Node<T> node = new Node<>(t);
            node.setNext(current);
            predecessor.setNext(node);

            if(head.getData().compareTo(t) > 0){
                head = node;
            }
        } finally {
            lock.unlock();
        }
    }

    public void remove(T t) {
        Node<T> predecessor, current;
        lock.lock();
       
        try{    	
            predecessor = head;
            current = predecessor.getNext();
            if(predecessor.getData().compareTo(t) == 0){
            	head = current; 
            	return;
            }

            while(current != null && current.getData().compareTo(t) != 0){
                predecessor = current;
                current = current.getNext();
            }
            if(current != null && current.getData().compareTo(t) == 0){
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
