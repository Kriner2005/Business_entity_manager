package co.edu.uptc.interfaces;

import java.util.ArrayList;

public interface IStructureCollection<T> {

    void add(T element);

    T remove();

    ArrayList<T> toArrayLsit();

    int size();

    boolean isEmpty();
}
