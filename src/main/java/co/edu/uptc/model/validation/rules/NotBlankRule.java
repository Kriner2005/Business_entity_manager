package co.edu.uptc.model.validation.rules;

import co.edu.uptc.config.MessageManager;
import co.edu.uptc.model.validation.ValidationResult;

public class NotBlankRule {

    private final String fieldKey;

    public NotBlankRule(String fieldName) {
        this.fieldKey = fieldName;
    }

    public ValidationResult validate(String value) {
        if (value == null || value.isBlank())
            return ValidationResult.fail(MessageManager.msg("validation." + fieldKey + ".empty"));
        return ValidationResult.ok();
    }
}