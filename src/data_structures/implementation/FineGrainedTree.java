package data_structures.implementation;

import java.util.ArrayList;

import data_structures.Sorted;

public class FineGrainedTree<T extends Comparable<T>> implements Sorted<T> {
    private FineTreeNode<T> root;

    public void add(T t) {
        if (root == null) {
            root = new FineTreeNode<T>(t);
            System.out.println(this.toArrayList());
            return;
        }

        FineTreeNode<T> lastNode = root;
        try {
            while (true) {
                lastNode.lock();
                if (lastNode.getData().compareTo(t) > 0) {
                    if (lastNode.getLeftChild() == null) {
                        lastNode.setLeftChild(new FineTreeNode<T>(t));
                        break;
                    } else {
                        FineTreeNode<T> node = lastNode.getLeftChild();
                        lastNode.unlock();
                        lastNode = node;
                    }
                } else {
                    if (lastNode.getRightChild() == null) {
                        lastNode.setRightChild(new FineTreeNode<T>(t));
                        break;
                    } else {
                        FineTreeNode<T> node = lastNode.getRightChild();
                        lastNode.unlock();
                        lastNode = node;
                    }
                }
            }
        } finally {
            lastNode.unlock();
        }
        //System.out.println(this.toArrayList());
    }

    public void remove(T t) {
        if (root == null) {
            System.out.println(this.toArrayList());
            return;
        }

        FineTreeNode<T> lastNode = root;
        try {
            while (true) {
                lastNode.lock();
            }
        } finally {
            lastNode.unlock();
        }

        // System.out.println(this.toArrayList());
    }

    public ArrayList<T> toArrayList() {
        ArrayList<T> list = new ArrayList<>();
        list = addRecursive(root, list);

        return list;
    }

    private ArrayList<T> addRecursive(FineTreeNode<T> treeNode, ArrayList<T> arrayList){
        if(treeNode == null){
            return arrayList;
        }
        arrayList = addRecursive(treeNode.getLeftChild(), arrayList);
        arrayList.add(treeNode.getData());
        arrayList = addRecursive(treeNode.getRightChild(), arrayList);
        return arrayList;
    }
}
