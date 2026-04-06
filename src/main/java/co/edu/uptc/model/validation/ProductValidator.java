package co.edu.uptc.model.validation;

import co.edu.uptc.interfaces.IValidator;
import co.edu.uptc.model.entities.Product;
import co.edu.uptc.model.validation.rules.NotBlankRule;
import co.edu.uptc.model.validation.rules.PriceRule;

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

        return priceRule.validate(product.getPrice());
    }
}