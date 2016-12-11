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
        System.out.println(this.toArrayList());
    }

    public void remove(T t) {
        root.lock();
        try {
            if(root.data == null){ //Tree is empty
                return;
            } else {
                Node<T> current = root;
                while(current != null) {
                    if(current.data.compareTo(t) == 0){ // Found node to remove
                        if(current.left == null){
                            System.out.println("right");
                            current = current.right;
                        } else if(current.right == null){
                            System.out.println("left");
                            current = current.left;
                        } else {
                            current.right.lock();
                            current.data = returnAndRemoveSmallest(current.right, current);
                        }
                        break;
                    } else if(current.data.compareTo(t) > 0){ // Go Left
                        if(current.left != null) {
                            current.left.lock();
                            current.unlock();
                            current = current.left;
                        } else {
                            break;
                        }
                    } else { // Go Right
                        if(current.right != null) {
                            current.right.lock();
                            current.unlock();
                            current = current.right;
                        } else {
                            break;
                        }
                    }
                }
            }
        } finally {
            ;
        }
        System.out.println(this.toArrayList());
    }

    private T returnAndRemoveSmallest(Node<T> start, Node<T> startParent){
        System.out.println("return and remove");

        Node<T> current = start,
                last = startParent;
        while(current.left != null){
            last.unlock();
            last = current;
            current.left.lock();
            current = current.left;
        }

        T data = current.data;
        if(last == startParent){
            last.right = null;
        } else {
            last.left = null;
        }

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
