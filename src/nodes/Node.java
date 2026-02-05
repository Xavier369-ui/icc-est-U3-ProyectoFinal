package nodes;

public class Node<T> {

    private T value;

    public Node(T value) {
        this.value = value;
    }

    public T getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node)) return false;
        return value.equals(((Node<?>) o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
