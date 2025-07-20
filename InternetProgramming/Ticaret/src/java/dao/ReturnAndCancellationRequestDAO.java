package dao;

import entities.Order;
import entities.ReturnAndCancellationRequest;
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

public class ReturnAndCancellationRequestDAO {

    private static final Logger LOGGER = Logger.getLogger(ReturnAndCancellationRequestDAO.class.getName());

    private static UserDAO userDAO = new UserDAO();
    private static OrderDAO orderDAO = new OrderDAO();

    public List<ReturnAndCancellationRequest> getAllRequests() {
        List<ReturnAndCancellationRequest> requests = new ArrayList<>();
        String query = "SELECT * FROM ReturnAndCancellationRequests";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {

                User newUser = userDAO.getUserById(resultSet.getInt("user_id"));
                Order newOrder = orderDAO.getOrderById(resultSet.getInt("order_id"));
                newOrder.setUser(new User());
                newOrder.setUser(newUser);

                requests.add(new ReturnAndCancellationRequest(
                        resultSet.getInt("request_id"),
                        newOrder,
                        newUser,
                        resultSet.getTimestamp("request_date"),
                        resultSet.getString("request_type"),
                        resultSet.getString("status")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching requests", e);
            throw new RuntimeException(e);
        }
        return requests;
    }

    public void addRequest(ReturnAndCancellationRequest request) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO ReturnAndCancellationRequests (order_id, user_id, request_type) VALUES (?, ?, ?)")) {
            statement.setInt(1, request.getOrderId());
            statement.setInt(2, request.getUserId());
            statement.setString(3, request.getRequestType());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding request", e);
        }
    }

    public void deleteRequest(int requestId) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM ReturnAndCancellationRequests WHERE request_id = ?")) {
            statement.setInt(1, requestId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting request with id: " + requestId, e);
        }
    }

    public void updateRequest(ReturnAndCancellationRequest request) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("UPDATE ReturnAndCancellationRequests SET order_id = ?, user_id = ?, request_type = ?, status = ? WHERE request_id = ?")) {
            statement.setInt(1, request.getOrderId());
            statement.setInt(2, request.getUserId());
            statement.setString(3, request.getRequestType());
            statement.setString(4, request.getStatus());
            statement.setInt(5, request.getRequestId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating request with id: " + request.getRequestId(), e);
        }
    }

    public List<ReturnAndCancellationRequest> getRequestsWithPagination(int offset, int limit) {
        List<ReturnAndCancellationRequest> requests = new ArrayList<>();
        String query = "SELECT * FROM ReturnAndCancellationRequests LIMIT ? OFFSET ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User newUser = userDAO.getUserById(resultSet.getInt("user_id"));
                    Order newOrder = orderDAO.getOrderById(resultSet.getInt("order_id"));
                    newOrder.setUser(newUser);

                    requests.add(new ReturnAndCancellationRequest(
                            resultSet.getInt("request_id"),
                            newOrder,
                            newUser,
                            resultSet.getTimestamp("request_date"),
                            resultSet.getString("request_type"),
                            resultSet.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching requests with pagination", e);
            throw new RuntimeException(e);
        }
        return requests;
    }
}
