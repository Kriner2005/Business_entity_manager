package co.edu.uptc.model.validation;

import co.edu.uptc.interfaces.IValidator;
import co.edu.uptc.model.entities.Accounting;
import co.edu.uptc.model.validation.rules.*;

public class AccountingValidator implements IValidator<Accounting> {

    private final NotBlankRule descriptionRule;

    public AccountingValidator(NotBlankRule descriptionRule) {
        this.descriptionRule = descriptionRule;
    }

    @Override
    public ValidationResult validate(Accounting accounting) {
        ValidationResult desc = descriptionRule.validate(accounting.getDescription());
        if (!desc.isValid())
            return desc;

        ValidationResult amount = validateAmount(accounting.getAmount());
        if (!amount.isValid())
            return amount;

        return validateType(accounting);
    }

    private ValidationResult validateAmount(double amount) {
        if (amount <= 0)
            return ValidationResult.fail("El valor del movimiento debe ser positivo");
        return ValidationResult.ok();
    }

    private ValidationResult validateType(Accounting accounting) {
        if (accounting.getType() == null)
            return ValidationResult.fail("El tipo de movimiento es obligatorio");
        return ValidationResult.ok();
    }
}