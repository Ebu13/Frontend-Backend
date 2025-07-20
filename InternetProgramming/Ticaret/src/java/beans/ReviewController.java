package beans;

import dao.ReviewDAO;
import dao.UserDAO;
import entities.Product;
import entities.Review;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class ReviewController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ReviewController.class.getName());

    @Inject
    private LoginBean loginBean;

    @Inject
    private ProductController pController;

    private List<Review> reviews;
    private List<Review> fbReviews;
    private Review selectedReview;
    private Review newReview;

    private final ReviewDAO reviewDAO;
    private final UserDAO userDAO;

    public ReviewController() {
        reviewDAO = new ReviewDAO();
        userDAO = new UserDAO();
    }

    @PostConstruct
    public void init() {
        refreshReviews();
        selectedReview = new Review();
        newReview = new Review();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setFbReviews(List<Review> reviews) {
        this.fbReviews = reviews;
    }

    public List<Review> getFbReviews() {
        return fbReviews;
    }

    public void deleteReview(int reviewId) {
        try {
            reviewDAO.deleteReview(reviewId);
            refreshReviews();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting review with id: " + reviewId, ex);
        }
    }

    public void updateReview() {
        try {
            reviewDAO.updateReview(selectedReview);
            refreshReviews();
            selectedReview = new Review(); // Güncelleme sonrasında formu temizle
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating review with id: " + selectedReview.getReviewId(), ex);
        }
    }

    public String addReview(Product product) {
        try {

            newReview.setProduct(product);
            newReview.setUser(loginBean.getLoggedInUser());
            reviewDAO.addReview(newReview);

            setfbReviews(reviewDAO.getReviewsByProductId(product.getProductId()));
            refreshReviews();
            newReview = new Review();
            return "productDetail?faces-redirect=true";
            

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding review", ex);
            return null;
        }
    }

    public void editReview(Review review) {
        selectedReview = review;
    }

    private void refreshReviews() {
        try {
            reviews = reviewDAO.getAllReviews();
            LOGGER.info("Review list size: " + reviews.size()); // Review listesinin boyutunu logla
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching reviews", ex);
        }
    }

    public Review getSelectedReview() {
        return selectedReview;
    }

    public void setSelectedReview(Review selectedReview) {
        this.selectedReview = selectedReview;
    }

    public Review getNewReview() {
        return newReview;
    }

    public void setNewReview(Review newReview) {
        this.newReview = newReview;
    }

    void setfbReviews(List<Review> reviewsByProductId) {
        this.fbReviews = reviewsByProductId;
    }
}
