package data_structures.implementation;

public class TreeNode<T> {
    private T data;
    private TreeNode<T> parent;
    private TreeNode<T> leftChild;
    private TreeNode<T> rightChild;

    public TreeNode(T data){
        this.data = data;
        this.leftChild = null;
        this.rightChild = null;
    }

    public TreeNode(T data, TreeNode<T> parent){
        this.data = data;
        this.parent = parent;
        this.leftChild = null;
        this.rightChild = null;
    }

    public int getKey(){
        return data.hashCode();
    }

    public T getData(){
        return data;
    }

    public TreeNode<T> getLeftChild() { return leftChild; }

    public TreeNode<T> getRightChild() { return rightChild; }

    public void setLeftChild(TreeNode<T> treeNode) { this.leftChild = treeNode; }

    public void setRightChild(TreeNode<T> treeNode) { this.rightChild = treeNode; }
}
