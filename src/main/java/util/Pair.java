package util;


import java.util.Map;

public class Pair<T, E> implements Map.Entry<T, E> {

    private T key;
    private E value;

    public Pair(T key, E value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public T getKey() {
        return key;
    }

    @Override
    public E getValue() {
        return value;
    }

    @Override
    public E setValue(E value) {
        return value;
    }
}
