package finalproject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {

    private int id;                 // DB PK
    private Book book;
    private Member member;

    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;   // null if active
    private double fine;

    // Optional feature: extension request
    private boolean extensionRequested;

    /* ================= CONSTRUCTORS ================= */

    // New loan (used by service/GUI logic)
    public Loan(Book book, Member member, LocalDate issueDate, LocalDate dueDate) {
        this.book = book;
        this.member = member;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.fine = 0.0;
        this.extensionRequested = false;
    }

    // Loaded from DB
    public Loan(int id, Book book, Member member, LocalDate issueDate, LocalDate dueDate,
                LocalDate returnDate, double fine, boolean extensionRequested) {
        this.id = id;
        this.book = book;
        this.member = member;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fine = fine;
        this.extensionRequested = extensionRequested;
    }

    /* ================= STATUS / RULES ================= */

    public boolean isActive() {
        return returnDate == null;
    }

    public boolean isOverdue() {
        return isActive() && LocalDate.now().isAfter(dueDate);
    }

    public double calculateFine() {
        if (!isOverdue()) return 0.0;
        long daysLate = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        return daysLate > 0 ? daysLate * 0.5 : 0.0; // €0.50 per day
    }

    public void closeLoan() {
        this.returnDate = LocalDate.now();
        this.fine = calculateFine();
    }

    /* ================= EXTENSION ================= */

    public void requestExtension() {
        this.extensionRequested = true;
    }

    public void approveExtension(int extraDays) {
        if (extensionRequested && isActive() && extraDays > 0) {
            this.dueDate = this.dueDate.plusDays(extraDays);
            this.extensionRequested = false;
        }
    }

    /* ================= GETTERS / SETTERS ================= */

    public int getId() { return id; }
    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public double getFine() { return fine; }
    public boolean isExtensionRequested() { return extensionRequested; }

    public void setId(int id) { this.id = id; }
    public void setFine(double fine) { this.fine = fine; }
}
