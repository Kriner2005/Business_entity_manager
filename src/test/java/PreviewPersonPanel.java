import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import co.edu.uptc.view.panels.PersonPanel;

public class PreviewPersonPanel {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Preview Person Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);

            frame.add(new PersonPanel());

            frame.setVisible(true);
        });
    }
}