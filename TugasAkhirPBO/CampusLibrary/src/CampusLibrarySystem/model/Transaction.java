package CampusLibrarySystem.model;

import java.time.LocalDate;

public class Transaction {
    private String id;
    private String memberId;
    private String isbn;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDate actualReturnDate;
    private String status;
    private int fine;

    public Transaction() {}

    public Transaction(String id, String memberId, String isbn,
                       LocalDate borrowDate, LocalDate returnDate,
                       LocalDate actualReturnDate, String status, int fine) {
        this.id = id;
        this.memberId = memberId;
        this.isbn = isbn;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.actualReturnDate = actualReturnDate;
        this.status = status;
        this.fine = fine;
    }

    public String getId() { return id; }
    public String getMemberId() { return memberId; }
    public String getIsbn() { return isbn; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public LocalDate getActualReturnDate() { return actualReturnDate; }
    public String getStatus() { return status; }
    public int getFine() { return fine; }

    public void setId(String id) { this.id = id; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setActualReturnDate(LocalDate actualReturnDate) { this.actualReturnDate = actualReturnDate; }
    public void setStatus(String status) { this.status = status; }
    public void setFine(int fine) { this.fine = fine; }
}
