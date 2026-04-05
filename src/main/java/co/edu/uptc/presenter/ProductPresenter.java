package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.model.entity.Product;
import co.edu.uptc.model.validation.ProductValidator;
import co.edu.uptc.model.validation.ValidationResult;
import co.edu.uptc.model.validation.rules.NotBlankRule;
import co.edu.uptc.model.validation.rules.PriceRule;
import co.edu.uptc.presenter.interfaces.IProductPresenter;
import co.edu.uptc.view.interfaces.IProductView;

public class ProductPresenter implements IProductPresenter {

    private IProductView view;
    private ModelInterface model;

    private final ProductValidator validator;

    public ProductPresenter() {
        this.validator = new ProductValidator(new NotBlankRule("Descripción"), new PriceRule(10_000_000));
    }

    @Override
    public void setView(IProductView view) {
        this.view = view;
    }

    @Override
    public void setModel(ModelInterface model) {
        this.model = model;
    }

    @Override
    public void addProduct(String description, String unit, String price) {
        double parsedPrice = parsePrice(price);

        if (parsedPrice < 0) {
            view.showError("Precio inválido. Ingrese un número mayor a cero");
            return;
        }

        Product product = new Product(model.createProductId(), description, unit.trim(), parsedPrice);

        ValidationResult result = validator.validate(product);
        if (!result.isValid()) {
            view.showError(result.getMessage());
            return;
        }

        model.addProduct(product);
        view.showMessage("Producto agregado correctamente");
    }

    @Override
    public void removeProduct() {
        Product removedPropdutc = model.removeProduct();

        if (removedPropdutc == null) {
            view.showError("NO hay prodcuctos en la lista");
            return;
        }

        view.showRemovedProduct(removedPropdutc);
        view.showMessage("Producto retirado de la lista");
    }

    @Override
    public void listProducts() {
        view.showProductList(model.getProducts());
    }

    @Override
    public void exportCSV() {
        view.showAlert("Exportación CSV — próximamente.");
    }

    private double parsePrice(String raw) {
        if (raw == null || raw.isBlank())
            return -1.0;
        try {
            return Double.parseDouble(raw.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }
}
