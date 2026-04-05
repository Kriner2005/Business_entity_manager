package co.edu.uptc.presenter.interfaces;

import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.view.interfaces.IPersonView;

public interface IPersonPresenter extends PresenterInterface<IPersonView> {
    void addPerson(String name, String lastName, String gender, String birthDate);

    void removePerson();

    void listPersons();

    void exportCSV();
}
