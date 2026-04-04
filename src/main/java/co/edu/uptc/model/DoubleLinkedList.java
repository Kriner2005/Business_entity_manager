package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DoubleLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        Node(T data) {
            this.data = data;
        }
    }


    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public T removeFirst() {
        if (isEmpty())
            return null;
        T data = head.data;
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return data;
    }

    public T removeLast() {
        if (isEmpty())
            return null;
        T data = tail.data;
        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return data;
    }

    // En DoubleList.java — agregar este método
public T removeById(java.util.function.Predicate<T> matcher) {
    Node<T> current = head;
    while (current != null) {
        if (matcher.test(current.data)) {
            if (current.prev != null) current.prev.next = current.next;
            else head = current.next;

            if (current.next != null) current.next.prev = current.prev;
            else tail = current.prev;

            size--;
            return current.data;
        }
        current = current.next;
    }
    return null;
}

    public T getFirst() {
        return isEmpty() ? null : head.data;
    }

    public T getLast() {
        return isEmpty() ? null : tail.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public List<T> toList() {
        List<T> result = new ArrayList<>(size);
        Node<T> current = head;
        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return Collections.unmodifiableList(result);
    }

    public List<T> toListReversed() {
        List<T> result = new ArrayList<>(size);
        Node<T> current = tail;
        while (current != null) {
            result.add(current.data);
            current = current.prev;
        }
        return Collections.unmodifiableList(result);
    }
}