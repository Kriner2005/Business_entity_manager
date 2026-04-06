package co.edu.uptc.config;

import javax.swing.SwingConstants;

import co.edu.uptc.interfaces.IFileStorage;
import co.edu.uptc.model.persistence.FileStorageService;
import co.edu.uptc.model.persistence.serializer.CsvSerializer;
import co.edu.uptc.model.persistence.serializer.JsonLSerializer;

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

    // === DEFAULTS ===
    private static final int    DEFAULT_PAGE_SIZE = 10;
    private static final String DEFAULT_ALIGN     = "LEFT";

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
                new CsvSerializer<>(Person.class));

        productStorage = new FileStorageService<>(
                basePath + config.get("product.file"),
                new CsvSerializer<>(Product.class));

        turnStorage = new FileStorageService<>(
                basePath + config.get("accounting.file"),
                new JsonLSerializer<>(Accounting.class));
    }

    // === GETTERS STORAGES ===

    public IFileStorage<Person> getPersonStorage() { return personStorage; }

    public IFileStorage<Product> getProductStorage() { return productStorage; }

    public IFileStorage<Accounting> getTurnStorage() { return turnStorage; }

    // === GETTER PAGINADO ===

    public int getPageSize() {
        String raw = config.get("page.size");
        if (raw == null || raw.isBlank()) return DEFAULT_PAGE_SIZE;
        try {
            int val = Integer.parseInt(raw.trim());
            return val > 0 ? val : DEFAULT_PAGE_SIZE;
        } catch (NumberFormatException e) {
            return DEFAULT_PAGE_SIZE;
        }
    }

    // === GETTER ALINEACIÓN ===

    // Devuelve la constante SwingConstants lista para usar directamente
    // en DefaultTableCellRenderer.setHorizontalAlignment()
    public int getTableAlign() {
        String raw = config.get("table.align");
        if (raw == null || raw.isBlank()) raw = DEFAULT_ALIGN;
        return switch (raw.trim().toUpperCase()) {
            case "CENTER" -> SwingConstants.CENTER;
            case "RIGHT"  -> SwingConstants.RIGHT;
            default       -> SwingConstants.LEFT;
        };
    }
}