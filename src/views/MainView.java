package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.App;

public class MainView extends View {
    private String[] views = { "Book List", "Borrowers", "Borrowing", "Returning" };
    private JPanel center;

    public MainView() {
        super(App.getwWidth(100), App.getwHeight(100));
        center = new JPanel(new FlowLayout(FlowLayout.CENTER, getvWidth(1.5), App.getwHeight(3)));
        center.setPreferredSize(new Dimension(App.getwWidth(40), center.getPreferredSize().height));
        center.setBackground(App.PRIMARY);

        createTitle();
        createImage();
        createButtons();
    }

    private void createTitle() {
        JLabel title = new JLabel("Library Management System");
        title.setFont(new Font(App.FONT, Font.BOLD, App.HEADING));
        title.setForeground(App.ACCENT1);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setPreferredSize(new Dimension(getvWidth(100), title.getPreferredSize().height));

        center.add(title, BorderLayout.CENTER);
    }

    private void createImage() {
        JLabel image = new JLabel(App.getImage("xiaojie-cat.gif", 50, 70));
        image.setPreferredSize(new Dimension(getvWidth(100), App.getwHeight(70)));

        center.add(image);
    }

    private void createButtons() {
        for (int i = 0; i < views.length; i++) {
            JButton button = new JButton(views[i]);
            button.setActionCommand(views[i]);
            button.setFont(new Font(App.FONT, Font.BOLD, App.TITLE));
            button.setBackground(App.ACCENT1);
            button.setForeground(App.PRIMARY);
            button.setFocusPainted(false);

            jButtons.add(button);
            center.add(button);
        }

        panel.add(center);
    }
}
