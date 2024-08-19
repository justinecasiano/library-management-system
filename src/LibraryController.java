import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import config.App;
import models.Book;
import models.Borrower;
import models.Borrowing;
import models.Database;
import utilities.Error;
import utilities.Error.ErrorType;
import views.BookListView;
import views.BorrowerListView;
import views.BorrowingView;
import views.MainView;
import views.PenaltyView;
import views.ReturningView;
import views.Window;

public class LibraryController implements ActionListener {
    private Database database;
    private MainView mainView;
    private BookListView bookListView;
    private BorrowerListView borrowerListView;
    private BorrowingView borrowingView;
    private ReturningView returningView;
    private PenaltyView penaltyView;
    private List<Book> books;
    private List<Borrower> borrowers;

    public LibraryController(Database database) {
        this.database = database;
        initModels();
        initViews();
        setView(mainView.getContent());
        Window.show();
    }

    public void initModels() {
        books = database.getBooks();
        borrowers = database.getBorrowers();
    }

    public void initViews() {
        mainView = new MainView();
        mainView.setActionListeners(this);

        bookListView = new BookListView();
        bookListView.createCategoryAndBooks("Fictional", getBooks("Fictional"));
        bookListView.createCategoryAndBooks("Non-Fictional", getBooks("Non-Fictional"));
        bookListView.createCategoryAndBooks("Academic", getBooks("Academic"));
        bookListView.setActionListeners(this);

        borrowerListView = new BorrowerListView();
        borrowerListView.createBorrowersList("Teacher", getBorrowers("Teacher"));
        borrowerListView.createBorrowersList("Student", getBorrowers("Student"));
        borrowerListView.setActionListeners(this);

        borrowingView = new BorrowingView();
        borrowingView.createInputFields(getBookTitles(), getBorrowerNames());
        borrowingView.setActionListeners(this);

        returningView = new ReturningView();
        returningView.setActionListeners(this);

        penaltyView = new PenaltyView();
        penaltyView.setActionListeners(this);
    }

    public void updateBookList() {
        books = database.getBooks();
        bookListView = new BookListView();
        bookListView.createCategoryAndBooks("Fictional", getBooks("Fictional"));
        bookListView.createCategoryAndBooks("Non-Fictional", getBooks("Non-Fictional"));
        bookListView.createCategoryAndBooks("Academic", getBooks("Academic"));
        bookListView.setActionListeners(this);
        setView(bookListView.getContent());
    }

    public void clearBorrowingFields() {
        borrowingView = new BorrowingView();
        borrowingView.createInputFields(getBookTitles(), getBorrowerNames());
        borrowingView.setActionListeners(this);
    }

    public List<String> getBorrowerNames() {
        return borrowers.stream()
                .map((borrower) -> String.format("%s (%s)", borrower.name, borrower.type))
                .collect(Collectors.toList());
    }

    public Borrower getBorrower(String name) {
        return borrowers.stream()
                .filter((borrower) -> borrower.name.equals(name))
                .findFirst().get();
    }

    public List<Borrower> getBorrowers(String type) {
        return borrowers.stream()
                .filter((borrower) -> borrower.type.equals(type))
                .collect(Collectors.toList());
    }

    public List<String> getBookTitles() {
        return books.stream()
                .map((book) -> String.format("%s (%s)", book.title, book.category))
                .collect(Collectors.toList());
    }

    public Book getBook(String title) {
        return books.stream()
                .filter((book) -> book.title.equals(title))
                .findFirst().get();
    }

    public List<Book> getBooks(String category) {
        return books.stream()
                .filter((book) -> book.category.equals(category))
                .collect(Collectors.toList());
    }

    public void setView(JPanel panel) {
        Window.setView(panel);
    }

    public boolean validateBorrowing() {
        String bookTitle = (String) borrowingView.bookDropdown.getSelectedItem();
        String borrowerName = (String) borrowingView.borrowerDropdown.getSelectedItem();
        LocalDate dateBorrowed = null;

        Book book = getBook(bookTitle.split("\\s\\(")[0]);
        Borrower borrower = getBorrower(borrowerName.split("\\s\\(")[0]);

        if (bookTitle.equals("Select a Book") || borrowerName.equals("Select a Borrower"))
            return Error.show(ErrorType.NO_REQUIRED_DETAILS);

        if (book.category.equals("Academic"))
            return Error.show(ErrorType.BORROWING_ACADEMIC_BOOKS);

        if ((borrower.type.equals("Student") && database.getBorrowings(borrower) > 2) ||
                (borrower.type.equals("Teacher") && database.getBorrowings(borrower) > 5))
            return Error.show(ErrorType.EXCEED_BOOKS_BORROWED);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formatter = formatter.withLocale(Locale.TAIWAN);

            dateBorrowed = LocalDate.parse(borrowingView.dateBorrowed.getText(), formatter);
        } catch (Exception e) {
            System.out.println((e));
            return Error.show(ErrorType.INVALID_ENTRY);
        }

