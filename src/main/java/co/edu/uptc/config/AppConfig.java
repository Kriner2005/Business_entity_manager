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

    private IFileStorage<Person> personStorage;
    private IFileStorage<Product> productStorage;
    private IFileStorage<Accounting> accountingStorage;

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
        String basePath = require("data.path");

        personStorage = new FileStorageService<>(
                basePath + require("person.file"),
                new CsvSerializer<>(Person.class));

        productStorage = new FileStorageService<>(
                basePath + require("product.file"),
                new CsvSerializer<>(Product.class));

        accountingStorage = new FileStorageService<>(
                basePath + require("accounting.file"),
                new JsonLSerializer<>(Accounting.class));
    }

    public IFileStorage<Person> getPersonStorage() {
        return personStorage;
    }

    public IFileStorage<Product> getProductStorage() {
        return productStorage;
    }

    public IFileStorage<Accounting> getAccountingStorage() {
        return accountingStorage;
    }

    public int getPageSize() {
        return parseInt("page.size");
    }

    public int getTableAlign() {
        return switch (requireUpper("table.align")) {
            case "CENTER" -> SwingConstants.CENTER;
            case "RIGHT" -> SwingConstants.RIGHT;
            case "LEFT" -> SwingConstants.LEFT;
            default -> throw new IllegalStateException(
                    "Valor inválido para 'table.align'. Valores válidos: LEFT, CENTER, RIGHT");
        };
    }

    public int getPersonNameMin() {
        return parseInt("person.name.min");
    }

    public int getPersonNameMax() {
        return parseInt("person.name.max");
    }

    public int getPersonLastNameMin() {
        return parseInt("person.lastname.min");
    }

    public int getPersonLastNameMax() {
        return parseInt("person.lastname.max");
    }

    public String getProductDescriptionStyle() {
        String style = requireUpper("product.description.style");
        if (!style.equals("UPPERCASE") && !style.equals("TITLECASE")) {
            throw new IllegalStateException(
                    "Valor inválido para 'product.description.style'. Valores válidos: UPPERCASE, TITLECASE");
        }
        return style;
    }

    private String require(String key) {
        String value = config.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Clave faltante o vacía en config.properties: '" + key + "'");
        }
        return value.trim();
    }

    private String requireUpper(String key) {
        return require(key).toUpperCase();
    }

    /**
     * Lee una clave y la convierte a int.
     * Lanza excepción si la clave no existe o no es un número válido.
     */
    private int parseInt(String key) {
        String raw = require(key);
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new IllegalStateException(
                    "Valor inválido para '" + key + "': '" + raw + "' no es un número entero");
        }
    }

    public String getAppLanguage() {
        return require("app.language");
    }
}