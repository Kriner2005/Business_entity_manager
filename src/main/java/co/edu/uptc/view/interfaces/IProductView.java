package co.edu.uptc.view.interfaces;

import java.util.List;

import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.entities.Product;
import co.edu.uptc.presenter.interfaces.IProductPresenter;

public interface IProductView extends ViewInterface<IProductPresenter> {
    void showProductList(List<Product> products);
    void showRemovedProduct(Product product);
}
