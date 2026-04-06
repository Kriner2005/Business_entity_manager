package co.edu.uptc.view.interfaces;

import java.util.List;

import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.entities.Accounting;
import co.edu.uptc.presenter.interfaces.IAccountingPresenter;

public interface IAccountingView extends ViewInterface<IAccountingPresenter> {
    void showAccountingList(List<Accounting> accountings, int currentPage, int totalPages);
    void showTotalBalance(double total);

    void clearForm();
}