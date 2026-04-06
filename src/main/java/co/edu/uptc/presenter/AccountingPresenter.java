package co.edu.uptc.presenter;

import java.time.LocalDateTime;
import java.util.List;

import co.edu.uptc.config.AppConfig;
import co.edu.uptc.enums.MovementType;
import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.model.entities.Accounting;
import co.edu.uptc.model.validation.AccountingValidator;
import co.edu.uptc.model.validation.ValidationResult;
import co.edu.uptc.model.validation.rules.NotBlankRule;
import co.edu.uptc.presenter.interfaces.IAccountingPresenter;
import co.edu.uptc.view.interfaces.IAccountingView;

public class AccountingPresenter implements IAccountingPresenter {

    private ModelInterface model;
    private IAccountingView view;

    private final AccountingValidator validator;

    private final int pageSize;
    private int currentPage = 0;

    public AccountingPresenter() {
        validator = new AccountingValidator(new NotBlankRule("Descripción"));
        this.pageSize = AppConfig.getInstance().getPageSize();
    }

    @Override
    public void setView(IAccountingView view) {
        this.view = view;
    }

    @Override
    public void setModel(ModelInterface model) {
        this.model = model;
    }

    @Override
    public void addAccounting(String description, String movementType, String value) {
        MovementType type = parseMovementType(movementType);
        if (type == null) {
            view.showError("Tipo de movimiento inválido.");
            return;
        }

        double parsedValue = parseAmount(value);
        if (parsedValue < 0) {
            view.showError("Valor inválido. Ingrese un número mayor a cero.");
            return;
        }

        Accounting accounting = new Accounting(
                description.trim(),
                type,
                parsedValue,
                LocalDateTime.now());

        ValidationResult result = validator.validate(accounting);
        if (!result.isValid()) {
            view.showError(result.getMessage());
            return;
        }

        model.addAccounting(accounting);
        view.showMessage("Movimiento registrado correctamente.");
        view.showTotalBalance(model.getTotalBalance());
        view.clearForm();  
    }

    @Override
    public void listAccounting() {
        currentPage = 0; // al listar siempre empieza desde la primera página
        showCurrentPage();
    }

    @Override
    public void nextPage() {
        List<Accounting> all = model.getAccountingMovements();
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
    public void exportFile() {
        // accounting guarda automáticamente con append — no hay nada que exportar
    }

    // ── helpers ───────────────────────────────────────────────────────────

    private void showCurrentPage() {
        List<Accounting> all = model.getAccountingMovements();
        int from = currentPage * pageSize;
        int to   = Math.min(from + pageSize, all.size());
        List<Accounting> page = all.subList(from, to);

        int totalPages = totalPages(all.size());
        view.showAccountingList(page, currentPage + 1, totalPages);
        view.showTotalBalance(model.getTotalBalance());
    }

    private int totalPages(int totalElements) {
        if (totalElements == 0) return 1;
        return (int) Math.ceil((double) totalElements / pageSize);
    }

    private double parseAmount(String raw) {
        if (raw == null || raw.isBlank()) return -1.0;
        try {
            return Double.parseDouble(raw.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }

    private MovementType parseMovementType(String raw) {
        if (raw == null || raw.isBlank()) return null;
        try {
            return MovementType.valueOf(raw.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}