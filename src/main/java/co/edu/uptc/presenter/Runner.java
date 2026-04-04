package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;

public class Runner {
    PresenterInterface presenter;
    ModelInterface model;
    ViewInterface view;

    private void makwMVP() {
        presenter = new MainPresenter();
        model = null;
        view = null;

        presenter.setModel(model);
        presenter.setView(view);

        view.setPresenter(presenter);
    }

    public void run() {
        makwMVP();
        view.start();
    }
}
