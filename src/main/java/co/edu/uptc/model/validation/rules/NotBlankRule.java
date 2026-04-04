package co.edu.uptc.model.validation.rules;

import co.edu.uptc.model.validation.ValidationResult;

public class NotBlankRule {

    private final String fieldName;

    public NotBlankRule(String fieldName) {
        this.fieldName = fieldName;
    }

    public ValidationResult validate(String value) {
        if (value == null || value.isBlank())
            return ValidationResult.fail(fieldName + " no puede estar vacío");
        return ValidationResult.ok();
    }
}