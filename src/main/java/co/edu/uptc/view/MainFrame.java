package co.edu.uptc.view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import co.edu.uptc.view.mediator.AppMediator;
import co.edu.uptc.view.panels.AccountingPanel;
import co.edu.uptc.view.panels.MenuPanel;
import co.edu.uptc.view.panels.PersonPanel;
import co.edu.uptc.view.panels.ProductPanel;

public class MainFrame extends JFrame {

    // ── layout ─────────────────────────────────────────────────────────────
    // CardLayout permite tener varios paneles apilados y mostrar uno a la vez
    // Es el mecanismo de "navegación" entre pantallas en Swing
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel     container  = new JPanel(cardLayout);

    // ── paneles ────────────────────────────────────────────────────────────
    private final MenuPanel       menuPanel       = new MenuPanel();
    private final PersonPanel     personPanel     = new PersonPanel();
    private final ProductPanel    productPanel    = new ProductPanel();
    private final AccountingPanel accountingPanel = new AccountingPanel();

    // ── mediador ───────────────────────────────────────────────────────────
    private final AppMediator mediator;

    public MainFrame() {
        mediator = new AppMediator(this, menuPanel, personPanel, productPanel, accountingPanel);

        initFrame();
        registerPanels();
        connectColleagues();

        showPanel("menu"); // pantalla inicial
    }

    private void initFrame() {
        setTitle("Sistema de Gestión - UPTC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null); // centrado en pantalla
        setContentPane(container);
    }

    // Registra cada panel en el CardLayout con un nombre clave
    // Ese nombre es el que usa AppMediator para navegar
    private void registerPanels() {
        container.add(menuPanel,       "menu");
        container.add(personPanel,     "persons");
        container.add(productPanel,    "products");
        container.add(accountingPanel, "accounting");
    }

    // Le dice a cada panel quién es su mediador
    // Esto activa la relación Colleague ↔ Mediator
    private void connectColleagues() {
        menuPanel.setMediator(mediator);
        personPanel.setMediator(mediator);
        productPanel.setMediator(mediator);
        accountingPanel.setMediator(mediator);
    }

    // El mediador llama este método para cambiar de panel
    public void showPanel(String name) {
        cardLayout.show(container, name);
    }
}