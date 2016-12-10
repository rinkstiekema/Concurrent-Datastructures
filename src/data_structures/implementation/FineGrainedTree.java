package data_structures.implementation;

import java.util.ArrayList;

import data_structures.Sorted;

public class FineGrainedTree<T extends Comparable<T>> implements Sorted<T> {
    private FineTreeNode<T> root;

    public FineGrainedTree(){
        root = new FineTreeNode<T>(null);
    }

    public void add(T t) {
        try {
            root.lock();
            if (root.getData() == null) {
                root.setData(t);
                System.out.println(this.toArrayList().size());
                return;
            }
        } finally {
            root.unlock();
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
        System.out.println(this.toArrayList().size());
    }

    public void remove(T t) {
        try {
            root.lock();
            if (root.getData() == null) {
                return;
            }
        } finally {
            root.unlock();
        }

        FineTreeNode<T> lastNode = root;
        try {
            while (true) {
                lastNode.lock();
                if(lastNode.getData().compareTo(t) < 0){
                    FineTreeNode<T> child = lastNode.getLeftChild();
                    try {
                        child.lock();
                        if(child.getData().compareTo(t) == 0){
                              if(child.isLeaf()){
                                  child = null;
                              } else if(child.hasOneChild()) {
                                  if(child.getLeftChild() == null)
                              }
                        }
                    } finally {
                        child.unlock();
                    }
                }
            }
        }
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
