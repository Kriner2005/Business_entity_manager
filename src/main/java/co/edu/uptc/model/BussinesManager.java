package co.edu.uptc.model;

import java.util.Collections;
import java.util.List;

import co.edu.uptc.enums.MovementType;
import co.edu.uptc.interfaces.IContainer;
import co.edu.uptc.interfaces.IFileStorage;
import co.edu.uptc.interfaces.IStructureCollection;
import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.model.entities.Accounting;
import co.edu.uptc.model.entities.Person;
import co.edu.uptc.model.entities.Product;

public class BussinesManager implements ModelInterface {

    private final IContainer<Person> personContainer;
    private final IStructureCollection<IContainer<Person>, Person> personBehaviour;

    private final IContainer<Product> productContainer;
    private final IStructureCollection<IContainer<Product>, Product> productBehaviour;

    private final List<Accounting> accountingContainer;
    


    private final IFileStorage<Person> personStorage;
    private final IFileStorage<Product> productStorage;
    private final IFileStorage<Accounting> accountingStorage;

    private int personIdCounter;
    private int productIdCounter;

    public BussinesManager(
            IContainer<Person> personContainer,
            IStructureCollection<IContainer<Person>, Person> personBehaviour,
            IContainer<Product> productContainer,
            IStructureCollection<IContainer<Product>, Product> productBehaviour,
            IFileStorage<Person> personStorage,
            IFileStorage<Product> productStorage,
            IFileStorage<Accounting> accountingStorage) {

        this.personContainer = personContainer;
        this.personBehaviour = personBehaviour;
        this.productContainer = productContainer;
        this.productBehaviour = productBehaviour;
        this.personStorage = personStorage;
        this.productStorage = productStorage;
        this.accountingStorage = accountingStorage;

        for (Person p : personStorage.loadAll()) {
            personBehaviour.add(personContainer, p);
        }

        for (Product p : productStorage.loadAll()) {
            productBehaviour.add(productContainer, p);
        }

        this.accountingContainer = accountingStorage.loadAll();

        personIdCounter = personBehaviour.toList(personContainer).stream()
                .mapToInt(Person::getId)
                .max()
                .orElse(0);

        productIdCounter = productBehaviour.toList(productContainer).stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0);
    }

    @Override
    public void addPerson(Person person) {
        personBehaviour.add(personContainer, person);
    }

    @Override
    public Person removePerson() {
        return personBehaviour.remove(personContainer);
    }

    @Override
    public List<Person> getPersons() {
        return personBehaviour.toList(personContainer);
    }

    @Override
    public int createtPersonId() {
        return ++personIdCounter;
    }

    @Override
    public void saveFilePerson() {
        personStorage.overwrite(personBehaviour.toList(personContainer));
    }

    @Override
    public void addProduct(Product product) {
        productBehaviour.add(productContainer, product);
    }

    @Override
    public Product removeProduct() {
        return productBehaviour.remove(productContainer);
    }

    @Override
    public List<Product> getProducts() {
        return productBehaviour.toList(productContainer);
    }

    @Override
    public int createProductId() {
        return ++productIdCounter;
    }

    @Override
    public void saveFileProduct() {
        productStorage.overwrite(productBehaviour.toList(productContainer));
    }

    @Override
    public void addAccounting(Accounting accounting) {

        accountingContainer.add(accounting);
        accountingStorage.append(accounting);
    }

    @Override
    public List<Accounting> getAccountingMovements() {
        return Collections.unmodifiableList(accountingContainer);
    }

    @Override
    public double getTotalBalance() {
        return accountingContainer.stream()
                .mapToDouble(a -> a.getType() == MovementType.INGRESO
                        ? a.getAmount()
                        : -a.getAmount())
                .sum();
    }

    @Override
    public void saveFileAccounting() {
    }
}