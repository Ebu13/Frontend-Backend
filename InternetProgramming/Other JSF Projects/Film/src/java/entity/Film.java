package entity;


public class Film{

    private int id;
    private String name;
    private String description;
    private String text;
    private String tur;
    private int duration;
    private int year;

    public Film(int id, String name, String description, String text, String tur, int duration, int year) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.text = text;
        this.tur = tur;
        this.duration = duration;
        this.year = year;
    }

    public Film(String name, String description, String text, String tur, int duration, int year) {
        this.name = name;
        this.description = description;
        this.text = text;
        this.tur = tur;
        this.duration = duration;
        this.year = year;
    }

    public Film() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
