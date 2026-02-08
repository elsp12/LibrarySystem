package gui;

import finalproject.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.dao.BookDAO;

public class MainView extends BorderPane {

    private final BookDAO bookDAO = new BookDAO();

    // master list from DB (unfiltered)
    private final ObservableList<Book> masterData = FXCollections.observableArrayList();

    // keep table as a field so we can refresh reliably
    private final TableView<Book> table = new TableView<>();

    public MainView() {

        // SEARCH BAR
        TextField searchField = new TextField();
        searchField.setPromptText("Search by title or author...");

        // TABLE COLUMNS
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getTitle()));

        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(cell.getValue().getAuthor()));

        TableColumn<Book, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(
                        cell.getValue().isAvailable() ? "Available" : "Issued"
                ));

        table.getColumns().add(titleCol);
        table.getColumns().add(authorCol);
        table.getColumns().add(statusCol);

        // FILTERING LOGIC
        FilteredList<Book> filteredData = new FilteredList<>(masterData, b -> true);

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            String filter = (newValue == null) ? "" : newValue.trim().toLowerCase();

            filteredData.setPredicate(book -> {
                if (filter.isEmpty()) return true;

                String title = (book.getTitle() == null) ? "" : book.getTitle().toLowerCase();
                String author = (book.getAuthor() == null) ? "" : book.getAuthor().toLowerCase();

                return title.contains(filter) || author.contains(filter);
            });
        });

        SortedList<Book> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        // -------------------------
        // BUTTONS
        // -------------------------
        Button addBook = new Button("Add Book");
        Button issueBook = new Button("Issue Book");
        Button returnBook = new Button("Return Book");

        // Add Book action (DB INSERT)
        addBook.setOnAction(e -> {
            Dialog<Book> dialog = new Dialog<>();
            dialog.setTitle("Add Book");

            VBox vbox = new VBox(10);
            TextField titleField = new TextField();
            titleField.setPromptText("Title");
            TextField authorField = new TextField();
            authorField.setPromptText("Author");
            vbox.getChildren().addAll(new Label("Title:"), titleField, new Label("Author:"), authorField);

            dialog.getDialogPane().setContent(vbox);

            ButtonType addBtn = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addBtn, ButtonType.CANCEL);

            dialog.setResultConverter(bt -> {
                if (bt == addBtn) {
                    String title = titleField.getText() == null ? "" : titleField.getText().trim();
                    String author = authorField.getText() == null ? "" : authorField.getText().trim();

                    // validation
                    if (title.isEmpty() || author.isEmpty()) {
                        showAlert("Validation", "Title and Author are required.");
                        return null;
                    }
                    return new Book(title, author); //error
                   
                }
                return null;
            });

            dialog.showAndWait().ifPresent(b -> {
                try {
                    bookDAO.insert(b);   // store in DB
                    loadBooks();         // reload table from DB
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showAlert("Database error", ex.getMessage());
                }
            });
        });

        // Issue Book action (DB UPDATE availability=0)
        issueBook.setOnAction(e -> {
            Book b = table.getSelectionModel().getSelectedItem();
            if (b == null) return;

            if (!b.isAvailable()) {
                showAlert("Info", "That book is already issued.");
                return;
            }

            try {
                bookDAO.updateAvailability(b.getId(), false);
                loadBooks();
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Database error", ex.getMessage());
            }
        });

        // Return Book action (DB UPDATE availability=1)
        returnBook.setOnAction(e -> {
            Book b = table.getSelectionModel().getSelectedItem();
            if (b == null) return;

            if (b.isAvailable()) {
                showAlert("Info", "That book is already available.");
                return;
            }

            try {
                bookDAO.updateAvailability(b.getId(), true);
                loadBooks();
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Database error", ex.getMessage());
            }
        });

        HBox controls = new HBox(10, addBook, issueBook, returnBook);

        // Put search bar ABOVE the table
        VBox centerLayout = new VBox(10, searchField, table);

        setCenter(centerLayout);
        setBottom(controls);

        // Load from DB at startup
        loadBooks();
    }

    private void loadBooks() {
        masterData.setAll(bookDAO.findAll());
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}

