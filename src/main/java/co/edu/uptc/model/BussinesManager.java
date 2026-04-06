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

        // ── Carga inicial ────────────────────────────────────────────────
        // Lee cada archivo y mete los registros en el contenedor
        // correspondiente usando el comportamiento correcto (Queue / Stack).
        // Así, al arrancar la app, los datos ya existen en memoria.

        for (Person p : personStorage.loadAll()) {
            personBehaviour.add(personContainer, p);
        }

        for (Product p : productStorage.loadAll()) {
            productBehaviour.add(productContainer, p);
        }

        // accounting ya viene como List, no necesita contenedor propio
        this.accountingContainer = accountingStorage.loadAll();

        // ── Inicializar contadores de ID ─────────────────────────────────
        // Si no hacemos esto, al reiniciar la app los IDs vuelven a 1
        // y colisionan con los registros que ya estaban guardados.
        // Buscamos el ID más alto que ya existe y arrancamos desde ahí.

        personIdCounter = personBehaviour.toList(personContainer).stream()
                .mapToInt(Person::getId)
                .max()
                .orElse(0); // si el archivo estaba vacío, empieza en 0

        productIdCounter = productBehaviour.toList(productContainer).stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0);
    }

      @Override
    public void addPerson(Person person) {
        personBehaviour.add(personContainer, person);
        personStorage.append(person); // guarda inmediatamente en disco
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
        // Reescribe el archivo completo con todos los registros actuales.
        // Útil si hubo retiros (removes) que cambiaron el estado en memoria
        // y queremos que el archivo refleje exactamente lo que hay ahora.
        personStorage.overwrite(personBehaviour.toList(personContainer));
    }

       @Override
    public void addProduct(Product product) {
        productBehaviour.add(productContainer, product);
        productStorage.append(product); // guarda inmediatamente en disco
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
        // Igual que saveFilePerson: reescribe todo desde cero.
        productStorage.overwrite(productBehaviour.toList(productContainer));
    }

   @Override
    public void addAccounting(Accounting accounting) {
        accountingContainer.add(accounting);
        accountingStorage.append(accounting); // guarda inmediatamente en disco
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveFileAccounting'");
    }

}
