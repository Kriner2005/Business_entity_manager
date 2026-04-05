package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.presenter.interfaces.IProductPresenter;
import co.edu.uptc.view.interfaces.IProductView;

public class ProductPresenter implements IProductPresenter{

    @Override
    public void setView(IProductView view) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setView'");
    }

    @Override
    public void setModel(ModelInterface model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setModel'");
    }

    @Override
    public void addProduct(String description, String unit, String price) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addProduct'");
    }

    @Override
    public void removeProduct() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeProduct'");
    }

    @Override
    public void listProducts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listProducts'");
    }

    @Override
    public void exportCSV() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exportCSV'");
    }
}
