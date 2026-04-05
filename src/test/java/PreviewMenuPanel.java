import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import co.edu.uptc.view.panels.MenuPanel;

public class PreviewMenuPanel {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Look & Feel opcional
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Preview Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);

            frame.add(new MenuPanel());

            frame.setVisible(true);
        });
    }
}