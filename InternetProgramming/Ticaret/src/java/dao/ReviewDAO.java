package dao;

import entities.Product;
import entities.Review;
import entities.User;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReviewDAO {

    private static final Logger LOGGER = Logger.getLogger(ReviewDAO.class.getName());
    private static ProductDAO productDAO = new ProductDAO();
    private static UserDAO userDAO = new UserDAO();
    
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM Reviews")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Review review = new Review();
                review.setReviewId(resultSet.getInt("review_id"));
                
                User newUser=new User();
                newUser=userDAO.getUserById(resultSet.getInt("user_id"));
                
                review.setUser(newUser);

                Product newProduct = new Product();
                newProduct = productDAO.getProductById(resultSet.getInt("product_id"));
                review.setProduct(newProduct);

                review.setRating(resultSet.getInt("rating"));
                review.setComment(resultSet.getString("comment"));
                review.setReviewDate(resultSet.getTimestamp("review_date"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching reviews", e);
        }
        return reviews;
    }

    public void addReview(Review review) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO Reviews (user_id, product_id, rating, comment) VALUES (?, ?, ?, ?)")) {
            statement.setInt(1, review.getUserId());
            statement.setInt(2, review.getProductId());
            statement.setInt(3, review.getRating());
            statement.setString(4, review.getComment());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding review", e);
        }
    }

    public void deleteReview(int reviewId) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM Reviews WHERE review_id = ?")) {
            statement.setInt(1, reviewId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting review with id: " + reviewId, e);
        }
    }

    public void updateReview(Review review) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("UPDATE Reviews SET user_id = ?, product_id = ?, rating = ?, comment = ? WHERE review_id = ?")) {
            statement.setInt(1, review.getUserId());
            statement.setInt(2, review.getProductId());
            statement.setInt(3, review.getRating());
            statement.setString(4, review.getComment());
            statement.setInt(5, review.getReviewId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating review with id: " + review.getReviewId(), e);
        }
    }

    public List<Review> getReviewsWithPagination(int offset, int limit) {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM Reviews LIMIT ? OFFSET ?")) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Review review = new Review();
                review.setReviewId(resultSet.getInt("review_id"));
                
                User newUser=new User();
                newUser=userDAO.getUserById(resultSet.getInt("user_id"));
                
                review.setUser(newUser);

                Product newProduct = new Product();
                newProduct = productDAO.getProductById(resultSet.getInt("product_id"));
                review.setProduct(newProduct);

                review.setRating(resultSet.getInt("rating"));
                review.setComment(resultSet.getString("comment"));
                review.setReviewDate(resultSet.getTimestamp("review_date"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching reviews with pagination", e);
        }
        return reviews;
    }
    public List<Review> getReviewsByProductId(int productId) {
    List<Review> reviews = new ArrayList<>();
    try (Connection connection = DBConnection.getConnection(); 
         PreparedStatement statement = connection.prepareStatement("SELECT * FROM Reviews WHERE product_id = ?")) {
        statement.setInt(1, productId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Review review = new Review();
            review.setReviewId(resultSet.getInt("review_id"));
            
            User newUser = userDAO.getUserById(resultSet.getInt("user_id"));
            review.setUser(newUser);

            Product newProduct = productDAO.getProductById(resultSet.getInt("product_id"));
            review.setProduct(newProduct);

            review.setRating(resultSet.getInt("rating"));
            review.setComment(resultSet.getString("comment"));
            review.setReviewDate(resultSet.getTimestamp("review_date"));
            reviews.add(review);
        }
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Error occurred while fetching reviews for product with id: " + productId, e);
    }
    return reviews;
}

}
