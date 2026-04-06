package co.edu.uptc.presenter.interfaces;

import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.view.interfaces.IProductView;

public interface IProductPresenter extends PresenterInterface<IProductView> {
    void addProduct(String description, String unit, String price);

    void removeProduct();

    void listProducts();

    void nextPage();

    void prevPage();

    void exportCSV();
}