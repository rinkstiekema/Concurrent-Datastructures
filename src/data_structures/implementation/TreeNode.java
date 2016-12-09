package data_structures.implementation;

public class TreeNode<T> {
    private T data;
    private TreeNode<T> leftChild;
    private TreeNode<T> rightChild;

    public TreeNode(T data){
        this.data = data;
        this.leftChild = null;
        this.rightChild = null;
    }

    public T getData(){
        return data;
    }

    public TreeNode<T> getLeftChild() { return leftChild; }

    public TreeNode<T> getRightChild() { return rightChild; }

    public void setLeftChild(TreeNode<T> treeNode) { this.leftChild = treeNode; }

    public void setRightChild(TreeNode<T> treeNode) { this.rightChild = treeNode; }
    
    public void setData(T t) { this.data = t; }
    
    public boolean isLeaf(){
    	if(leftChild == null && rightChild == null){
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean hasOneChild(){
    	if(leftChild != null && rightChild == null || leftChild == null && rightChild != null){
    		return true;
    	} else {
    		return false; 
    	}
    }
}

