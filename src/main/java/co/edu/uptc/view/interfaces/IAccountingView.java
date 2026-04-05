package co.edu.uptc.view.interfaces;

import java.util.List;

import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.entity.Accounting;
import co.edu.uptc.presenter.interfaces.IAccountingPresenter;

public interface IAccountingView extends ViewInterface<IAccountingPresenter> {
    void showAccountingList(List<Accounting> accountings);
    void showTotalBalance(double total);
}