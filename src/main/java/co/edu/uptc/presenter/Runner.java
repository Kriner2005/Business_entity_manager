package co.edu.uptc.presenter;

import co.edu.uptc.config.AppConfig;
import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.model.BussinesManager;
import co.edu.uptc.model.DoubleLinkedList;
import co.edu.uptc.model.collectionsByBehaviour.Queue;
import co.edu.uptc.view.MainFrame;
import co.edu.uptc.view.interfaces.IAppView;

public class Runner {

    private ModelInterface model;
    private IAppView appView;
    private MainPresenter mainPresenter;

    private void buildModel() {
        AppConfig config = AppConfig.getInstance();

        model = new BussinesManager(
                new DoubleLinkedList<>(), new Queue<>(),
                new DoubleLinkedList<>(), new Queue<>(),
                config.getPersonStorage(),
                config.getProductStorage(),
                config.getAccountingStorage());
    }

    private void buildPresenter() {
        mainPresenter = new MainPresenter();
        mainPresenter.setModel(model);
    }

    private void buildView() {
        appView = new MainFrame();
    }

    private void wire() {
        mainPresenter.getPersonPresenter().setView(appView.getPersonView());
        appView.getPersonView().setPresenter(mainPresenter.getPersonPresenter());

        mainPresenter.getProductPresenter().setView(appView.getProductView());
        appView.getProductView().setPresenter(mainPresenter.getProductPresenter());

        mainPresenter.getAccountingPresenter().setView(appView.getAccountingView());
        appView.getAccountingView().setPresenter(mainPresenter.getAccountingPresenter());
    }

    public void run() {
        buildModel();
        buildPresenter();
        buildView();
        wire();
        appView.launch();
    }
}