package finalproject;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan implements Serializable {
    private Book book;
    private LocalDate dueDate;

    public Loan(Book book) {
        this.book = book;
        this.dueDate = LocalDate.now().plusDays(14);
        book.setAvailable(false);
    }

    public double calculateFine() {
        long overdue = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        return overdue > 0 ? overdue * 0.5 : 0;
    }
}
