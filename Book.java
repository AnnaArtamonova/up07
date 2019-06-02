import javafx.beans.property.SimpleStringProperty;

public class Book {

    private final SimpleStringProperty id;
    private final SimpleStringProperty fulltitle;
    private final SimpleStringProperty price;
    private final SimpleStringProperty year;
    private final SimpleStringProperty authors;
    private final SimpleStringProperty genre;

    public Book(SimpleStringProperty id, SimpleStringProperty fulltitle,
                SimpleStringProperty price, SimpleStringProperty year,
                SimpleStringProperty authors, SimpleStringProperty genre) {
        this.id = id;
        this.fulltitle = fulltitle;
        this.price = price;
        this.year = year;
        this.authors = authors;
        this.genre = genre;
    }

    public Book(String id, String fulltitle,
                String price, String year,
                String authors, String genre) {
        this.id = new SimpleStringProperty(id);
        this.fulltitle = new SimpleStringProperty(fulltitle);
        this.price = new SimpleStringProperty(price);
        this.year = new SimpleStringProperty(year);
        this.authors = new SimpleStringProperty(authors);
        this.genre = new SimpleStringProperty(genre);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getFulltitle() {
        return fulltitle.get();
    }

    public SimpleStringProperty fulltitleProperty() {
        return fulltitle;
    }

    public void setFulltitle(String fulltitle) {
        this.fulltitle.set(fulltitle);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getYear() {
        return year.get();
    }

    public SimpleStringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public String getAuthors() {
        return authors.get();
    }

    public SimpleStringProperty authorsProperty() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors.set(authors);
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
}