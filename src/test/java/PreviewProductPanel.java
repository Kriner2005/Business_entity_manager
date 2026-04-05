import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import co.edu.uptc.view.panels.ProductPanel;

public class PreviewProductPanel {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Opcional: look & feel del sistema
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Preview Product Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);

            frame.add(new ProductPanel());

            frame.setVisible(true);
        });
    }
}