package service;

import finalproject.Book;
import finalproject.Member;
import service.dao.BookDAO;
import service.dao.LoanDAO;

import java.time.LocalDate;

public class LibraryService {

    private final BookDAO bookDAO = new BookDAO();
    private final LoanDAO loanDAO = new LoanDAO();

    public void addBook(Book book) {
        validateBook(book);
        bookDAO.insert(book);
    }

    public void issueBook(Book book, Member member) {

        if (!book.isAvailable())
            throw new IllegalStateException("Book not available");

        if (member.isSuspended())
            throw new IllegalStateException("Member is suspended");

        int active = loanDAO.countActiveLoansForMember(member.getId());
        if (active >= member.getMaxBorrowLimit())
            throw new IllegalStateException("Borrow limit reached");

        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = issueDate.plusDays(14);

        // create loan row
        loanDAO.addLoan(book.getId(), member.getId(), issueDate, dueDate);

        // mark book unavailable
        bookDAO.updateAvailability(book.getId(), false);

        // update local object (UI refresh)
        book.markIssued();
    }

    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank())
            throw new IllegalArgumentException("Title required");
        if (book.getAuthor() == null || book.getAuthor().isBlank())
            throw new IllegalArgumentException("Author required");
    }
}
