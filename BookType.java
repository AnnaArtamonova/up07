import javafx.beans.property.SimpleStringProperty;

public class BookType {
    private final SimpleStringProperty genre_id;
    private final SimpleStringProperty genre;
    private SimpleStringProperty description;

    public BookType(SimpleStringProperty genre_id, SimpleStringProperty genre,  SimpleStringProperty description) {
        this.genre_id = genre_id;
        this.genre = genre;
        this.description = description;
    }

    public BookType(String genre_id, String genre,  String description) {
        this.genre_id = new SimpleStringProperty(genre_id);
        this.genre = new SimpleStringProperty(genre);
        this.description = new SimpleStringProperty(description);
    }

    public String getId() {
        return genre_id.get();
    }

    public SimpleStringProperty idProperty() {
        return genre_id;
    }

    public void setId(String genre_id) {
        this.genre_id.set(genre_id);
    }

    public String getGenre() {
        return genre.get();
    }

    public SimpleStringProperty genreProperty() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void descriptionGenre(String description) {
        this.description.set(description);
    }

}