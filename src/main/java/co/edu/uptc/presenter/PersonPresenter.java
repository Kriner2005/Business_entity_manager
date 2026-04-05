package co.edu.uptc.presenter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.model.entity.Person;
import co.edu.uptc.model.validation.PersonValidator;
import co.edu.uptc.model.validation.ValidationResult;
import co.edu.uptc.model.validation.rules.DateRule;
import co.edu.uptc.model.validation.rules.NameLengthRule;
import co.edu.uptc.presenter.interfaces.IPersonPresenter;
import co.edu.uptc.view.interfaces.IPersonView;

public class PersonPresenter implements IPersonPresenter {
    private IPersonView view;
    private ModelInterface model;

    private final PersonValidator validator;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PersonPresenter() {
        this.validator = new PersonValidator(new NameLengthRule("Nombre", 2, 10), new NameLengthRule("Apellido", 2, 10),
                new DateRule());
    }

    @Override
    public void setView(IPersonView view) {
        this.view = view;
    }

    @Override
    public void setModel(ModelInterface model) {
        this.model = model;
    }

    @Override
    public void addPerson(String name, String lastName, String gender, String birthDate) {
        char g = parseGender(gender);
        if (g == 0) {
            view.showError("Género invalido. Use Masculino o Femenino");
            return;
        }

        LocalDate date = parseDate(birthDate);
        if (date == null) {
            view.showError("FechaInvalida. FOrmato esperado: yyyy-MM-dd");
            return;
        }

        Person person = new Person(model.createtPersonId(), name.trim(), lastName.trim(), g, date);

        ValidationResult result = validator.validate(person);

        if (!result.isValid()) {
            view.showError(result.getMessage());
            return;
        }

        model.addPerson(person);
        view.showMessage("Persona agergada corectamente");
    }

    @Override
    public void removePerson() {
        Person remmoved = model.removePerson();
        if (remmoved == null) {
            view.showError("La cola de personas esta vacia");
            return;
        }

        view.showRemovedPerson(remmoved);
        view.showMessage("Persona retirada de la cola");;
    }

    @Override
    public void listPersons() {
        view.showPersonList(model.getPersons());
    }

    @Override
    public void exportCSV() {
        model.saveCSV();
        view.showAlert("Exportación CSV");
    }

    private char parseGender(String gender) {
        if (gender == null)
            return 0;
        return switch (gender.trim().toUpperCase()) {
            case "MASCULINO", "M" -> 'M';
            case "FEMENINO", "F" -> 'F';
            default -> 0;
        };
    }

    private LocalDate parseDate(String raw) {
        if (raw == null || raw.isBlank())
            return null;
        try {
            return LocalDate.parse(raw.trim(), dateFormatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
