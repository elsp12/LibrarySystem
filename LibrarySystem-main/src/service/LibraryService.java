package service;

	import finalproject.*;
	import finalproject.db.LibraryDAO;//ketu do vendosi Sindi reference per db 

	import java.time.LocalDate;
	import java.time.temporal.ChronoUnit;
	import java.util.*;
	import java.util.stream.Collectors;
	import javax.mail.MessagingException;


	public class LibraryService {
		 // Temporary in-memory collections
	    // (DB group replaces these with DAO calls)
	    private List<Book> books = new ArrayList<>();
	    private List<Member> members = new ArrayList<>();
	    private List<Loan> loans = new ArrayList<>();

	    /* ================= BOOK ================= */

	    public void addBook(Book book) {
	        validateBook(book);
	        books.add(book);
	    }

	    public void removeBook(Book book) {
	        if (!book.isAvailable()) {
	            throw new IllegalStateException("Book currently issued");
	        }
	        books.remove(book);
	    }

	    /* ================= MEMBER ================= */

	    public void registerMember(Member member) {
	        members.add(member);
	    }

	    /* ================= LOAN ================= */

	    //DBMS corrections needed 
	    public void issueBook(Book book, Member member) throws SQLException {
	        if (!book.isAvailable()) throw new IllegalStateException("Book not available");
	        Loan loan = new Loan(book, member);
	        book.markIssued();
	        bookDAO.update(book); // mark book as unavailable
	        loanDAO.addLoan(loan);
	    }

	    public void returnBook(Loan loan) throws SQLException {
	        loan.closeLoan();
	        loan.getBook().markReturned();
	        bookDAO.update(loan.getBook());
	        loanDAO.update(loan);
	    }

	    public void returnBook(Loan loan) {
	        loan.closeLoan();
	        loan.book.markReturned();
	    }

	    /* ================= RULES ================= */

	    private int activeLoansFor(Member member) {
	        return (int) loans.stream()
	                .filter(l -> l.member == member && l.returnDate == null)
	                .count();
	    }

	    private void validateBook(Book book) {
	        if (book.getTitle().isBlank() || book.getAuthor().isBlank()) {
	            throw new IllegalArgumentException("All fields must be filled");
	        }
	    }
	    
	    
	    public void sendOverdueEmailIfNeeded(Loan loan) throws MessagingException {
	        if (loan.isOverdue()) {
	            double fine = loan.calculateFine();

	            // Update fine in DB via DAO
	            loan.setFine(fine); // make setter in Loan
	            loanDAO.updateLoan(loan);

	            // Send email
	            EmailService emailService = new EmailService();
	            emailService.sendOverdueEmail(
	                loan.getMember().getEmail(),
	                loan.getMember().getName(),
	                loan.getBook().getTitle(),
	                (int) ChronoUnit.DAYS.between(loan.getDueDate(), java.time.LocalDate.now()),
	                fine
	            );
	        }
	    }

	}


