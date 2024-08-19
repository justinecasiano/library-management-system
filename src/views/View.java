package views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import config.App;

public abstract class View {
    protected JPanel panel;
    protected JPanel top;
    protected List<JButton> jButtons;

    public View(int width, int height) {
        panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(width, panel.getPreferredSize().height + height));
        panel.setBackground(App.PRIMARY);

        top = new JPanel(new FlowLayout(FlowLayout.LEADING, getvWidth(1.5), App.getwHeight(3)));
        top.setBackground(App.PRIMARY);
        panel.add(top, BorderLayout.NORTH);

        jButtons = new ArrayList<>();
    }

    public View(String title, int width, int height) {
        this(width, height);
        createCloseButton();

        JLabel label = new JLabel(title);
        label.setFont(new Font(App.FONT, Font.BOLD, App.HEADING));
        label.setForeground(App.ACCENT1);
        label.setPreferredSize(new Dimension(getvWidth(100), label.getPreferredSize().height));
        label.setHorizontalAlignment(JLabel.CENTER);

        top.add(label);
    }

    public static JTextArea createLabel(String text) {
        JTextArea label = new JTextArea(text);
        label.setWrapStyleWord(true);
        label.setLineWrap(true);
        label.setFocusable(false);
        label.setEditable(false);
        label.setOpaque(false);
        label.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 5));
        label.setForeground(App.ACCENT1);
        label.setBackground(App.ACCENT2);

        return label;
    }

    public void createCloseButton() {
        JButton button = new JButton("X");
        button.setActionCommand("Close");
        button.setFont(new Font(App.FONT, Font.BOLD, App.TITLE));
        button.setBackground(App.ACCENT1);
        button.setForeground(App.PRIMARY);
        button.setFocusPainted(false);
        jButtons.add(button);

        top.add(button);
    }

    public void setActionListeners(ActionListener listener) {
        for (JButton button : jButtons)
            button.addActionListener(listener);
    }

    public void refreshPanel(Component component) {
        panel.removeAll();
        panel.add(component);
        panel.revalidate();
        panel.repaint();
    }

    public void addComponents(Component[] components) {
        for (Component component : components) {
            panel.add(component);
        }
    }

    // Returns the View width based on the percentage
    public int getvWidth(double percent) {
        return (int) (panel.getPreferredSize().width * percent / 100);
    }

    // Returns the View height based on the percentage
    public int getvHeight(double percent) {
        return (int) (panel.getPreferredSize().height * percent / 100);
    }

    public JPanel getContent() {
        return panel;
    }
}
