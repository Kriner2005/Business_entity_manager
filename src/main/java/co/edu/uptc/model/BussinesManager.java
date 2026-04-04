package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.interfaces.IContainer;
import co.edu.uptc.interfaces.IStructureCollection;
import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.model.entity.Accounting;
import co.edu.uptc.model.entity.Person;
import co.edu.uptc.model.entity.Product;

public class BussinesManager implements ModelInterface {

    private IContainer<Person> personContainer;
    private IStructureCollection<IContainer<Person>, Person> personBehaviour;

    private IContainer<Product> productContainer;
    private IStructureCollection<IContainer<Product>, Product> productBehaviour;

    private List<Accounting> accountingContainer;

    public BussinesManager(
            IContainer<Person> personContainer,
            IStructureCollection<IContainer<Person>, Person> personBehaviour,
            IContainer<Product> productContainer,
            IStructureCollection<IContainer<Product>, Product> productBehaviour) {
        this.personContainer = personContainer;
        this.personBehaviour = personBehaviour;
        this.productContainer = productContainer;
        this.productBehaviour = productBehaviour;
        this.accountingContainer = new ArrayList<>();
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createtPersonId'");
    }

    @Override
    public void saveCSV() {

    }

    @Override
    public void addProduct(Product product) {
        productBehaviour.add(productContainer, product);
    }

    @Override
    public Product removeProduct(int id) {
        return productBehaviour.remove(productContainer);
    }

    @Override
    public List<Product> getProducts() {
        return productBehaviour.toList(productContainer);
    }

    @Override
    public int createProductId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProductId'");
    }

    @Override
    public void addAccounting(Accounting accounting) {
        accountingContainer.add(accounting);
    }

    @Override
    public List<Accounting> getAccountingMovements() {
        return accountingContainer;
    }

    @Override
    public double getTotalBalance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotalBalance'");
    }

}
