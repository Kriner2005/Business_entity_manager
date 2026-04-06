package co.edu.uptc.model.validation.rules;

import co.edu.uptc.config.MessageManager;
import co.edu.uptc.model.validation.ValidationResult;

public class PriceRule {

    private final double maxPrice;

    public PriceRule(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public ValidationResult validate(double price) {
        if (price <= 0)
            return ValidationResult.fail(MessageManager.msg("validation.price.zero"));
        if (price > maxPrice)
            return ValidationResult.fail(MessageManager.msg("validation.price.max", maxPrice));
        return ValidationResult.ok();
    }
}
