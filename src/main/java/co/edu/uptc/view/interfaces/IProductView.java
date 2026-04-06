package co.edu.uptc.view.interfaces;

import java.util.List;

import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.entities.Product;
import co.edu.uptc.presenter.interfaces.IProductPresenter;

public interface IProductView extends ViewInterface<IProductPresenter> {
    // Muestra la página actual — el presenter ya recortó la sublista
    void showProductList(List<Product> products, int currentPage, int totalPages);

    void showRemovedProduct(Product product);
}
