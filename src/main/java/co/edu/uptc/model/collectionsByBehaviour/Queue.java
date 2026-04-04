package co.edu.uptc.model.collectionsByBehaviour;

import java.util.List;

import co.edu.uptc.interfaces.IContainer;
import co.edu.uptc.interfaces.IStructureCollection;

public class Queue<E> implements IStructureCollection<IContainer<E>, E> {

    @Override
    public void add(IContainer<E> container, E element) {
        container.addLast(element);
    }

    @Override
    public E remove(IContainer<E> container) {
        return container.removeFirst();

    }

    @Override
    public List<E> toList(IContainer<E> container) {
        return container.toList();
    }

    @Override
    public int size(IContainer<E> container) {
        return container.size();
    }

    @Override
    public boolean isEmpty(IContainer<E> container) {
        return container.isEmpty();
    }
}
