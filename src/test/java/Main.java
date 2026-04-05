
import co.edu.uptc.enums.MovementType;
import co.edu.uptc.interfaces.*;
import co.edu.uptc.model.BussinesManager;
import co.edu.uptc.model.DoubleLinkedList;
import co.edu.uptc.model.collectionsByBehaviour.Queue;
import co.edu.uptc.model.collectionsByBehaviour.Stack;
import co.edu.uptc.model.entity.*;
import co.edu.uptc.model.persistence.FileStorageService;
import co.edu.uptc.model.persistence.serializer.JsonLSerializer;
import co.edu.uptc.model.validation.*;
import co.edu.uptc.model.validation.rules.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    // ── colores ANSI para consola ──────────────────────────────────────────
    private static final String GREEN = "\033[0;32m";
    private static final String RED = "\033[0;31m";
    private static final String YELLOW = "\033[0;33m";
    private static final String CYAN = "\033[0;36m";
    private static final String RESET = "\033[0m";

    public static void main(String[] args) {
        testDoubleLinkedList();
        testQueueBehaviour();
        testStackBehaviour();
        testPersonValidator();
        testProductValidator();
        testAccountingValidator();
        testBussinesManagerPersons();
        testBussinesManagerProducts();
        testAccountingPersistence();
    }

    // ── DoubleLinkedList ───────────────────────────────────────────────────

    private static void testDoubleLinkedList() {
        section("DoubleLinkedList");

        IContainer<String> list = new DoubleLinkedList<>();

        check("isEmpty al inicio", list.isEmpty());
        list.addLast("B");
        list.addFirst("A");
        list.addLast("C");
        check("size == 3", list.size() == 3);
        check("getFirst == A", "A".equals(list.getFirst()));
        check("getLast  == C", "C".equals(list.getLast()));
        check("get(1)   == B", "B".equals(list.get(1)));

        String removed = list.removeFirst();
        check("removeFirst retorna A", "A".equals(removed));
        check("size == 2 tras removeFirst", list.size() == 2);

        String removedLast = list.removeLast();
        check("removeLast retorna C", "C".equals(removedLast));
        check("size == 1 tras removeLast", list.size() == 1);

        List<String> asLst = list.toList();
        check("toList contiene B", asLst.contains("B") && asLst.size() == 1);
    }

    // ── Queue (FIFO) ───────────────────────────────────────────────────────

    private static void testQueueBehaviour() {
        section("Queue — comportamiento FIFO");

        IContainer<String> container = new DoubleLinkedList<>();
        IStructureCollection<IContainer<String>, String> queue = new Queue<>();

        queue.add(container, "Primero");
        queue.add(container, "Segundo");
        queue.add(container, "Tercero");

        check("size == 3", queue.size(container) == 3);
        check("remove retorna Primero (FIFO)", "Primero".equals(queue.remove(container)));
        check("remove retorna Segundo (FIFO)", "Segundo".equals(queue.remove(container)));
        check("remove retorna Tercero (FIFO)", "Tercero".equals(queue.remove(container)));
        check("isEmpty tras vaciar", queue.isEmpty(container));
    }

    // ── Stack (LIFO) ───────────────────────────────────────────────────────

    private static void testStackBehaviour() {
        section("Stack — comportamiento LIFO");

        IContainer<String> container = new DoubleLinkedList<>();
        IStructureCollection<IContainer<String>, String> stack = new Stack<>();

        stack.add(container, "Primero");
        stack.add(container, "Segundo");
        stack.add(container, "Tercero");

        check("size == 3", stack.size(container) == 3);
        check("remove retorna Tercero (LIFO)", "Tercero".equals(stack.remove(container)));
        check("remove retorna Segundo (LIFO)", "Segundo".equals(stack.remove(container)));
        check("remove retorna Primero (LIFO)", "Primero".equals(stack.remove(container)));
        check("isEmpty tras vaciar", stack.isEmpty(container));
    }

    // ── PersonValidator ────────────────────────────────────────────────────

    private static void testPersonValidator() {
        section("PersonValidator");

        IValidator<Person> validator = new PersonValidator(
                new NameLengthRule("Nombres", 2, 30),
                new NameLengthRule("Apellidos", 2, 30),
                new DateRule());

        Person valid = new Person(1, "Carlos", "Ramirez", 'M', LocalDate.of(1995, 5, 10));
        check("carlos persona válida", validator.validate(valid).isValid());

        Person blankName = new Person(2, "", "Ramirez", 'M', LocalDate.of(1995, 5, 10));
        check("nombre vacío → inválido", !validator.validate(blankName).isValid());
        info(validator.validate(blankName).getMessage());

        Person shortName = new Person(3, "A", "Ramirez", 'M', LocalDate.of(1995, 5, 10));
        check("nombre muy corto → inválido", !validator.validate(shortName).isValid());
        info(validator.validate(shortName).getMessage());

        Person futureBirth = new Person(4, "Ana", "Lopez", 'F', LocalDate.now().plusDays(1));
        check("fecha futura → inválido", !validator.validate(futureBirth).isValid());
        info(validator.validate(futureBirth).getMessage());

        Person badGender = new Person(5, "Luis", "Perez", 'X', LocalDate.of(2000, 1, 1));
        check("género X → inválido", !validator.validate(badGender).isValid());
        info(validator.validate(badGender).getMessage());
    }

    // ── ProductValidator ───────────────────────────────────────────────────

    private static void testProductValidator() {
        section("ProductValidator");

        IValidator<Product> validator = new ProductValidator(
                new NotBlankRule("Descripción"),
                new PriceRule(999_999.0));

        Product valid = new Product(1, "ARROZ DIANA", "KILO", 3500.0);
        check("producto válido", validator.validate(valid).isValid());

        Product lowercase = new Product(2, "arroz diana", "KILO", 3500.0);
        check("descripción minúsculas → inválido", !validator.validate(lowercase).isValid());
        info(validator.validate(lowercase).getMessage());

        Product negPrice = new Product(3, "LECHE", "LITRO", -100.0);
        check("precio negativo → inválido", !validator.validate(negPrice).isValid());
        info(validator.validate(negPrice).getMessage());

        Product overPrice = new Product(4, "ORO", "GRAMO", 1_000_000.0);
        check("precio sobre máximo → inválido", !validator.validate(overPrice).isValid());
        info(validator.validate(overPrice).getMessage());

        Product blankDesc = new Product(5, "   ", "KILO", 100.0);
        check("descripción en blanco → inválido", !validator.validate(blankDesc).isValid());
        info(validator.validate(blankDesc).getMessage());
    }

    // ── AccountingValidator ────────────────────────────────────────────────

    private static void testAccountingValidator() {
        section("AccountingValidator");

        IValidator<Accounting> validator = new AccountingValidator(
                new NotBlankRule("Descripción"));

        Accounting valid = new Accounting("Venta", MovementType.INGRESO, 50_000.0, LocalDateTime.now());
        check("contabilidad válida", validator.validate(valid).isValid());

        Accounting blankDesc = new Accounting("", MovementType.EGRESO, 1000.0, LocalDateTime.now());
        check("descripción vacía → inválido", !validator.validate(blankDesc).isValid());
        info(validator.validate(blankDesc).getMessage());

        Accounting zeroAmount = new Accounting("Pago", MovementType.EGRESO, 0.0, LocalDateTime.now());
        check("monto cero → inválido", !validator.validate(zeroAmount).isValid());
        info(validator.validate(zeroAmount).getMessage());

        Accounting negAmount = new Accounting("Pago", MovementType.EGRESO, -500.0, LocalDateTime.now());
        check("monto negativo → inválido", !validator.validate(negAmount).isValid());
        info(validator.validate(negAmount).getMessage());

        Accounting nullType = new Accounting("Pago", null, 500.0, LocalDateTime.now());
        check("tipo null → inválido", !validator.validate(nullType).isValid());
        info(validator.validate(nullType).getMessage());
    }

    // ── BussinesManager — Personas ─────────────────────────────────────────

    private static void testBussinesManagerPersons() {
        section("BussinesManager — Personas (Cola FIFO)");

        BussinesManager manager = buildManager();

        Person p1 = new Person(manager.createtPersonId(), "Carlos", "Ramirez", 'M', LocalDate.of(1990, 1, 1));
        Person p2 = new Person(manager.createtPersonId(), "Ana", "Lopez", 'F', LocalDate.of(1995, 3, 15));
        Person p3 = new Person(manager.createtPersonId(), "Luis", "Torres", 'M', LocalDate.of(2000, 7, 20));

        manager.addPerson(p1);
        manager.addPerson(p2);
        manager.addPerson(p3);

        check("IDs consecutivos (1,2,3)", p1.getId() == 1 && p2.getId() == 2 && p3.getId() == 3);
        check("getPersons size == 3", manager.getPersons().size() == 3);

        Person removed = manager.removePerson();
        check("removePerson retorna Carlos (FIFO)", "Carlos".equals(removed.getName()));
        check("getPersons size == 2 tras remove", manager.getPersons().size() == 2);

        Person removed2 = manager.removePerson();
        check("segundo remove retorna Ana (FIFO)", "Ana".equals(removed2.getName()));
    }

    // ── BussinesManager — Productos ────────────────────────────────────────

    private static void testBussinesManagerProducts() {
        section("BussinesManager — Productos (Pila LIFO)");

        BussinesManager manager = buildManager();

        Product pr1 = new Product(manager.createProductId(), "ARROZ", "KILO", 3500.0);
        Product pr2 = new Product(manager.createProductId(), "LECHE", "LITRO", 4200.0);
        Product pr3 = new Product(manager.createProductId(), "AZUCAR", "KILO", 2800.0);

        manager.addProduct(pr1);
        manager.addProduct(pr2);
        manager.addProduct(pr3);

        check("getProducts size == 3", manager.getProducts().size() == 3);

        Product removed = manager.removeProduct();
        check("removeProduct retorna AZUCAR (LIFO)", "AZUCAR".equals(removed.getDescription()));
        check("getProducts size == 2 tras remove", manager.getProducts().size() == 2);

        Product removed2 = manager.removeProduct();
        check("segundo remove retorna LECHE (LIFO)", "LECHE".equals(removed2.getDescription()));
    }

    // ── Accounting — persistencia JSONL ────────────────────────────────────

    private static void testAccountingPersistence() {
        section("Accounting — persistencia JSONL");

        String testFile = "data/test_accounting.txt";

        // limpia el archivo antes de cada test para resultado reproducible
        try {
            new java.io.File(testFile).delete();
        } catch (Exception ignored) {
        }

        IFileStorage<Accounting> storage = new FileStorageService<>(
                testFile, new JsonLSerializer());

        BussinesManager manager = new BussinesManager(
                new DoubleLinkedList<>(), new Queue<>(),
                new DoubleLinkedList<>(), new Stack<>(),
                storage);

        manager.addAccounting(new Accounting("Venta", MovementType.INGRESO, 100_000.0, LocalDateTime.now()));
        manager.addAccounting(new Accounting("Compra", MovementType.EGRESO, 40_000.0, LocalDateTime.now()));
        manager.addAccounting(new Accounting("Préstamo", MovementType.INGRESO, 50_000.0, LocalDateTime.now()));

        check("3 movimientos en memoria", manager.getAccountingMovements().size() == 3);
        double expected = 100_000.0 - 40_000.0 + 50_000.0;
        check("balance == 110000.0", manager.getTotalBalance() == expected);
        info("Balance calculado: " + manager.getTotalBalance());

        // nueva instancia — simula reinicio de la app
        IFileStorage<Accounting> storage2 = new FileStorageService<>(
                testFile, new JsonLSerializer());

        BussinesManager manager2 = new BussinesManager(
                new DoubleLinkedList<>(), new Queue<>(),
                new DoubleLinkedList<>(), new Stack<>(),
                storage2);

        check("carga 3 registros desde archivo", manager2.getAccountingMovements().size() == 3);
        check("balance correcto tras recarga", manager2.getTotalBalance() == expected);
        check("descripción primer registro ok",
                "Venta".equals(manager2.getAccountingMovements().get(0).getDescription()));
        check("tipo primer registro == INGRESO",
                MovementType.INGRESO == manager2.getAccountingMovements().get(0).getType());

        info("Archivo generado en: " + testFile);
    }

    // ── helpers ────────────────────────────────────────────────────────────

    private static BussinesManager buildManager() {
        IFileStorage<Accounting> storage = new FileStorageService<>(
                "data/test_temp.txt", new JsonLSerializer());
        return new BussinesManager(
                new DoubleLinkedList<>(), new Queue<>(),
                new DoubleLinkedList<>(), new Stack<>(),
                storage);
    }

    private static void section(String name) {
        System.out.println("\n" + CYAN + "══ " + name + " " + "═".repeat(Math.max(0, 50 - name.length())) + RESET);
    }

    private static void check(String label, boolean condition) {
        if (condition) {
            System.out.println(GREEN + "  nice " + RESET + label);
        } else {
            System.out.println(RED + "  bad " + RESET + label + RED + "  ← FALLA" + RESET);
        }
    }

    private static void info(String msg) {
        System.out.println(YELLOW + "    → " + msg + RESET);
    }
}