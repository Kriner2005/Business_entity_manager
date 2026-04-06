package co.edu.uptc.view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import co.edu.uptc.view.interfaces.IAccountingView;
import co.edu.uptc.view.interfaces.IAppView;
import co.edu.uptc.view.interfaces.IPersonView;
import co.edu.uptc.view.interfaces.IProductView;
import co.edu.uptc.view.mediator.AppMediator;
import co.edu.uptc.view.panels.AccountingPanel;
import co.edu.uptc.view.panels.MenuPanel;
import co.edu.uptc.view.panels.PersonPanel;
import co.edu.uptc.view.panels.ProductPanel;

public class MainFrame extends JFrame implements IAppView {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel container = new JPanel(cardLayout);

    private final MenuPanel menuPanel = new MenuPanel();
    private final PersonPanel personPanel = new PersonPanel();
    private final ProductPanel productPanel = new ProductPanel();
    private final AccountingPanel accountingPanel = new AccountingPanel();

    private final AppMediator mediator;

    public MainFrame() {
        mediator = new AppMediator(this, menuPanel, personPanel, productPanel, accountingPanel);

        initFrame();
        registerPanels();
        connectColleagues();

        showPanel("menu");
    }

    private void initFrame() {
        setTitle("Sistema de Gestión - UPTC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setContentPane(container);
    }

    private void registerPanels() {
        container.add(menuPanel, "menu");
        container.add(personPanel, "persons");
        container.add(productPanel, "products");
        container.add(accountingPanel, "accounting");
    }

    private void connectColleagues() {
        menuPanel.setMediator(mediator);
        personPanel.setMediator(mediator);
        productPanel.setMediator(mediator);
        accountingPanel.setMediator(mediator);
    }

    public void showPanel(String name) {
        cardLayout.show(container, name);
    }

    @Override
    public IPersonView getPersonView() {
        return personPanel;
    }

    @Override
    public IProductView getProductView() {
        return productPanel;
    }

    @Override
    public IAccountingView getAccountingView() {
        return accountingPanel;
    }

    @Override
    public void launch() {
        setVisible(true);
    }
}