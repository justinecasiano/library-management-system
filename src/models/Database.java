package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Database {
   private Connection con;
   public List<Book> books;
   public List<Borrower> borrowers;

   public Database() {
      try {
         this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
         books = getBooks();
         borrowers = getBorrowers();
      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, e, "Database Error", JOptionPane.ERROR_MESSAGE);
         System.out.println(e);
         System.exit(1);
      }
   }

   public int getBorrowings(Borrower borrower) {
      int count = 0;
      try (Statement stmt = con.createStatement()) {
         String selectBooks = String.format("SELECT COUNT(*) FROM borrowing JOIN borrower USING (borrower_id) WHERE borrower_id=%s", borrower.borrowerID);
         try (ResultSet result = stmt.executeQuery(selectBooks)) {
            while (result.next()) count++ ;
         } catch (SQLException e) {
            System.out.println(e);
         }
      } catch (SQLException e) {
         System.out.println(e);
      }
      return count;
   }

   public void addBorrowing(Borrowing borrowing) {
      try (Statement stmt = con.createStatement()) {
         String insertBorrowing = "INSERT INTO borrowing (`book_id`, `borrower_id`, `date_borrowed`, `due_date`) VALUES "
               + "(?, ?, ?, ?)";
         try (PreparedStatement pstmt = con.prepareStatement(insertBorrowing)) {
            pstmt.setString(1, borrowing.book.bookID);
            pstmt.setString(2, borrowing.borrower.borrowerID);
            pstmt.setString(3, borrowing.dateBorrowed.toString());
            pstmt.setString(4, (borrowing.dueDate != null) ? borrowing.dueDate.toString() : null);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected);
         } catch (SQLException e) {
            System.out.println(e);
         }

         String updateBook = String.format("UPDATE book SET status='%s' WHERE book_id=%s", "Borrowed",
               borrowing.book.bookID);
         int rowsAffected = stmt.executeUpdate(updateBook);
         System.out.println(rowsAffected);
      } catch (SQLException e) {
         System.out.println(e);
      }
   }

   public List<Book> getBooks() {
      books = new ArrayList<>();
      try (Statement stmt = con.createStatement()) {
         String selectBooks = "SELECT * FROM book WHERE status='Available'";
         try (ResultSet result = stmt.executeQuery(selectBooks)) {
            while (result.next()) {
               Book book = new Book();
               book.bookID = result.getString("book_id");
               book.title = result.getString("title");
               book.ISBN = result.getString("isbn");
               book.author = result.getString("author");
               book.copyright = result.getString("copyright");
               book.publisher = result.getString("publisher");
               book.category = result.getString("category");
               book.status = result.getString("status");
               book.image = result.getString("image");

               books.add(book);
            }
         } catch (SQLException e) {
            System.out.println(e);
         }
      } catch (SQLException e) {
         System.out.println(e);
      }
      return books;
   }

   public List<Borrower> getBorrowers() {
      List<Borrower> borrowers = new ArrayList<>();
      try (Statement stmt = con.createStatement()) {
         String selectTeacherBorrowers = "SELECT * FROM borrower JOIN teacher USING (borrower_id)";

         try (ResultSet result = stmt.executeQuery(selectTeacherBorrowers)) {
            while (result.next()) {
               Teacher teacher = new Teacher();
               teacher.borrowerID = result.getString("borrower_id");
               teacher.name = result.getString("name");
               teacher.ID = result.getString("id_num");
               teacher.type = result.getString("type");
               teacher.department = result.getString("department");

               borrowers.add(teacher);
            }
         } catch (SQLException e) {
            System.out.println(e);
         }

         String selectStudentBorrowers = "SELECT * FROM borrower JOIN student USING (borrower_id)";

         try (ResultSet result = stmt.executeQuery(selectStudentBorrowers)) {
            while (result.next()) {
               Student student = new Student();
               student.borrowerID = result.getString("student_id");
               student.name = result.getString("name");
               student.ID = result.getString("id_num");
               student.type = result.getString("type");
               student.yearLevel = result.getString("year_level");
               student.section = result.getString("section");

               borrowers.add(student);
            }
         } catch (SQLException e) {
            System.out.println(e);
         }
      } catch (SQLException e) {
         System.out.println(e);
      }
      return borrowers;
   }
}
