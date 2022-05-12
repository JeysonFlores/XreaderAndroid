package site.xreader.xreaderandroid.models;

public class Volume {

    public int id;
    public String  name;
    public String link;
    public String image_path;

    public Volume(int id, String name, String link, String image_path) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.image_path = image_path;
    }
}
