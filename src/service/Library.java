package service;

import finalproject.Book;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private static final String FILE = "library.dat";
    public List<Book> books;

    public Library() {
        books = load();
    }

    public void addBook(Book b) {
        books.add(b);
        save();
    }

    public void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE))) {
            out.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Book> load() {
        File f = new File(FILE);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE))) {
            return (List<Book>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        
    }
}
