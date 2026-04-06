package co.edu.uptc.presenter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import co.edu.uptc.config.AppConfig;
import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.model.entities.Person;
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

    private final int pageSize;
    private int currentPage = 0;

    public PersonPresenter() {
        AppConfig config = AppConfig.getInstance();

        this.validator = new PersonValidator(
                new NameLengthRule("Nombre", config.getPersonNameMin(), config.getPersonNameMax()),
                new NameLengthRule("Apellido", config.getPersonLastNameMin(), config.getPersonLastNameMax()),
                new DateRule());

        this.pageSize = config.getPageSize();
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
            view.showError("Género inválido. Use Masculino o Femenino");
            return;
        }

        LocalDate date = parseDate(birthDate);
        if (date == null) {
            view.showError("Fecha inválida. Formato esperado: yyyy-MM-dd");
            return;
        }

        Person person = new Person(model.createtPersonId(), name.trim(), lastName.trim(), g, date);

        ValidationResult result = validator.validate(person);
        if (!result.isValid()) {
            view.showError(result.getMessage());
            return;
        }

        model.addPerson(person);
        view.showMessage("Persona agregada (sin guardar — use Exportar CSV)");
    }

    @Override
    public void removePerson() {
        Person removed = model.removePerson();
        if (removed == null) {
            view.showError("La cola de personas está vacía");
            return;
        }

        // Si al retirar la página actual queda vacía, retrocede una página
        List<Person> all = model.getPersons();
        int totalPages = totalPages(all.size());
        if (currentPage >= totalPages && currentPage > 0) {
            currentPage--;
        }

        view.showRemovedPerson(removed);
        view.showMessage("Persona retirada (sin guardar — use Exportar CSV)");
    }

    @Override
    public void listPersons() {
        currentPage = 0; // al listar siempre empieza en la primera página
        showCurrentPage();
    }

    @Override
    public void nextPage() {
        List<Person> all = model.getPersons();
        if ((currentPage + 1) * pageSize < all.size()) {
            currentPage++;
        }
        showCurrentPage();
    }

    @Override
    public void prevPage() {
        if (currentPage > 0) {
            currentPage--;
        }
        showCurrentPage();
    }

    @Override
    public void exportCSV() {
        model.saveFilePerson();
        view.showMessage("CSV exportado correctamente");
    }

    // ── helpers ───────────────────────────────────────────────────────────

    private void showCurrentPage() {
        List<Person> all = model.getPersons();
        int from = currentPage * pageSize;
        int to = Math.min(from + pageSize, all.size());
        List<Person> page = all.subList(from, to);

        int totalPages = totalPages(all.size());

        view.showPersonList(page, currentPage + 1, totalPages);
    }

    private int totalPages(int totalElements) {
        if (totalElements == 0)
            return 1;
        return (int) Math.ceil((double) totalElements / pageSize);
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