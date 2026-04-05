package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.presenter.interfaces.IAccountingPresenter;
import co.edu.uptc.view.interfaces.IAccountingView;

public class AccountingPresenter implements IAccountingPresenter{

    @Override
    public void setView(IAccountingView view) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setView'");
    }

    @Override
    public void setModel(ModelInterface model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setModel'");
    }

    @Override
    public void addAccounting(String description, String movement, String value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAccounting'");
    }

    @Override
    public void listAccounting() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listAccounting'");
    }

    @Override
    public void exportFile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exportFile'");
    }

}
