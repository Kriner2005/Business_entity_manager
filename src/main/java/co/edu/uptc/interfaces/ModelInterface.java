package co.edu.uptc.interfaces;

import java.util.List;

import co.edu.uptc.model.entities.Accounting;
import co.edu.uptc.model.entities.Person;
import co.edu.uptc.model.entities.Product;

public interface ModelInterface {
    void addPerson(Person person);

    Person removePerson();
    List<Person> getPersons();
    int createtPersonId();
    void saveFilePerson();

    void addProduct(Product product);
    Product removeProduct();
    List<Product> getProducts();
    int createProductId();
    void saveFileProduct();
    
    void addAccounting(Accounting accounting);
    List<Accounting> getAccountingMovements();
    double getTotalBalance();
    void saveFileAccounting();
}
