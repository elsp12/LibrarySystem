package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import finalproject.Book;
import service.Library;

public class MainView extends BorderPane {

    private Library library = new Library();
    private ObservableList<Book> data = FXCollections.observableArrayList();

    public MainView() {
        // TableView to show books
        TableView<Book> table = new TableView<>(data);
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTitle()));
        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getAuthor()));
        TableColumn<Book, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(
                cell.getValue().isAvailable() ? "Available" : "Issued"));

        table.getColumns().addAll(titleCol, authorCol, statusCol);

        // Buttons
        Button addBook = new Button("Add Book");
        Button issueBook = new Button("Issue Book");
        Button returnBook = new Button("Return Book");

        // Add Book action
        addBook.setOnAction(e -> {
            // Simple dialog for input
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
                if (bt == addBtn) return new Book(titleField.getText(), authorField.getText());
                return null;
            });
            dialog.showAndWait().ifPresent(b -> {
                library.addBook(b);
                data.add(b);
            });
        });

        // Issue Book action
        issueBook.setOnAction(e -> {
            Book b = table.getSelectionModel().getSelectedItem();
            if (b != null && b.isAvailable()) {
                b.setAvailable(false);
                table.refresh();
                library.save();
            }
        });

        // Return Book action
        returnBook.setOnAction(e -> {
            Book b = table.getSelectionModel().getSelectedItem();
            if (b != null && !b.isAvailable()) {
                b.setAvailable(true);
                table.refresh();
                library.save();
            }
        });

        HBox controls = new HBox(10, addBook, issueBook, returnBook);
        setCenter(table);
        setBottom(controls);

        data.addAll(library.books);
    }
}
