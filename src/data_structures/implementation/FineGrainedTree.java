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

    public void remove(T t){
        root.lock();
        remove(root, t);
        System.out.println(this.toArrayList());
    }

    public void remove(Node<T> root, T t){
        System.out.println("Removing: "+t);
        if(root.data == null){
            root.unlock();
            return;
        }

        if(root.data.compareTo(t) > 0) {
            System.out.println("Going left");
            root.left.lock();
            root.unlock();
            remove(root.left, t);
        } else if(root.data.compareTo(t) < 0) {
            System.out.println("Going right");
            root.right.lock();
            root.unlock();
            remove(root.right, t);
        } else {
            System.out.println("Found it");
            if(root.left != null && root.right != null){
                System.out.println("2 children");
                root.right.lock();
                root.data = returnAndRemoveSmallest(root, root.right);
            } else if(root.left != null){
                System.out.println("Left child");
                root.left.lock();

                root.data = root.left.data;
                root.right = root.left.right;
                root.left = root.left.left;

                root.unlock();
            } else if(root.right != null) {
                System.out.println("Right child");
                root.right.lock();

                root.data = root.right.data;
                root.left = root.right.left;
                root.right = root.right.right;

                root.unlock();
            } else {
                System.out.println("0 children");
                root = null;
            }
        }
    }

    /*public void remove(T t) {
        root.lock();
        try {
            if(root.data == null){ //Tree is empty
                root.unlock();
                return;
            } else {
                Node<T> current = root;
                while(current != null) {
                    if(current.data.compareTo(t) == 0){ // Found node to remove
                        if(current.left == null){

                        } else if(current.right == null){

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
    }*/

    private T returnAndRemoveSmallest(Node<T> last, Node<T> current){
        if(current.left == null){
            if(last.data.compareTo(current.data) < 0){
                last.right = null;
                last.unlock();
                T data = current.data;
                current.unlock();
                return data;
            }
            last.left = null;
            T data = current.data;
            current.unlock();
            last.unlock();
            return data;
        }

        last.unlock();
        current.left.lock();
        return returnAndRemoveSmallest(current, current.left);
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
