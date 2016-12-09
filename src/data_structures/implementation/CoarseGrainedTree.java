package data_structures.implementation;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import data_structures.Sorted;

public class CoarseGrainedTree<T extends Comparable<T>> implements Sorted<T> {
    private TreeNode<T> root;
    private Lock lock = new ReentrantLock(); 
    
    public void add(T t) {
        lock.lock(); 
        try {
        	if(root == null){
        		root = new TreeNode<T>(t);
        		return; 
        	}
        	addRecursive(root, new TreeNode<T>(t)); 
        } finally {
        	lock.unlock();
        }
    }
    
    private void addRecursive(TreeNode<T> lastNode, TreeNode<T> node){
    	if(lastNode.getData().compareTo(node.getData()) > 0){
    		if(lastNode.getLeftChild() == null){ 
    			lastNode.setLeftChild(node);
    			//node.setParent(lastNode);
    			return;
    		} else {
    			addRecursive(lastNode.getLeftChild(), node); 
    		}
    	} else {
    		if(lastNode.getRightChild() == null){
    			lastNode.setRightChild(node);
    			//node.setParent(lastNode);
    			return;
    		} else {
    			addRecursive(lastNode.getRightChild(), node); 
    		}
    	}
    }

    public void remove(T t) { 
    	lock.lock(); 
    	
    	try {
        	if(root == null){
        		return; 
        	}
        	
        	removeRecursive(root, t); 
    	} finally {
    		lock.unlock();
    	}
    }

    private void removeRecursive(TreeNode<T> currentNode, T t){
        if(currentNode == null) {   // ?
            return;
        } else if (currentNode.getData().compareTo(t) < 0) {
            removeRecursive(currentNode.getLeftChild());
        } else if (currentNode.getData().compareTo(t) > 0) {
            removeRecursive(currentNode.getRightChild());
        } else {
            //found the node to remove
            if (currentNode.getLeftChild != null && currentNode.getRightChild != null) {
                TreeNode<T> min = findMin(currentNode.getRightChild());
                currentNode.setData(min.getData);
                min = null;
                // if(min.getParent().getData().compareTo(min.getData()) < 0){
                //     min.getParent().setRightChild(null);
                // } else {
                //     min.getParent().setLeftChild(null);
                // }
                // currentNode.setData(min.getData());
            } else if(currentNode.getLeftChild == null) {
                currentNode = currentNode.getRightChild;
            } else if(currentNode.getRightChild == null) {
                currentNode = currentNode.getLeftChild;
            } else {
                currentNode = null;
            }
        }

    }
    
    // private void removeRecursive(TreeNode<T> currentNode, T t){
    // 	if(currentNode.getData().compareTo(t) == 0){



    // 		if(currentNode.isLeaf()){
    // 			if(currentNode == root){
    // 				root = null;
    // 				return; 
    // 			}
    // 			if(currentNode.getParent().getData().compareTo(t) < 0){
    // 				currentNode.getParent().setRightChild(null);
    // 			} else {
    // 				currentNode.getParent().setLeftChild(null);
    // 			}
    // 		} else if(currentNode.hasOneChild()){
    // 			if(currentNode == root){
    // 				if(currentNode.getLeftChild() != null){
    // 					root = currentNode.getLeftChild();
    // 				} else {
    // 					root = currentNode.getRightChild();
    // 				}
    // 				root.setParent(null);
    // 				return; 
    // 			} else if(currentNode.getLeftChild() != null){
    // 				currentNode.getLeftChild().setParent(currentNode.getParent());
    //     			if(currentNode.getParent().getData().compareTo(t) < 0){
    //     				currentNode.getParent().setRightChild(currentNode.getLeftChild());
    //     			} else {
    //     				currentNode.getParent().setLeftChild(currentNode.getLeftChild());
    //     			}
    // 			} else {
    // 				currentNode.getRightChild().setParent(currentNode.getParent());
    //     			if(currentNode.getParent().getData().compareTo(t) < 0){
    //     				currentNode.getParent().setRightChild(currentNode.getRightChild());
    //     			} else {
    //     				currentNode.getParent().setLeftChild(currentNode.getRightChild());
    //     			}
    // 			}
    // 		} else {
    // 			TreeNode<T> min = findMin(currentNode.getRightChild());
				// if(min.getParent().getData().compareTo(min.getData()) < 0){
    // 				min.getParent().setRightChild(null);
    // 			} else {
    // 				min.getParent().setLeftChild(null);
    // 			}

    // 			currentNode.setData(min.getData());
    // 		}



    // 	} else if(currentNode.getData().compareTo(t) < 0){
    // 		if(currentNode.getRightChild() != null){
    // 			removeRecursive(currentNode.getRightChild(), t); 
    // 		}
    // 	} else {
    // 		if(currentNode.getLeftChild() != null){
    // 			removeRecursive(currentNode.getLeftChild(), t); 
    // 		}
    // 	}
    // }
    
    private TreeNode<T> findMin(TreeNode<T> node){
    	if(node.getLeftChild() == null){
    		return node; 
    	}
    	
    	return findMin(node.getLeftChild()); 
    }

    public ArrayList<T> toArrayList() {
    	ArrayList<T> list = new ArrayList<>();
    	list = addRecursive(root, list);

    	return list; 
    }
    
    private ArrayList<T> addRecursive(TreeNode<T> treeNode, ArrayList<T> arrayList){
    	if(treeNode == null){
    		return arrayList; 
    	}
    	arrayList = addRecursive(treeNode.getLeftChild(), arrayList);
		arrayList.add(treeNode.getData());
    	arrayList = addRecursive(treeNode.getRightChild(), arrayList); 
    	return arrayList;    	
    }
}
