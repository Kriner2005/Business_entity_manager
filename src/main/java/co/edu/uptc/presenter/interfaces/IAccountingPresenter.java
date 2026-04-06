package co.edu.uptc.presenter.interfaces;

import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.view.interfaces.IAccountingView;

public interface IAccountingPresenter extends PresenterInterface<IAccountingView> {
    void addAccounting(String description, String movement, String value);
    void listAccounting();
    void nextPage();
    void prevPage();
    void exportFile();
}