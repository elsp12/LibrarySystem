package finalproject;

//import java.io.Serializable;-removed since not working with files 
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
    
	private int id; // DB-generated
	private Book book;
	private Member member;
	
	private LocalDate issueDate;
	private LocalDate dueDate;
	private LocalDate returnDate;
    private double fine;
    
    private boolean extensionRequested;

    //construcotr for new loan from UI 
    public Loan(Book book , Member member) {
        this.book = book;
        this.member=member;//added 
        this.dueDate = issueDate.plusDays(14);//added
        this.extensionRequested = false;
        book.setAvailable(false);//mark book as issued
    }

 // Constructor for loading loan from DB
    public Loan(int id, Book book, Member member, LocalDate issueDate, LocalDate dueDate,
                LocalDate returnDate, double fine, boolean extensionRequested) {
        this(book, member);
        this.id = id;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fine = fine;
        this.extensionRequested = extensionRequested;
    }
    
 // Check if loan is overdue
    public boolean isOverdue() {
        return returnDate == null && LocalDate.now().isAfter(dueDate);
    }

    // Calculate fine for overdue loan
    public double calculateFine() {
        if (!isOverdue()) return 0;
        long daysLate = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        return daysLate * 0.5;
    }
    
 // Close the loan (return book)
    public void closeLoan() {
        this.returnDate = LocalDate.now();
        this.fine = calculateFine();
        this.book.setAvailable(true); // mark book as returned
    }

    // Request extension
    public void requestExtension() {
        this.extensionRequested = true;
    }
    
    //connect to gui 
 // Approve the extension (adds extra days to due date)
    public void approveExtension(int extraDays) {
        if (extensionRequested && returnDate == null) {
            dueDate = dueDate.plusDays(extraDays);
            extensionRequested = false; // reset after approval
        }
    }

    // Getters
    public int getId() { return id; }
    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public double getFine() { return fine; }
    public boolean isExtensionRequested() { return extensionRequested; }

}
