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
    }

    public void remove(T t) {
<<<<<<< HEAD
        remove(t, root);
    }

    private void remove(T t, FineTreeNode<T> root){
        System.out.println("Removing: "+t);
        if (root == null) {
            System.out.println(this.toArrayList());
            return;
=======
        try {
            root.lock();
            if (root.getData() == null) {
                return;
            }
        } finally {
            root.unlock();
>>>>>>> 18d02e345d9588d18f107c69d8d04827f7858f90
        }

        FineTreeNode<T> lastNode = new FineTreeNode<T>(null);
        FineTreeNode<T> currentNode = root;
        try {
            currentNode.lock();
            lastNode.lock();
            while (true) {
<<<<<<< HEAD
                if (currentNode.getData().compareTo(t) < 0){
                    lastNode.unlock();
                    lastNode = currentNode;
                    currentNode.getLeftChild().lock();
                    if(currentNode.getLeftChild().getData() != null){
                        currentNode = currentNode.getLeftChild();
                    }
                } else if (currentNode.getData().compareTo(t) > 0){
                    lastNode.unlock();
                    lastNode = currentNode;
                    currentNode.getRightChild().lock();
                    if(currentNode.getRightChild().getData() != null){
                        currentNode = currentNode.getRightChild();
                    }
                } else {
                    System.out.println("found node");
                    if (currentNode.getLeftChild().getData() != null && currentNode.getRightChild().getData() != null) {
                        System.out.println(this.toArrayList());
                        currentNode.getRightChild().lock();
                        currentNode.setData(findMin(currentNode.getRightChild()));
                        remove(currentNode.getData(), currentNode.getRightChild());
                        break;
                    } else if (currentNode.getLeftChild() != null){
                        if(lastNode.getLeftChild() == currentNode){
                            lastNode.setLeftChild(currentNode.getLeftChild());
                        } else {
                            lastNode.setRightChild(currentNode.getLeftChild());
                        }
                        break;
                    } else if (currentNode.getRightChild() != null){
                        if(lastNode.getLeftChild() == currentNode){
                            lastNode.setLeftChild(currentNode.getRightChild());
                        } else {
                            lastNode.setRightChild(currentNode.getRightChild());
                        }
                        break;
                    }
                }
            }
        } finally {
            currentNode.unlock();
            lastNode.unlock();
        }

        System.out.println(this.toArrayList());
    }


    private T findMin(FineTreeNode<T> root){
        FineTreeNode<T> left = new FineTreeNode<T>(null);
        if(root.getLeftChild() != null){
            try {
                root.getLeftChild().lock();
                left = root.getLeftChild();
                root.unlock();
                return findMin(left);
            } finally {
                left.unlock();
            }
        } else {
            return root.getData();

        }
=======
                lastNode.lock();
                if(lastNode.getData().compareTo(t) < 0){
                    FineTreeNode<T> child = lastNode.getLeftChild();
                    try {
                        child.lock();
                        if(child.getData().compareTo(t) == 0){
                              if(child.isLeaf()){
                                  child = null;
                              } else if(child.hasOneChild()) {
                                  if(child.getLeftChild() == null){
                                      
                                  }
                              }
                        }
                    } finally {
                        child.unlock();
                    }
                }
            }
        }
>>>>>>> 18d02e345d9588d18f107c69d8d04827f7858f90
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
