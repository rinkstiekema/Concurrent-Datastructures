package data_structures.implementation;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import data_structures.Sorted;

public class CoarseGrainedTree<T extends Comparable<T>> implements Sorted<T> {
	public class Node<T>{
		T data;
		Node<T> left, right;

		public Node(T data) {
			this.data = data;
			this.left = null;
			this.right = null;
		}

		public Node(T data, Node<T> left, Node<T> right) {
			this.data = data == null ? null : data;
			this.left = left;
			this.right = right;
		}
	}

	private Node<T> root;
	private ReentrantLock lock = new ReentrantLock();

	public CoarseGrainedTree(){
		root = null;
	}

	private Node<T> addRecursively(T t, Node<T> root){
		if(root == null){
			return new Node<T>(t);
		}

		if(root.data.compareTo(t) > 0 ){
			root.left = addRecursively(t, root.left);
		} else {
			root.right = addRecursively(t, root.right);
		}
		return root;
	}

	public void add(T t) {
		lock.lock();
		try {
			root = addRecursively(t, root);
		} finally {
			lock.unlock();
		}
		//System.out.println(this.toArrayList().size());
	}

	public void remove(T t) {
		lock.lock();
		try {
			root = removeRecursively(t, root);
		} finally {
			lock.unlock();
		}
		//System.out.println(this.toArrayList().size());
	}

	private Node<T> removeRecursively(T t, Node<T> root){
		if(root.data.compareTo(t) > 0){
			root.left = removeRecursively(t, root.left);
		} else if(root.data.compareTo(t) < 0){
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