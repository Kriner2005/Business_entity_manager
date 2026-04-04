package co.edu.uptc.interfaces.abstracts;

import co.edu.uptc.interfaces.IStructureCollection;
import co.edu.uptc.model.DoubleLinkedList;

public abstract class CollectionBase<T> implements IStructureCollection<T>{
    private DoubleLinkedList list;
    private IStructureCollection structure;

    public CollectionBase<T> (DoubleLinkedList list, IStructureCollection structure ) {

    }
}
