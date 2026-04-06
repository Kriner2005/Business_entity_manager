package co.edu.uptc.config;

import javax.swing.SwingConstants;

import co.edu.uptc.interfaces.IFileStorage;
import co.edu.uptc.model.persistence.FileStorageService;
import co.edu.uptc.model.persistence.serializer.CsvSerializer;
import co.edu.uptc.model.persistence.serializer.JsonLSerializer;
import co.edu.uptc.model.entities.Person;
import co.edu.uptc.model.entities.Product;
import co.edu.uptc.model.entities.Accounting;

/**
 * AppConfig — Singleton que centraliza el acceso a la configuración.
 *
 * Responsabilidades:
 * - Leer claves del ConfigLoader (que ya maneja interno/externo).
 * - Convertir los valores a los tipos correctos (int, String, etc.).
 * - Construir los storages listos para inyectar en BussinesManager.
 *
 * LO QUE NO HACE:
 * - Inventar valores por defecto — eso es responsabilidad del
 * config.properties interno. Si una clave falta, falla con
 * un mensaje claro que indica exactamente qué está mal.
 */
public class AppConfig {

    private static AppConfig instance;

    private final ConfigLoader config;

    // ── Storages ───────────────────────────────────────────────────────────
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

    // ── Inicialización de storages ─────────────────────────────────────────

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

    // ── Getters de storages ────────────────────────────────────────────────

    public IFileStorage<Person> getPersonStorage() {
        return personStorage;
    }

    public IFileStorage<Product> getProductStorage() {
        return productStorage;
    }

    public IFileStorage<Accounting> getAccountingStorage() {
        return accountingStorage;
    }

    // ── Paginado ───────────────────────────────────────────────────────────

    public int getPageSize() {
        return parseInt("page.size");
    }

    // ── Alineación de tabla ────────────────────────────────────────────────

    public int getTableAlign() {
        return switch (requireUpper("table.align")) {
            case "CENTER" -> SwingConstants.CENTER;
            case "RIGHT" -> SwingConstants.RIGHT;
            case "LEFT" -> SwingConstants.LEFT;
            default -> throw new IllegalStateException(
                    "Valor inválido para 'table.align'. Valores válidos: LEFT, CENTER, RIGHT");
        };
    }

    // ── Persona ────────────────────────────────────────────────────────────

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

    // ── Producto ───────────────────────────────────────────────────────────

    /**
     * Estilo de la descripción del producto.
     * Valores válidos definidos en config.properties: UPPERCASE, TITLECASE
     */
    public String getProductDescriptionStyle() {
        String style = requireUpper("product.description.style");
        if (!style.equals("UPPERCASE") && !style.equals("TITLECASE")) {
            throw new IllegalStateException(
                    "Valor inválido para 'product.description.style'. Valores válidos: UPPERCASE, TITLECASE");
        }
        return style;
    }

    // ── Helpers de lectura ─────────────────────────────────────────────────

    /**
     * Lee una clave como String.
     * Lanza excepción si la clave no existe o está vacía.
     * El mensaje indica exactamente qué clave falta para facilitar el debug.
     */
    private String require(String key) {
        String value = config.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Clave faltante o vacía en config.properties: '" + key + "'");
        }
        return value.trim();
    }

    /**
     * Lee una clave como String y la convierte a mayúsculas.
     * Útil para claves de tipo enum (LEFT, CENTER, UPPERCASE, etc.)
     * donde la comparación no debe depender de si el usuario escribió
     * "left", "LEFT" o "Left" en el archivo de config.
     */
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