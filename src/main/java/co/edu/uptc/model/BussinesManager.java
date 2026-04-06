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
        // Lee los archivos y llena los contenedores al arrancar la app.

        for (Person p : personStorage.loadAll()) {
            personBehaviour.add(personContainer, p);
        }

        for (Product p : productStorage.loadAll()) {
            productBehaviour.add(productContainer, p);
        }

        this.accountingContainer = accountingStorage.loadAll();

        // ── Contadores de ID ─────────────────────────────────────────────
        // Arranca desde el ID más alto ya existente para evitar colisiones.

        personIdCounter = personBehaviour.toList(personContainer).stream()
                .mapToInt(Person::getId)
                .max()
                .orElse(0);

        productIdCounter = productBehaviour.toList(productContainer).stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0);
    }

    // ── Personas ──────────────────────────────────────────────────────────

    @Override
    public void addPerson(Person person) {
        // Solo en memoria — el archivo no se toca hasta que el usuario exporte
        personBehaviour.add(personContainer, person);
    }

    @Override
    public Person removePerson() {
        // Solo en memoria — el archivo no cambia hasta exportar
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
        // Reescribe el archivo completo con el estado actual en memoria.
        // Solo se llama cuando el usuario pulsa "Exportar CSV".
        personStorage.overwrite(personBehaviour.toList(personContainer));
    }

    // ── Productos ─────────────────────────────────────────────────────────

    @Override
    public void addProduct(Product product) {
        // Solo en memoria
        productBehaviour.add(productContainer, product);
    }

    @Override
    public Product removeProduct() {
        // Solo en memoria
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
        // Reescribe el archivo completo con el estado actual en memoria.
        // Solo se llama cuando el usuario pulsa "Exportar CSV".
        productStorage.overwrite(productBehaviour.toList(productContainer));
    }

    // ── Contabilidad ──────────────────────────────────────────────────────

    @Override
    public void addAccounting(Accounting accounting) {
        // Contabilidad sí guarda inmediatamente — es un log financiero,
        // no tiene sentido perder movimientos si la app se cierra sin exportar.
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
        // append() ya guarda cada movimiento al momento de agregarlo,
        // así que este método no necesita hacer nada adicional.
    }
}