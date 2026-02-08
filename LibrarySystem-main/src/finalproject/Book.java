
package finalproject;
import java.util.List;

public class Book {
    private int id;              // DB primary key
    private String title;
    private String author;
    private boolean available;
    private String genre;
    private String isbn;
    private int publicationYear;
    private String language;
    private int edition;
    private int borrowCount;
    
    // Physical location
    private String shelf;
    private int row;

    // Categories
    private List<String> categories;

    // For loading from DB + added attributes 
    public Book(int id, String title, String author, String genre, String isbn,
            int publicationYear, String language, int edition,
            String shelf, int row, boolean available, int borrowCount,
            List<String> categories) {
    this(title, author, genre, isbn, publicationYear, language, edition, shelf, row, categories);
    this.id = id;
    this.available = available;
    this.borrowCount = borrowCount;
   }

    // For creating from UI (DB will generate id)
    public Book(String title, String author, String genre, String isbn,
            int publicationYear, String language, int edition,
            String shelf, int row, List<String> categories) {
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.isbn = isbn;
    this.publicationYear = publicationYear;
    this.language = language;
    this.edition = edition;
    this.shelf = shelf;
    this.row = row;
    this.available = true;
    this.borrowCount = 0;
    this.categories = categories;
<<<<<<< HEAD
    }

=======
    }
    
 // Minimal constructor for older DAO queries
    public Book(int id, String title, String author, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = available;
    }
 // Minimal constructor for UI (used in MainView: new Book(title, author))
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
        this.borrowCount = 0;
    }


>>>>>>> 27eddc7 (Align Book model, DAO, service, and GUI; add LoanDAO and EmailService stub)
 // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public String getIsbn() { return isbn; }
    public int getPublicationYear() { return publicationYear; }
    public String getLanguage() { return language; }
    public int getEdition() { return edition; }
    public boolean isAvailable() { return available; }
    public int getBorrowCount() { return borrowCount; }
    public String getShelf() { return shelf; }
    public int getRow() { return row; }
    public List<String> getCategories() { return categories; }
    
 // Setters (only for fields that can change)
    public void setId(int id) { this.id = id; }
    public void setAvailable(boolean available) { this.available = available; }
    public void setShelf(String shelf) { this.shelf = shelf; }
    public void setRow(int row) { this.row = row; }

 // Methods to mark book as issued or returned
    public void markIssued() {
        this.available = false;
        this.borrowCount++;
    }

    public void markReturned() {
        this.available = true;
    }

    @Override
    public String toString() {
        return title + " by " + author + (available ? " (Available)" : " (Issued)") +
               " | Genre: " + genre + " | ISBN: " + isbn + " | Borrowed: " + borrowCount + " times";
    }
}
