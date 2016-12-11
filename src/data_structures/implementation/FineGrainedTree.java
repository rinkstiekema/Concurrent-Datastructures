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

    private Node<T> addRecursively(T t, Node<T> root){
        if(root == null){
            return new Node<T>(t);
        }

        if(root.data.compareTo(t) > 0 ){
            root.unlock();
            if(root.left != null) {
                root.left.lock();
            }
            root.left = addRecursively(t, root.left);
        } else {
            root.unlock();
            if(root.right != null) {
                root.right.lock();
            }
            root.right = addRecursively(t, root.right);
        }
        return root;
    }

    public void add(T t) {
        root.lock();
        try {
            if(root.data == null){ //Tree is empty
                root.data = t;
            } else {
                Node<T> current = root;
                while (true) {
                    if (current.data.compareTo(t) > 0) { //t is smaller than current node
                        if(current.left != null){
                            current.left.lock();
                            try {
                                current = current.left;
                            } finally {
                                current.unlock();
                            }
                        } else { //Found spot to add the new node
                            current.left = new Node<T>(t);
                            break;
                        }
                    } else {  //t is bigger than current node
                        if(current.right != null){
                            current.right.lock();
                            try {
                                current = current.right;
                            } finally {
                                current.unlock();
                            }
                        } else { //Found spot to add the new node
                            current.right = new Node<T>(t);
                            break;
                        }
                    }
                }
            }
        } finally {
            root.unlock();
        }

        System.out.println(this.toArrayList());
    }

    public void remove(T t) {

    }

    private Node<T> removeRecursively(T t, Node<T> root){
        if(root.data.compareTo(t) < 0){
            root.left = removeRecursively(t, root.left);
        } else if(root.data.compareTo(t) > 0){
            root.right = removeRecursively(t, root.right);
        } else {
            if(root.left == null){
                root = root.right;
            } else if(root.right == null){
                root = root.left;
            } else {
                root.data = smallest(root.right);
                root.right = removeRecursively(root.data, root.right);
            }
        }
        return root;
    }

    private T smallest(Node<T> root){
        return root.left == null ? root.data : smallest(root.left);
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
