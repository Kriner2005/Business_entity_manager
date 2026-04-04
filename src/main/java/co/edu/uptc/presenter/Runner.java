package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.IContainer;
import co.edu.uptc.interfaces.IStructureCollection;
import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.BussinesManager;
import co.edu.uptc.model.DoubleLinkedList;
import co.edu.uptc.model.collectionsByBehaviour.Queue;
import co.edu.uptc.model.collectionsByBehaviour.Stack;
import co.edu.uptc.model.entity.Person;
import co.edu.uptc.model.entity.Product;

public class Runner {
    PresenterInterface presenter;
    ModelInterface model;
    ViewInterface view;

    private void makwMVP() {

        IContainer<Person> personList = new DoubleLinkedList<>();
        IContainer<Product> productList = new DoubleLinkedList<>();

        IStructureCollection<IContainer<Person>, Person> personQueue = new Queue<>();
        IStructureCollection<IContainer<Product>, Product> productStack = new Stack<>();

        model = new BussinesManager(personList, personQueue, productList, productStack);

        presenter = new MainPresenter();
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
