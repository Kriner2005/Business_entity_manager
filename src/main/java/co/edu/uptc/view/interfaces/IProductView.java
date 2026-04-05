package co.edu.uptc.view.interfaces;

import java.util.List;

import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.entity.Product;

public interface IProductView extends ViewInterface {
    void showProductList(List<Product> products);
    void showRemovedProduct(Product product);
}
