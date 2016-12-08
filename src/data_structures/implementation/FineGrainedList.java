package data_structures.implementation;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import data_structures.Sorted;

public class FineGrainedList<T extends Comparable<T>> implements Sorted<T> {
    private FineNode<T> head;

    public FineGrainedList() {
        head = new FineNode<T>(null);
    }

    public void add(T t) {
        head.lock();
        FineNode<T> predecessor = head;

        if(head.getNext() == null) {
            head.setNext(new FineNode<T>(t));
            head.unlock();
            return;
        }

        try {
            FineNode<T> current = predecessor.getNext();
            current.lock();

            try {
                while(current.getData().compareTo(t) < 0){
                    if(current.getNext() == null){
                        current.setNext(new FineNode<T>(t));
                        return;
                    }
                    predecessor.unlock();
                    predecessor = current;
                    current = current.getNext();
                    current.lock();
                }

                FineNode<T> newNode = new FineNode<T>(t);
                newNode.setNext(current);
                predecessor.setNext(newNode);
            } finally {
                current.unlock();
            }
        } finally {
            predecessor.unlock();
        }
    }

    public void remove(T t) {
        FineNode<T> predecessor = null,
                    current = null;

        head.lock();
        try {
            predecessor = head;
            if(predecessor.getNext() == null){
                head.unlock();
                return;
            }

            current = predecessor.getNext();
            current.lock();

            try {
                while(current.getData().compareTo(t) < 0){
                    predecessor.unlock();
                    predecessor = current;
                    current = current.getNext();
                    current.lock();
                }

                if(current.getData().compareTo(t) == 0){
                    predecessor.setNext(current.getNext());
                }
            } finally {
                current.unlock();
            }
        } finally {
            predecessor.unlock();
        }
    }

    public ArrayList<T> toArrayList() {
        FineNode<T> current = head.getNext();
        ArrayList<T> list = new ArrayList<>();

        while(current != null){
            list.add((T) current.getData());
            current = current.getNext();
        }

        return list;
    }
}
