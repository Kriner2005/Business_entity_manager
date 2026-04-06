package co.edu.uptc.interfaces;

import java.util.List;

public interface IFileStorage<T> {
    void append(T entity);

    List<T> loadAll();

    void overwrite(List<T> entities);
}
