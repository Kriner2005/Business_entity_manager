// rules/NameLengthRule.java
package co.edu.uptc.model.validation.rules;

import co.edu.uptc.model.validation.ValidationResult;

public class NameLengthRule {

    private final int min;
    private final int max;
    private final String fieldName;

    public NameLengthRule(String fieldName, int min, int max) {
        this.fieldName = fieldName;
        this.min = min;
        this.max = max;
    }

    public ValidationResult validate(String value) {
        if (value == null || value.isBlank())
            return ValidationResult.fail(fieldName + " no puede estar vacío");
        if (value.length() < min)
            return ValidationResult.fail(fieldName + " mínimo " + min + " caracteres");
        if (value.length() > max)
            return ValidationResult.fail(fieldName + " máximo " + max + " caracteres");
        return ValidationResult.ok();
    }
}