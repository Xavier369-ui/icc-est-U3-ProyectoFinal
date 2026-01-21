package nodes;

public class Node<T> {

    private T value;

    public Node(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node<?> node = (Node<?>) o;
        return value.equals(node.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
