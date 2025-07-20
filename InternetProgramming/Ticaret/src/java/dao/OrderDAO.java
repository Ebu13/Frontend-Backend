package dao;

import entities.Order;
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

public class OrderDAO {

    private static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
    private static UserDAO userDAO = new UserDAO();

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM Orders")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = mapResultSetToOrder(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching orders", e);
        }
        return orders;
    }

    public Order getOrderById(int orderId) {
        Order order = null;
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM Orders WHERE order_id = ?")) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = mapResultSetToOrder(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching order with id: " + orderId, e);
        }
        return order;
    }

    public void addOrder(Order order) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO Orders (user_id, total_amount) VALUES (?, ?)")) {
            statement.setInt(1, order.getUserId());
            statement.setBigDecimal(2, order.getTotalAmount());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding order", e);
        }
    }

    public void deleteOrder(int orderId) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM Orders WHERE order_id = ?")) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting order with id: " + orderId, e);
        }
    }

    public void updateOrder(Order order) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("UPDATE Orders SET user_id = ?, total_amount = ? WHERE order_id = ?")) {
            statement.setInt(1, order.getUserId());
            statement.setBigDecimal(2, order.getTotalAmount());
            statement.setInt(3, order.getOrderId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating order with id: " + order.getOrderId(), e);
        }
    }

    public List<Order> getOrdersWithPagination(int offset, int limit) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM Orders LIMIT ? OFFSET ?")) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = mapResultSetToOrder(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching orders with pagination", e);
        }
        return orders;
    }

    private Order mapResultSetToOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setOrderId(resultSet.getInt("order_id"));
        User newUser = userDAO.getUserById(resultSet.getInt("user_id"));
        order.setUser(newUser);
        order.setOrderDate(resultSet.getTimestamp("order_date"));
        order.setTotalAmount(resultSet.getBigDecimal("total_amount"));
        return order;
    }
}
