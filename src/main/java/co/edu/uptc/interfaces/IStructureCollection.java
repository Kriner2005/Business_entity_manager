package co.edu.uptc.interfaces;

import java.util.List;

public interface IStructureCollection<C extends IContainer<E>,E> {

    void add(C container, E element);

    E remove(C container);

    List<E> toList(C container);

    int size(C container);

    boolean isEmpty(C container);
}
