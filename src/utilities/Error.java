package utilities;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import config.App;
import views.Window;

public interface Error {
    public enum ErrorType {
        EXCEED_BOOKS_BORROWED("Sorry! You have exceeded the number of books to be borrowed."),
        INVALID_AMOUNT("Invalid Amount! Please enter a valid amount."),
        INVALID_ENTRY("Invalid Input"),
        NO_REQUIRED_DETAILS("Input Necessary Details"),
        BORROWING_ACADEMIC_BOOKS("Academic Books are only allowed inside the library."),
        HAS_PENALTY("Pay the penalty first before you can borrow or return a book");

        private String message;

        ErrorType(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static boolean show(ErrorType error) {
        showDialog(error);
        return false;
    }

    public static void showDialog(ErrorType error) {
        JPanel panel = new JPanel();
        panel.setBackground(App.PRIMARY);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel message = new JLabel(error.getMessage());
        message.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
        message.setForeground(App.ACCENT1);

        panel.add(message);
        JOptionPane dialog = new JOptionPane(panel, JOptionPane.ERROR_MESSAGE, -1);
        dialog.setBackground(App.PRIMARY);
        dialog.createDialog(Window.getFrame(), "Error").setVisible(true);
    }
}
