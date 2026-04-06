package co.edu.uptc.interfaces;

import java.util.List;

public interface IFileStorage<T> {
    void append(T entity); // agrega una línea al final — O(1)

    List<T> loadAll();

    void overwrite(List<T> entities);
}
