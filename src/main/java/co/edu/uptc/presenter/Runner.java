package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.IContainer;
import co.edu.uptc.interfaces.IFileStorage;
import co.edu.uptc.interfaces.IStructureCollection;
import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.BussinesManager;
import co.edu.uptc.model.DoubleLinkedList;
import co.edu.uptc.model.collectionsByBehaviour.Queue;
import co.edu.uptc.model.collectionsByBehaviour.Stack;
import co.edu.uptc.model.entity.Accounting;
import co.edu.uptc.model.entity.Person;
import co.edu.uptc.model.entity.Product;
import co.edu.uptc.model.persistence.FileStorageService;
import co.edu.uptc.model.persistence.serializer.AccountingJsonLSerializer;

public class Runner {

    ModelInterface model;
    ViewInterface view;

    public void makeMVP() {

        // 1. Model
        IFileStorage<Accounting> accountingStorage = new FileStorageService<>(
                "data/accounting.txt", new AccountingJsonLSerializer());

        model = new BussinesManager(
                new DoubleLinkedList<>(), new Queue<>(),
                new DoubleLinkedList<>(), new Stack<>(),
                accountingStorage);

        // 2. MainPresenter — recibe el model y lo distribuye internamente
        MainPresenter mainPresenter = new MainPresenter();
        mainPresenter.setModel(model);

        // 3. Conectar cada panel con su subpresenter
        // MainPresenter ya les dio el model cuando llamamos setModel()
        frame.getPersonPanel().setPresenter(mainPresenter.getPersonPresenter());
        frame.getProductPanel().setPresenter(mainPresenter.getProductPresenter());
        frame.getAccountingPanel().setPresenter(mainPresenter.getAccountingPresenter());
    }

    public void run() {
        makwMVP();
        view.start();
    }
}
