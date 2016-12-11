package data_structures.implementation;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import data_structures.Sorted;

public class FineGrainedTree<T extends Comparable<T>> implements Sorted<T> {
    public class Node<T>{
        T data;
        Node<T> left, right;
        Lock lock;


        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.lock = new ReentrantLock();
        }

        public Node(T data, Node<T> left, Node<T> right) {
            this.data = data == null ? null : data;
            this.left = left;
            this.right = right;
            this.lock = new ReentrantLock();
        }

        public void lock(){
            lock.lock();
        }

        public void unlock(){
            lock.unlock();
        }
    }

    private Node<T> root;

    public FineGrainedTree(){
        root = new Node<T>(null);
    }

    public void add(T t) {
        System.out.println("Adding: "+t);
        root.lock();
        try {
            if(root.data == null){ //Tree is empty
                root = new Node<T>(t);
            } else {
                Node<T> current = root;
                while(true) {
                    if (current.data.compareTo(t) > 0) {
                        if (current.left == null) {
                            current.left = new Node<T>(t);
                            current.unlock();
                            break;
                        } else {
                            current.left.lock();
                            current.unlock();
                            current = current.left;
                        }
                    } else {
                        if (current.right == null) {
                            current.right = new Node<T>(t);
                            current.unlock();
                            break;
                        } else {
                            current.right.lock();
                            current.unlock();
                            current = current.right;
                        }
                    }
                }
            }
        } finally {
            ;
        }
    }

    public void remove(T t) {
        System.out.println("Removing: "+t);
        root.lock();
        try {
            if(root.data == null){ //Tree is empty
                return;
            } else {
                Node<T> current = root;
                while(current != null) {
                    if(current.data.compareTo(t) == 0){ // Found node to remove
                        if(current.left == null){
                            current = current.right;
                            break;
                        } else if(current.right == null){
                            current = current.left;
                            break;
                        } else {
                            current.right.lock();
                            current.data = returnAndRemoveSmallest(current.right);
                            break;
                        }
                    } else if(current.data.compareTo(t) > 0){ // Go Left
                        if(current.left == null){
                            break;
                        }
                        current.left.lock();
                        current.unlock();
                        current = current.left;
                    } else { // Go Right
                        if(current.left == null){
                            break;
                        }
                        current.right.lock();
                        current.unlock();
                        current = current.right;
                    }
                }
            }
        } finally {
            ;
        }
    }

    private T returnAndRemoveSmallest(Node<T> start){
        Node<T> current = start;

        while(current.left != null){
            current.left.lock();
            current.unlock();
            current = current.left;
        }
        T data = current.data;
        current = null;
        return data;
    }

    public ArrayList<T> toArrayList() {
        ArrayList<T> list = new ArrayList<>();
        list = toArrayList(root, list);

        return list;
    }

    private ArrayList<T> toArrayList(Node<T> node, ArrayList<T> arrayList){
        if(node == null){
            return arrayList;
        }
        arrayList = toArrayList(node.left, arrayList);
        arrayList.add(node.data);
        arrayList = toArrayList(node.right, arrayList);
        return arrayList;
    }
}
