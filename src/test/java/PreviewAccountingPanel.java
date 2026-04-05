import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import co.edu.uptc.view.panels.AccountingPanel;

public class PreviewAccountingPanel {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Look & Feel opcional (mejora visual)
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Preview Accounting Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);

            frame.add(new AccountingPanel());

            frame.setVisible(true);
        });
    }
}