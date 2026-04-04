package co.edu.uptc.interfaces;

public interface ISerializer<T> {
    String serialize(T entity);

    T deserialize(String line);
}