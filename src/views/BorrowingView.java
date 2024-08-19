package views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import config.App;

public class BorrowingView extends View {
    public JPanel borrowingPanel;
    public JComboBox bookDropdown;
    public JComboBox borrowerDropdown;
    public JTextField dateBorrowed;
    public JTextField dueDate;

    public BorrowingView() {
        super("Borrowing", App.getwWidth(100), App.getwWidth(100));
        borrowingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, App.getwWidth(50), 5));
        borrowingPanel.setBackground(App.PRIMARY);

        panel.add(borrowingPanel);
    }

    public void createInputFields(List<String> books, List<String> borrowers) {
        books.add("Select a Book");
        bookDropdown = new JComboBox(books.toArray());
        bookDropdown.setSelectedItem("Select a Book");
        bookDropdown.setFont(new Font(App.FONT, Font.BOLD, App.TITLE - 3));
        bookDropdown.setForeground(App.ACCENT1);
        bookDropdown.setPreferredSize(new Dimension(App.getwWidth(55), App.getwHeight(15)));

        borrowers.add("Select a Borrower");
        borrowerDropdown = new JComboBox(borrowers.toArray());
        borrowerDropdown.setSelectedItem("Select a Borrower");
        borrowerDropdown.setFont(new Font(App.FONT, Font.BOLD, App.TITLE - 3));
        borrowerDropdown.setForeground(App.ACCENT1);
        borrowerDropdown.setPreferredSize(new Dimension(App.getwWidth(55), App.getwHeight(15)));

        borrowerDropdown.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                LocalDate due = (e.getItem().toString().contains("Student")) ? LocalDate.now().plusDays(3) : null;
                dueDate.setText((due == null) ? "No Due Date" : due.toString());
            }
        });

        JPanel dateBorrowedPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 5));
        dateBorrowedPanel.setBackground(App.PRIMARY);

        JLabel dateBorrowedLabel = new JLabel("Date Borrowed: ");
        dateBorrowedLabel.setFont(new Font(App.FONT, Font.BOLD, App.TITLE - 3));
        dateBorrowedLabel.setForeground(App.ACCENT1);

        dateBorrowed = new JTextField(LocalDate.now().toString());
        dateBorrowed.setFont(new Font(App.FONT, Font.BOLD, App.TITLE - 3));
        dateBorrowed.setForeground(App.ACCENT1);
        dateBorrowed.setPreferredSize(new Dimension(App.getwWidth(30), App.getwHeight(15)));

        JPanel dueDatePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 5));
        dueDatePanel.setBackground(App.PRIMARY);

        JLabel dueDateLabel = new JLabel("Due Date: ");
        dueDateLabel.setFont(new Font(App.FONT, Font.BOLD, App.TITLE - 3));
        dueDateLabel.setForeground(App.ACCENT1);

        dueDate = new JTextField(LocalDate.now().toString());
        dueDate.setFont(new Font(App.FONT, Font.BOLD, App.TITLE - 3));
        dueDate.setForeground(App.ACCENT1);
        dueDate.setEditable(false);
        dueDate.setFocusable(false);
        dueDate.setPreferredSize(new Dimension(App.getwWidth(30), App.getwHeight(15)));

        JButton borrow = new JButton("Borrow");
        borrow.setActionCommand("Borrow");
        borrow.setFont(new Font(App.FONT, Font.BOLD, App.HEADING));
        borrow.setBackground(App.ACCENT1);
        borrow.setForeground(App.PRIMARY);
        jButtons.add(borrow);

        dateBorrowedPanel.add(dateBorrowedLabel);
        dateBorrowedPanel.add(dateBorrowed);
        dueDatePanel.add(dueDateLabel);
        dueDatePanel.add(dueDate);

        borrowingPanel.add(bookDropdown);
        borrowingPanel.add(borrowerDropdown);
        borrowingPanel.add(dateBorrowedPanel);
        borrowingPanel.add(dueDatePanel);
        borrowingPanel.add(borrow);
    }
}
