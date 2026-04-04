package co.edu.uptc.model;

import java.util.List;


import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.model.entity.Accounting;
import co.edu.uptc.model.entity.Person;
import co.edu.uptc.model.entity.Product;
import co.edu.uptc.model.collectionsByBehaviour.Queue;
import co.edu.uptc.model.collectionsByBehaviour.Stack;

public class BussinesManager implements ModelInterface {

    private List<Person> peopleContainer;
    private List<Product> productsContainer;
    private List<Accounting> accountingContainer;

    private Queue<Product> queueBehaviour;
    private Stack<Person> stackBehaviour;

    public BussinesManager() {
        
    }

    @Override
    public void addPerson(Person person) {

    }

    @Override
    public Person removePerson() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removePerson'");
    }

    @Override
    public List<Person> getPersons() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPersons'");
    }

    @Override
    public int createtPersonId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createtPersonId'");
    }

    @Override
    public void saveCSV() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveCSV'");
    }

    @Override
    public void addProduct(Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addProduct'");
    }

    @Override
    public Product removeProduct(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeProduct'");
    }

    @Override
    public List<Product> getProducts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProducts'");
    }

    @Override
    public int createProductId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProductId'");
    }

    @Override
    public void addAccounting(Accounting accounting) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAccounting'");
    }

    @Override
    public List<Accounting> getAccountingMovements() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccountingMovements'");
    }

    @Override
    public double getTotalBalance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotalBalance'");
    }

    @Override
    public int crerateAccountingId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crerateAccountingId'");
    }

}
