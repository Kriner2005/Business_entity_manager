package co.edu.uptc.presenter;

import java.util.List;

import co.edu.uptc.config.AppConfig;
import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.model.entities.Product;
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

    private final int pageSize;
    private int currentPage = 0;

    public ProductPresenter() {
        this.validator = new ProductValidator(
                new NotBlankRule("Descripción"),
                new PriceRule(10_000_000));
        this.pageSize = AppConfig.getInstance().getPageSize();
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

        Product product = new Product(
                model.createProductId(),
                description.trim(),
                unit.trim(),
                parsedPrice);

        ValidationResult result = validator.validate(product);
        if (!result.isValid()) {
            view.showError(result.getMessage());
            return;
        }

        model.addProduct(product);
        view.showMessage("Producto agregado (sin guardar — use Exportar CSV)");
    }

    @Override
    public void removeProduct() {
        Product removed = model.removeProduct();

        if (removed == null) {
            view.showError("No hay productos en la lista");
            return;
        }

        // Si al retirar la página actual queda vacía, retrocede
        List<Product> all = model.getProducts();
        int totalPages = totalPages(all.size());
        if (currentPage >= totalPages && currentPage > 0) {
            currentPage--;
        }

        view.showRemovedProduct(removed);
        view.showMessage("Producto retirado (sin guardar — use Exportar CSV)");
    }

    @Override
    public void listProducts() {
        currentPage = 0;
        showCurrentPage();
    }

    @Override
    public void nextPage() {
        List<Product> all = model.getProducts();
        if ((currentPage + 1) * pageSize < all.size()) {
            currentPage++;
        }
        showCurrentPage();
    }

    @Override
    public void prevPage() {
        if (currentPage > 0) {
            currentPage--;
        }
        showCurrentPage();
    }

    @Override
    public void exportCSV() {
        model.saveFileProduct();
        view.showMessage("CSV exportado correctamente");
    }

    // ── helpers ───────────────────────────────────────────────────────────

    private void showCurrentPage() {
        List<Product> all = model.getProducts();
        int from  = currentPage * pageSize;
        int to    = Math.min(from + pageSize, all.size());
        List<Product> page = all.subList(from, to);

        int totalPages = totalPages(all.size());

        view.showProductList(page, currentPage + 1, totalPages);
    }

    private int totalPages(int totalElements) {
        if (totalElements == 0) return 1;
        return (int) Math.ceil((double) totalElements / pageSize);
    }

    private double parsePrice(String raw) {
        if (raw == null || raw.isBlank()) return -1.0;
        try {
            return Double.parseDouble(raw.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }
}