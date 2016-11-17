    public static class Node<T> {
        private T data;
        private Node<T> parent;
        private Node<T> leftChild;
        private Node<T> rightChild;

        public Node(T data){
            this.data = data;
        }

        public Node(T data, Node<T> parent){
            this.data = data;
            this.parent = parent;
        }
    }