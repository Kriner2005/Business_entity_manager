package co.edu.uptc.view.interfaces;

import java.util.List;

import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.entities.Person;
import co.edu.uptc.presenter.interfaces.IPersonPresenter;

public interface IPersonView extends ViewInterface<IPersonPresenter> {
    // Muestra la página actual — el presenter ya recortó la sublista
    void showPersonList(List<Person> persons, int currentPage, int totalPages);

    void showRemovedPerson(Person person);

    void clearForm();
}