package co.edu.uptc.view.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import co.edu.uptc.config.MessageManager;
import co.edu.uptc.view.interfaces.IColleague;
import co.edu.uptc.view.interfaces.IMediator;

public class MenuPanel extends JPanel implements IColleague {

    private IMediator mediator;

    private JLabel title;
    private JLabel personsLabel;
    private JLabel productsLabel;
    private JLabel accountingLabel;
    private JLabel exitLabel;

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
        title = new JLabel(MessageManager.msg("menu.main.title"), SwingConstants.CENTER);

        personsLabel = new JLabel(MessageManager.msg("menu.desc.persons"), SwingConstants.CENTER);
        productsLabel = new JLabel(MessageManager.msg("menu.desc.products"), SwingConstants.CENTER);
        accountingLabel = new JLabel(MessageManager.msg("menu.desc.accounting"), SwingConstants.CENTER);
        exitLabel = new JLabel(MessageManager.msg("menu.desc.exit"), SwingConstants.CENTER);

        persons = new JButton(MessageManager.msg("menu.persons"));
        products = new JButton(MessageManager.msg("menu.products"));
        accounting = new JButton(MessageManager.msg("menu.accounting"));
        exit = new JButton(MessageManager.msg("menu.exit"));

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

    private void bindEvents() {
        persons.addActionListener(e -> mediator.notify(this, "persons"));
        products.addActionListener(e -> mediator.notify(this, "products"));
        accounting.addActionListener(e -> mediator.notify(this, "accounting"));
        exit.addActionListener(e -> mediator.notify(this, "exit"));
    }


    @Override
    public void setMediator(IMediator mediator) {
        this.mediator = mediator;
    }
}