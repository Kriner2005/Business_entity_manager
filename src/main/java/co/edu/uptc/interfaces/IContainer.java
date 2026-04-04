package co.edu.uptc.interfaces;

import java.util.List;

public interface IContainer<E> {
    void addLast(E element);

    void addFirst(E element);

    E removeLast();

    E removeFirst();

    E getFirst();

    E getLast();

    E get(int index);

    int size();

    boolean isEmpty();

    List<E> toList();
}
