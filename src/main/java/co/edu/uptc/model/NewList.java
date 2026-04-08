package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.edu.uptc.interfaces.IContainer;

public class NewList<T> implements IContainer<T> {

    private final List<T> list;

    public NewList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void addLast(T element) {
        list.add(element);
    }

    @Override
    public void addFirst(T element) {
        list.add(0, element);
    }

    @Override
    public T removeLast() {
        if (isEmpty()) return null;
        return list.remove(list.size() - 1);
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) return null;
        return list.remove(0);
    }

    @Override
    public T getFirst() {
        if (isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public T getLast() {
        if (isEmpty()) return null;
        return list.get(list.size() - 1);
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= list.size()) return null;
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public List<T> toList() {
        return Collections.unmodifiableList(list);
    }
}