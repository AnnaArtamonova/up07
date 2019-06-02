import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.*;


public class DB extends Application {
    private static String[] arguments;

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    private final TableView<Book> tableBooks = new TableView<>();
    private final TableView<BookType> tableDeliveries = new TableView<>();
    private static final ObservableList<Book> dataBook = FXCollections.observableArrayList();
    private static final ObservableList<BookType> dataBookType = FXCollections.observableArrayList();
    final HBox hBox = new HBox(10);

    public static void main(String[] args) {
        connectionDB();
        CreateDB();
        launch(args);
        closeConnectionDB();
    }

    private static void connectionDB()
    {
        try {
            connection = null;
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:booktype.db");

            statement = connection.createStatement();
            String SQLSelect = "SELECT * from books";
            resultSet = statement.executeQuery(SQLSelect);

            while (resultSet.next()) {
                dataBook.add(new Book(resultSet.getString("bookID"), resultSet.getString("fulltitle"),
                        resultSet.getString("price"), resultSet.getString("year"),
                        resultSet.getString("authors"), resultSet.getString("genre")));
            }

            SQLSelect = "SELECT * from booktypes";
            resultSet = statement.executeQuery(SQLSelect);

            while (resultSet.next()) {
                dataBookType.add(new BookType(resultSet.getString("genreID"),
                        resultSet.getString("genre"), resultSet.getString("description")));
            }


        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void CreateDB()
    {
        try {
            statement.execute("CREATE TABLE if not exists 'booktypes' " +
                    "('genreID' INTEGER PRIMARY KEY AUTOINCREMENT,  'genre' text, 'description' text;");

            statement.execute("CREATE TABLE if not exists 'books' ('bookID' INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "'fulltitle' text, 'price' INT, 'year' text, 'authors' text," +
                    " 'genre' INTEGER REFERENCES booktypes(genre, descripton) DEFERRABLE INITIALLY DEFERRED);");

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void start(Stage stage) {

        Scene scene = new Scene(new Group());
        stage.setTitle("Books Table");
        stage.setWidth(765);
        stage.setHeight(535);

        final Label label = new Label("Books Table");
        label.setFont(new Font("Arial", 20));


        tableBooks.setEditable(true);
        tableDeliveries.setEditable(true);

        TableColumn<Book, String> idCol = new TableColumn<>("Book_ID");
        idCol.setPrefWidth(30);
        idCol.setMaxWidth(30);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        idCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<Book, String> fulltitleCol =
                new TableColumn<>("Full_title");
        fulltitleCol.setPrefWidth(200);
        fulltitleCol.setMaxWidth(200);
        fulltitleCol.setCellValueFactory(new PropertyValueFactory<>("fulltitle"));
        fulltitleCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<Book, String> priceCol =
                new TableColumn<>("Price");
        priceCol.setPrefWidth(100);
        priceCol.setMaxWidth(100);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<Book, String> yearCol =
                new TableColumn<>("Year");
        yearCol.setPrefWidth(100);
        yearCol.setMaxWidth(100);
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        yearCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<Book, String> authorsCol =
                new TableColumn<>("Aythors");
        authorsCol.setPrefWidth(100);
        authorsCol.setMaxWidth(100);
        authorsCol.setCellValueFactory(new PropertyValueFactory<>("authors"));
        authorsCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<Book, String> genreCol = new TableColumn<>("Genre");
        genreCol.setPrefWidth(100);
        genreCol.setMaxWidth(100);
        genreCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Book, String> p) {
                        // p.getValue() returns the Person instance for a particular TableView row
                        return p.getValue().genreProperty();
                    }
                });


        tableBooks.setItems(dataBook);
        tableBooks.getColumns().addAll(idCol, fulltitleCol, priceCol, yearCol, authorsCol, genreCol);
        tableBooks.setMaxHeight(200);

        TableColumn<BookType, String> BookTypeCol =
                new TableColumn<>("Genre ID");
        BookTypeCol.setMinWidth(55);
        BookTypeCol.setPrefWidth(55);
        BookTypeCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<BookType, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<BookType, String> p) {
                        return p.getValue().idProperty();
                    }
                });

        TableColumn<BookType, String> GenreCol =
                new TableColumn<>("Genre");
        GenreCol.setMinWidth(125);
        GenreCol.setPrefWidth(125);
        GenreCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<BookType, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<BookType, String> p) {
                       return p.getValue().genreProperty();
                    }
                });


        TableColumn<BookType, String> DescriptionCol =
                new TableColumn<>("Description");
        DescriptionCol.setMinWidth(450);
        DescriptionCol.setPrefWidth(450);
        DescriptionCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<BookType, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<BookType, String> p) {
                       return p.getValue().descriptionProperty();
                    }
                });

        tableDeliveries.setItems(dataBookType);
        tableDeliveries.getColumns().addAll(BookTypeCol, GenreCol, DescriptionCol);
        tableDeliveries.setMaxHeight(200);


        final TextField addFullTitle = new TextField();
        addFullTitle.setMaxWidth(111);
        addFullTitle.setPromptText("Full title");

        final TextField addPrice = new TextField();
        addPrice.setMaxWidth(111);
        addPrice.setPromptText("Price");

        final TextField addYear = new TextField();
        addYear.setMaxWidth(111);
        addYear.setPromptText("Year");

        final TextField addAuthors = new TextField();
        addAuthors.setMaxWidth(111);
        addAuthors.setPromptText("Authors");

        final TextField addGenre = new TextField();
        addGenre.setMaxWidth(111);
        addGenre.setPromptText("Genre");

        final TextField addDescription = new TextField();
        addDescription.setMaxWidth(120);
        addDescription.setPromptText("Description");

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            String genreID = contains(addGenre.getText(), addDescription.getText());

            if (genreID.equals("-1")) {
                genreID = "" + (dataBookType.size() + 1);
                dataBookType.add(new BookType(
                        //"Genre ID",
                        genreID,
                        addGenre.getText(),
                        addDescription.getText()));

                insert(addGenre.getText(), addDescription.getText());
            }
            String bookID = containsBook(addFullTitle.getText(), addPrice.getText(), addYear.getText(), addAuthors.getText(), addGenre.getText());
            if (bookID.equals("-1")){
                bookID = "" + (dataBook.size() + 1);
                dataBook.add(new Book(
                    //"BookID",
                    bookID,
                    addFullTitle.getText(),
                    addPrice.getText(),
                    addYear.getText(),
                    addAuthors.getText(),
                    addGenre.getText()));

                    insert(addFullTitle.getText(), addPrice.getText(), addYear.getText(),
                    addAuthors.getText(), genreID);
            }

            addFullTitle.clear();
            addPrice.clear();
            addYear.clear();
            addAuthors.clear();
            addGenre.clear();
            addDescription.clear();
        });

        hBox.getChildren().addAll(addFullTitle, addPrice, addYear, addAuthors, addGenre, addDescription, addButton);
        hBox.setSpacing(5);

        final VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, tableBooks, tableDeliveries, hBox);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

    }

    private void insert(String genre, String description) {
        String sql = "INSERT INTO booktype(genre, description) VALUES(?,?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, genre);
            pstmt.setString(2, description);

            pstmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void insert(String fulltitle, String price, String year, String authors, String genre) {
        String sql = "INSERT INTO books(fulltitle,price,year,authors,genre) VALUES(?,?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, fulltitle);
            pstmt.setString(2, price);
            pstmt.setString(3, year);
            pstmt.setString(4, authors);
            pstmt.setString(5, genre);

            pstmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private String containsBook(String fulltitle, String price, String year, String authors, String genre) {
        for (Book book : dataBook) {
            if (book.getGenre().equals(genre) && book.getFulltitle().equals(fulltitle) &&
                book.getPrice().equals(price) && book.getYear().equals(year) &&
                book.getAuthors().equals(authors)) {
                return book.getId();
            }
        }
        return "-1";
    }
    
    private String contains(String genre, String description) {
        for (BookType booktypes : dataBookType) {
            if (booktypes.getGenre().equals(genre) && booktypes.getDescription().equals(description)) {
                return booktypes.getId();
            }
        }
        return "-1";
    }


    private static void closeConnectionDB(){
        try {
            resultSet.close();
            connection.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}