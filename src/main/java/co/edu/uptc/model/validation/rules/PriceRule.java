package co.edu.uptc.model.validation.rules;

import co.edu.uptc.model.validation.ValidationResult;

public class PriceRule {

    private final double maxPrice;

    public PriceRule(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public ValidationResult validate(double price) {
        if (price <= 0)
            return ValidationResult.fail("El precio debe ser mayor a cero");
        if (price > maxPrice)
            return ValidationResult.fail("El precio no puede superar " + maxPrice);
        return ValidationResult.ok();
    }
}
