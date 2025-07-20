package entity;


public class Film {
    private int id;
    private String title;
    private String category;
    private String review;

    public Film() {
    }

    public Film(int id, String title, String category, String review) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.review = review;
    }

    public Film(String title, String category, String review) {
        this.title = title;
        this.category = category;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
    
    
    
}
