package co.edu.uptc.view.mediator;

import javax.swing.JOptionPane;

import co.edu.uptc.config.MessageManager;
import co.edu.uptc.view.MainFrame;
import co.edu.uptc.view.interfaces.IColleague;
import co.edu.uptc.view.interfaces.IMediator;
import co.edu.uptc.view.panels.AccountingPanel;
import co.edu.uptc.view.panels.MenuPanel;
import co.edu.uptc.view.panels.PersonPanel;
import co.edu.uptc.view.panels.ProductPanel;

public class AppMediator implements IMediator {

    // ── referencias a todos los colegas ────────────────────────────────────
    // El mediador conoce a todos. Los colegas solo conocen al mediador.
    private final MainFrame frame;
    private final MenuPanel menuPanel;
    private final PersonPanel personPanel;
    private final ProductPanel productPanel;
    private final AccountingPanel accountingPanel;

    public AppMediator(MainFrame frame,
            MenuPanel menuPanel,
            PersonPanel personPanel,
            ProductPanel productPanel,
            AccountingPanel accountingPanel) {
        this.frame = frame;
        this.menuPanel = menuPanel;
        this.personPanel = personPanel;
        this.productPanel = productPanel;
        this.accountingPanel = accountingPanel;
    }

    // ── centro de control ──────────────────────────────────────────────────
    // Aquí llegan TODOS los eventos de TODOS los paneles.
    // El mediador decide qué hacer con cada uno.
    // Los paneles nunca se hablan entre sí directamente.
    @Override
    public void notify(IColleague sender, String event) {
        switch (event) {
            case "persons" -> showPanel("persons");
            case "products" -> showPanel("products");
            case "accounting" -> showPanel("accounting");
            case "back" -> showPanel("menu");
            case "exit" -> onExit();
        }
    }

    @Override
    public void showPanel(String panelName) {
        frame.showPanel(panelName);
    }

    private void onExit() {
        int confirm = JOptionPane.showConfirmDialog(
                frame,
                MessageManager.msg("dialog.exit.msg"),
                MessageManager.msg("dialog.exit.title"),
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}