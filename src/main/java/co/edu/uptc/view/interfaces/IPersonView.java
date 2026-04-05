package co.edu.uptc.view.interfaces;

import java.util.List;

import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.entity.Person;

public interface IPersonView extends ViewInterface {
    void showPersonList(List<Person> persons);

    void showRemovedPerson(Person person);
}