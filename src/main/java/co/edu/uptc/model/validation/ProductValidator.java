package co.edu.uptc.model.validation;

import co.edu.uptc.interfaces.IValidator;
import co.edu.uptc.model.entity.Product;
import co.edu.uptc.model.validation.rules.*;

public class ProductValidator implements IValidator<Product> {

    private final NotBlankRule descriptionRule;
    private final PriceRule priceRule;

    public ProductValidator(NotBlankRule descriptionRule, PriceRule priceRule) {
        this.descriptionRule = descriptionRule;
        this.priceRule = priceRule;
    }

    @Override
    public ValidationResult validate(Product product) {
        ValidationResult desc = descriptionRule.validate(product.getDescription());
        if (!desc.isValid())
            return desc;

        ValidationResult price = priceRule.validate(product.getPrice());
        if (!price.isValid())
            return price;

        return validateDescription(product.getDescription());
    }

    private ValidationResult validateDescription(String description) {
        if (!description.equals(description.toUpperCase()))
            return ValidationResult.fail("La descripción debe estar en mayúsculas");
        return ValidationResult.ok();
    }
}