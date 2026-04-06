// rules/NameLengthRule.java
package co.edu.uptc.model.validation.rules;

import co.edu.uptc.config.MessageManager;
import co.edu.uptc.model.validation.ValidationResult;

public class NameLengthRule {

    private final int min;
    private final int max;
    private final String fieldKey;

    public NameLengthRule(String fieldName, int min, int max) {
        this.fieldKey = fieldName;
        this.min = min;
        this.max = max;
    }

public ValidationResult validate(String value) {
    if (value == null || value.isBlank())
        return ValidationResult.fail(MessageManager.msg("validation." + fieldKey + ".empty"));
    if (value.length() < min)
        return ValidationResult.fail(MessageManager.msg("validation." + fieldKey + ".min", min));
    if (value.length() > max)
        return ValidationResult.fail(MessageManager.msg("validation." + fieldKey + ".max", max));
    return ValidationResult.ok();
}
}