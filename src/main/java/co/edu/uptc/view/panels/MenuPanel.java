package co.edu.uptc.view.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import co.edu.uptc.view.interfaces.IColleague;
import co.edu.uptc.view.interfaces.IMediator;

public class MenuPanel extends JPanel implements IColleague {

    // ── mediador ───────────────────────────────────────────────────────────
    private IMediator mediator;

    // ── labels ─────────────────────────────────────────────────────────────
    private JLabel title;
    private JLabel personsLabel;
    private JLabel productsLabel;
    private JLabel accountingLabel;
    private JLabel exitLabel;

    // ── botones ────────────────────────────────────────────────────────────
    private JButton persons;
    private JButton products;
    private JButton accounting;
    private JButton exit;

    public MenuPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        initComponents();

        add(title, BorderLayout.NORTH);
        add(buildMenu(), BorderLayout.CENTER);
    }

    private void initComponents() {
        title = new JLabel("Sistema de Gestión - UPTC", SwingConstants.CENTER);

        personsLabel = new JLabel("Gestión de personas", SwingConstants.CENTER);
        productsLabel = new JLabel("Gestión de productos", SwingConstants.CENTER);
        accountingLabel = new JLabel("Gestión de contabilidad", SwingConstants.CENTER);
        exitLabel = new JLabel("Cerrar aplicación", SwingConstants.CENTER);

        persons = new JButton("Personas");
        products = new JButton("Productos");
        accounting = new JButton("Contabilidad");
        exit = new JButton("Salir");

        bindEvents();
    }

    private JPanel buildMenu() {
        JPanel menu = new JPanel(new GridLayout(4, 2, 12, 16));
        menu.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        menu.add(persons);
        menu.add(personsLabel);

        menu.add(products);
        menu.add(productsLabel);

        menu.add(accounting);
        menu.add(accountingLabel);

        menu.add(exit);
        menu.add(exitLabel);

        return menu;
    }

    // ── eventos ────────────────────────────────────────────────────────────

    private void bindEvents() {
        persons.addActionListener(e -> mediator.notify(this, "persons"));
        products.addActionListener(e -> mediator.notify(this, "products"));
        accounting.addActionListener(e -> mediator.notify(this, "accounting"));
        exit.addActionListener(e -> mediator.notify(this, "exit"));
    }

    // ── IColleague ─────────────────────────────────────────────────────────

    @Override
    public void setMediator(IMediator mediator) {
        this.mediator = mediator;
    }
}