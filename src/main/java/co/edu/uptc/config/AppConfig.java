package co.edu.uptc.config;

import co.edu.uptc.interfaces.IFileStorage;
import co.edu.uptc.model.persistence.FileStorageService;
import co.edu.uptc.model.persistence.serializer.CsvSerializer;
import co.edu.uptc.model.persistence.serializer.JsonLSerializer;

// IMPORTA TUS ENTIDADES
import co.edu.uptc.model.entities.Person;
import co.edu.uptc.model.entities.Product;
import co.edu.uptc.model.entities.Accounting;

public class AppConfig {

    private static AppConfig instance;

    private final ConfigLoader config;

    // === STORAGES ===
    private IFileStorage<Person> personStorage;
    private IFileStorage<Product> productStorage;
    private IFileStorage<Accounting> turnStorage;

    private AppConfig() {
        this.config = new ConfigLoader();
        initStorage();
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    private void initStorage() {

        String basePath = config.get("data.path");

        personStorage = new FileStorageService<>(
                basePath + config.get("person.file"),
                new CsvSerializer<>(Person.class)
        );

        productStorage = new FileStorageService<>(
                basePath + config.get("product.file"),
                new CsvSerializer<>(Product.class)
        );

        turnStorage = new FileStorageService<>(
                basePath + config.get("accounting.file"),
                new JsonLSerializer<>(Accounting.class)
        );
    }

    // === GETTERS ===
    public IFileStorage<Person> getPersonStorage() {
        return personStorage;
    }

    public IFileStorage<Product> getProductStorage() {
        return productStorage;
    }

    public IFileStorage<Accounting> getTurnStorage() {
        return turnStorage;
    }
}