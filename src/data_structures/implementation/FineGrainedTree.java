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

        public int numberOfChildren() {
            if (left != null && right != null) {
                return 2;
            } else if (left == null && right == null) {
                return 0;
            } else {
                return 1;
            }
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
                root.data = t;
                root.unlock();
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

    /*public void remove(T t){
        root.lock();
        remove(root, t);
        System.out.println(this.toArrayList());
    }*/

    /*public void remove(Node<T> root, T t){
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
    }*/

    public void remove(T t) {
        System.out.println("Removing: "+t);
        root.lock();
        try {
            if(root.data == null){ //Tree is empty
                root.unlock();
                return;
            } else {
                Node<T> current = root,
                        parent = new Node<T>(null);

                parent.lock();

                while(current != null) {
                    if(current.data.compareTo(t) == 0){ // Found node to remove
                        if(current.numberOfChildren() == 2){
                            System.out.println("2");
                            current.right.lock();
                            current.data = returnAndRemoveSmallest(current, current, current.right);
                        } else if(current.numberOfChildren() == 1){
                            System.out.println("1");
                            if(current.left == null){
                                if(parent.data == null){
                                    root = current.right;
                                } else {
                                    if(parent.data.compareTo(current.data) > 0){
                                        parent.left = current.right;
                                    } else {
                                        parent.right = current.right;
                                    }
                                }

                                parent.unlock();
                                current.unlock();
                            } else {
                                if(parent.data == null){
                                    root = current.left;
                                } else {
                                    if(parent.data.compareTo(current.data) > 0){
                                        parent.left = current.left;
                                    } else {
                                        parent.right = current.left;
                                    }
                                }

                                parent.unlock();
                                current.unlock();
                            }
                        } else {
                            System.out.println("0");
                            if(parent.data == null){
                                root.data = null;
                                parent.unlock();
                                current.unlock();
                                break;
                            } else {
                                if(parent.data.compareTo(current.data) > 0){
                                    parent.left = null;
                                } else {
                                    parent.right = null;
                                }
                                parent.unlock();
                                current.unlock();
                                break;
                            }
                        }
                        break;
                    } else if(current.data.compareTo(t) > 0){ // Go Left
                        System.out.println("Going left");
                        if(current.left != null) {
                            current.left.lock();
                            parent.unlock();
                            parent = current;
                            current = current.left;
                        } else {
                            current.unlock();
                            parent.unlock();
                            break;
                        }
                    } else { // Go Right
                        System.out.println("Going right");
                        if(current.right != null) {
                            current.right.lock();
                            parent.unlock();
                            parent = current;
                            current = current.right;
                        } else {
                            current.unlock();
                            parent.unlock();
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

    private T returnAndRemoveSmallest(Node<T> start, Node<T> last, Node<T> current){
        if(current.left == null){
            if(start == last){
                T data = current.data;
                last.right = current.right;
                last.unlock();
                current.unlock();
                return data;
            }

            T data = current.data;
            last.left = current.right;
            current.unlock();
            last.unlock();
            return data;
        }

        last.unlock();
        current.left.lock();
        return returnAndRemoveSmallest(start, current, current.left);
    }

    public ArrayList<T> toArrayList() {
        ArrayList<T> list = new ArrayList<>();
        list = toArrayList(root, list);

        return list;
    }

    private ArrayList<T> toArrayList(Node<T> node, ArrayList<T> arrayList){
        if(node == root && node.data == null){
            return arrayList;
        }
        if(node == null){
            return arrayList;
        }
        arrayList = toArrayList(node.left, arrayList);
        arrayList.add(node.data);
        arrayList = toArrayList(node.right, arrayList);
        return arrayList;
    }
}
