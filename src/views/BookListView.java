package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import config.App;
import models.Book;

public class BookListView extends View {
    public JPanel categoryPanel;
    JScrollPane scrollPane;

    public BookListView() {
        super("Book List", App.getwWidth(100), App.getwWidth(100));
        categoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, App.getwWidth(1.5), App.getwHeight(5)));
        categoryPanel.setBackground(App.PRIMARY);

        scrollPane = new JScrollPane(categoryPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        panel.add(scrollPane);
    }

    public void createCategoryAndBooks(String category, List<Book> books) {
        categoryPanel.setPreferredSize(
                new Dimension(App.getwWidth(100), categoryPanel.getPreferredSize().height + App.getwHeight(25)));

        JLabel label = new JLabel(category);
        label.setFont(new Font(App.FONT, Font.BOLD, App.TITLE));
        label.setBorder(new EmptyBorder(0, getvWidth(7), 0, 0));
        label.setForeground(App.ACCENT1);
        label.setPreferredSize(new Dimension(getvWidth(100), label.getPreferredSize().height));
        categoryPanel.add(label);

        for (int i = 0; i < books.size(); i++) {
            JButton book = createBook(books.get(i));
            categoryPanel.add(book);

            if ((i + 1) % 3 == 1)
                categoryPanel.setPreferredSize(
                        new Dimension(App.getwWidth(100),
                                categoryPanel.getPreferredSize().height + book.getPreferredSize().height + 5));
        }
    }

    public JButton createBook(Book book) {
        JTextArea bookName = createLabel(book.title);
        bookName.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));

        JLabel image = new JLabel(App.getImage(book.image, 26, 70));
        image.setPreferredSize(new Dimension(getvWidth(26), App.getwHeight(70)));

        JTextArea bookStatus = createLabel(book.status);

        JButton button = new JButton();
        button.setName(book.title);
        button.setActionCommand("View Book Information");
        button.setLayout(new BorderLayout(App.getwWidth(2.5), App.getwHeight(2.5)));
        button.setBackground(App.ACCENT2);

        button.add(bookName, BorderLayout.NORTH);
        button.add(image);
        button.add(bookStatus, BorderLayout.SOUTH);

        jButtons.add(button);
        return button;
    }
}
