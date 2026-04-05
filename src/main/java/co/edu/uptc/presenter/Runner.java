package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.IFileStorage;
import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.model.BussinesManager;
import co.edu.uptc.model.DoubleLinkedList;
import co.edu.uptc.model.collectionsByBehaviour.Queue;
import co.edu.uptc.model.collectionsByBehaviour.Stack;
import co.edu.uptc.model.entity.Accounting;
import co.edu.uptc.model.persistence.FileStorageService;
import co.edu.uptc.model.persistence.serializer.AccountingJsonLSerializer;
import co.edu.uptc.view.MainFrame;
import co.edu.uptc.view.interfaces.IAppView;

public class Runner {

    private ModelInterface model;
    private IAppView appView;
    private MainPresenter mainPresenter;

    private void buildModel() {
        IFileStorage<Accounting> accountingStorage = new FileStorageService<>(
                "data/accounting.txt", new AccountingJsonLSerializer());

        model = new BussinesManager(
                new DoubleLinkedList<>(), new Queue<>(),
                new DoubleLinkedList<>(), new Stack<>(),
                accountingStorage);
    }

    private void buildPresenter() {
        mainPresenter = new MainPresenter();
        mainPresenter.setModel(model); // distribuye internamente a los subpresenters
    }

    private void buildView() {
        // MainFrame crea los paneles internamente
        appView = new MainFrame();
    }

    private void wire() {
        // conecta cada panel con su subpresenter
        // el panel ya tiene el mediador (lo hace MainFrame internamente)
        // aquí solo falta el presenter
        appView.getPersonView().setPresenter(mainPresenter.getPersonPresenter());
        appView.getProductView().setPresenter(mainPresenter.getProductPresenter());
        appView.getAccountingView().setPresenter(mainPresenter.getAccountingPresenter());
    }

    public void run() {
        buildModel();
        buildPresenter();
        buildView();
        wire();

        // Swing debe correr en su propio hilo — SwingUtilities.invokeLater lo garantiza

        appView.show();
    }
}