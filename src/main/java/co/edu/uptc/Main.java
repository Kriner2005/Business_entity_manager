package co.edu.uptc;

import javax.swing.SwingUtilities;

import co.edu.uptc.presenter.Runner;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Runner runner = new Runner();
            runner.run();
        });
    }
}