        if (dateBorrowed.isBefore(LocalDate.now()))
            return Error.show(ErrorType.INVALID_ENTRY);

        LocalDate dueDate = (borrower.type.equals("Student")) ? dateBorrowed.plusDays(3) : null;
        database.addBorrowing(new Borrowing(borrower, book, dateBorrowed, dueDate));

        JPanel borrowingInfo = new JPanel();
        borrowingInfo.setBackground(App.PRIMARY);
        borrowingInfo.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel info = new JLabel(String.format("Successfully borrowed %s by %s", book.title, borrower.name));
        info.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
        info.setForeground(App.ACCENT1);

        borrowingInfo.add(info);
        createDialog(borrowingInfo, "Successfully Borrowed");

        updateBookList();
        clearBorrowingFields();
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Book List":
                setView(bookListView.getContent());
                break;
            case "Borrowers":
                setView(borrowerListView.getContent());
                break;
            case "Borrowing":
                setView(borrowingView.getContent());
                break;
            case "Returning":
                setView(returningView.getContent());
                break;
            case "Penalty":
                setView(penaltyView.getContent());
                break;
            case "Close":
                setView(mainView.getContent());
                break;
            case "View Book Information":
                JButton book = (JButton) e.getSource();
                showBookDialog(getBook(book.getName()));
                break;
            case "View Borrower Information":
                JButton borrower = (JButton) e.getSource();
                showBorrowerDialog(getBorrower(borrower.getName()));
                break;
            case "Borrow":
                validateBorrowing();
                break;
        }
    }

    public void showBookDialog(Book book) {
        JLabel author = new JLabel("Author: " + book.author);
        author.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
        author.setForeground(App.ACCENT1);

        JLabel isbn = new JLabel("ISBN: " + book.ISBN);
        isbn.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
        isbn.setForeground(App.ACCENT1);

        JLabel copyright = new JLabel(book.copyright);
        copyright.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
        copyright.setForeground(App.ACCENT1);

        JLabel publisher = new JLabel("Publisher: " + book.publisher);
        publisher.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
        publisher.setForeground(App.ACCENT1);

        JPanel bookInfo = new JPanel(new GridLayout(4, 1, 0, 1));
        bookInfo.setBackground(App.PRIMARY);
        bookInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
        bookInfo.add(author);
        bookInfo.add(isbn);
        bookInfo.add(copyright);
        bookInfo.add(publisher);

        createDialog(bookInfo, "Book Information");
    }

    public void showBorrowerDialog(Borrower borrower) {
        int column = (borrower.borrowing != null) ? borrower.borrowing.size() * 4 : 1;
        JPanel borrowerInfo = new JPanel(new GridLayout(column, 1, 0, 1));
        borrowerInfo.setBackground(App.PRIMARY);
        borrowerInfo.setBorder(new EmptyBorder(10, 10, 10, 10));

        if (borrower.borrowing != null) {
            for (Borrowing borrowing : borrower.borrowing) {
                JLabel book = new JLabel("Book: " + borrowing.book.title);
                book.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
                book.setForeground(App.ACCENT1);

                JLabel category = new JLabel("Category: " + borrowing.book.category);
                category.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
                category.setForeground(App.ACCENT1);

                JLabel dateBorrowed = new JLabel("Date Borrowed: " + borrowing.dateBorrowed.toString());
                dateBorrowed.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
                dateBorrowed.setForeground(App.ACCENT1);

                JLabel dueDate = new JLabel("Due Date: " + borrowing.dueDate.toString());
                dueDate.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
                dueDate.setForeground(App.ACCENT1);

                borrowerInfo.add(book);
                borrowerInfo.add(dateBorrowed);
                borrowerInfo.add(dueDate);
            }
        } else {
            JLabel info = new JLabel("No borrowings yet");
            info.setFont(new Font(App.FONT, Font.BOLD, App.TEXT - 3));
            info.setForeground(App.ACCENT1);

            borrowerInfo.add(info);
        }
        createDialog(borrowerInfo, "Borrower Information");
    }

    public void createDialog(JPanel panel, String title) {
        JOptionPane dialog = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_OPTION);
        dialog.setBackground(App.PRIMARY);
        dialog.createDialog(Window.getFrame(), title).setVisible(true);
    }
}
