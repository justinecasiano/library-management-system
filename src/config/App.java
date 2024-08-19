package config;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

public interface App {
    // Constants for user interface
    int WIDTH = 1200;
    int HEIGHT = 600;

    // Constants for fonts
    String FONT = "Comic Sans Ms";
    int HEADING = 35;
    int TITLE = 30;
    int TEXT = 24;

    // Constants for colors
    Color ACCENT1 = Color.decode("#F050AC");
    Color ACCENT2 = Color.decode("#FFC3EE");
    Color PRIMARY = Color.decode("#FFE5FF");
    Color SECONDARY = Color.decode("#FF75CC");

    // Returns the Window width based on the percentage
    public static int getwWidth(double percent) {
        return (int) (WIDTH * percent / 100);
    }

    // Returns the Window height based on the percentage
    public static int getwHeight(double percent) {
        return (int) (HEIGHT * percent / 100);
    }

    public static ImageIcon getImage(String name, int width, int height) {
        return new ImageIcon(new ImageIcon(App.class.getResource("/resources/images/" + name))
                .getImage().getScaledInstance(App.getwWidth(width),
                        App.getwHeight(height), Image.SCALE_DEFAULT));
    }
}
