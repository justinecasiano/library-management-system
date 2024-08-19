package models;

import java.time.LocalDate;

public class Borrowing {
   public Borrower borrower;
   public Book book;
   public LocalDate dateBorrowed; 
   public LocalDate dueDate;

   public Borrowing(Borrower borrower, Book book, LocalDate dateBorrowed, LocalDate dueDate) {
      this.borrower = borrower;
      this.book = book;
      this.dateBorrowed = dateBorrowed;
      this.dueDate = dueDate;
   } 
}
