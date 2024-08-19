package views;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public interface Window {
    JFrame frame = new JFrame();

    public static void init(String title, int width, int height) {
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void show() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public static void setView(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    public static Container getContentPane() {
        return frame.getContentPane();
    }

    public static JFrame getFrame() {
        return frame;
    }
}