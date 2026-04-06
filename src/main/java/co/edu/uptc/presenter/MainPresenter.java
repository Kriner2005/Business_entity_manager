package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;

public class MainPresenter implements PresenterInterface<ViewInterface<?>> {
    private ModelInterface model;

    private final PersonPresenter personPresenter = new PersonPresenter();
    private final ProductPresenter productPresenter = new ProductPresenter();
    private final AccountingPresenter accountingPresenter = new AccountingPresenter();

    public MainPresenter() {
    }

    @Override
    public void setModel(ModelInterface model) {
        this.model = model;

        personPresenter.setModel(model);
        productPresenter.setModel(model);
        accountingPresenter.setModel(model);
    }

    @Override
    public void setView(ViewInterface<?> view) {
    }

    public PersonPresenter getPersonPresenter() {
        return personPresenter;
    }

    public ProductPresenter getProductPresenter() {
        return productPresenter;
    }

    public AccountingPresenter getAccountingPresenter() {
        return accountingPresenter;
    }
}
