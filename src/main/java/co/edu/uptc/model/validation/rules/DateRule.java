package co.edu.uptc.model.validation.rules;

import co.edu.uptc.model.validation.ValidationResult;
import java.time.LocalDate;

public class DateRule {

    public ValidationResult validate(LocalDate date) {
        if (date == null)
            return ValidationResult.fail("La fecha no puede ser nula");
        if (date.isAfter(LocalDate.now()))
            return ValidationResult.fail("La fecha no puede ser futura");
        if (date.isBefore(LocalDate.now().minusYears(120)))
            return ValidationResult.fail("La fecha no es válida");
        return ValidationResult.ok();
    }
}