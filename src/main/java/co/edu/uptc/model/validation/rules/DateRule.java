package co.edu.uptc.model.validation.rules;

import co.edu.uptc.config.MessageManager;
import co.edu.uptc.model.validation.ValidationResult;
import java.time.LocalDate;

public class DateRule {

    public ValidationResult validate(LocalDate date) {
        if (date == null)
            return ValidationResult.fail(MessageManager.msg("validation.date.null"));
        if (date.isAfter(LocalDate.now()))
            return ValidationResult.fail(MessageManager.msg("validation.date.future"));
        if (date.isBefore(LocalDate.now().minusYears(120)))
            return ValidationResult.fail(MessageManager.msg("validation.date.invalid"));
        return ValidationResult.ok();
    }
}