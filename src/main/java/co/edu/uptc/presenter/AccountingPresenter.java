package co.edu.uptc.presenter;

import java.time.LocalDateTime;

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

    private AccountingValidator validator;

    public AccountingPresenter() {
        validator = new AccountingValidator(new NotBlankRule("Descripción"));
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
    }

    @Override
    public void listAccounting() {
        view.showAccountingList(model.getAccountingMovements());
        view.showTotalBalance(model.getTotalBalance());
    }

    @Override
    public void exportFile() {
        
    }

    private double parseAmount(String raw) {
        if (raw == null || raw.isBlank())
            return -1.0;
        try {
            return Double.parseDouble(raw.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }

    private MovementType parseMovementType(String raw) {
        if (raw == null || raw.isBlank())
            return null;
        try {
            return MovementType.valueOf(raw.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
