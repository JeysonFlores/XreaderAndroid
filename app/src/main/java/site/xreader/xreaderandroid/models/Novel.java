package site.xreader.xreaderandroid.models;

public class Novel {

    public int id;
    public String name;
    public String description;
    public String author;
    public String image_path;
    public int publishing_year;

    public Novel(int id, String name, String description, String author, String image_path, int publishing_year) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.image_path = image_path;
        this.publishing_year = publishing_year;
    }
}
