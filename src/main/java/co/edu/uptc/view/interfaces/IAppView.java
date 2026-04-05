package co.edu.uptc.view.interfaces;

public interface IAppView {
    IPersonView getPersonView();

    IProductView getProductView();

    IAccountingView getAccountingView();

    void show();
}
