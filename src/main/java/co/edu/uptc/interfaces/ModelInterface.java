package co.edu.uptc.interfaces;

import java.util.List;

import co.edu.uptc.model.entity.Accounting;
import co.edu.uptc.model.entity.Person;
import co.edu.uptc.model.entity.Product;

public interface ModelInterface {
    void addPerson(Person person);

    Person removePerson();
    List<Person> getPersons();
    int createtPersonId();
    void saveCSV();

    void addProduct(Product product);
    Product removeProduct(int id);
    List<Product> getProducts();
    int createProductId();
    
    void addAccounting(Accounting accounting);
    List<Accounting> getAccountingMovements();
    double getTotalBalance();
    int crerateAccountingId();
}
