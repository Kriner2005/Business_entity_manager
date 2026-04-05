package co.edu.uptc.view.panels;

import java.util.List;

import javax.swing.JPanel;

import co.edu.uptc.model.entity.Product;
import co.edu.uptc.presenter.interfaces.IProductPresenter;
import co.edu.uptc.view.interfaces.IColleague;
import co.edu.uptc.view.interfaces.IMediator;
import co.edu.uptc.view.interfaces.IProductView;

public class ProductPanel extends JPanel implements IProductView, IColleague{

    @Override
    public void setPresenter(IProductPresenter presenter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPresenter'");
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    @Override
    public void showMessage(String msg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showMessage'");
    }

    @Override
    public void showError(String msg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showError'");
    }

    @Override
    public void showAlert(String msg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showAlert'");
    }

    @Override
    public void setMediator(IMediator mediator) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setMediator'");
    }

    @Override
    public void showProductList(List<Product> products) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showProductList'");
    }

    @Override
    public void showRemovedProduct(Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showRemovedProduct'");
    }

}
