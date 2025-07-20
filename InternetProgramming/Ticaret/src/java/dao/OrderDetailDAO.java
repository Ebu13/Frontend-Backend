package dao;

import entities.Order;
import entities.OrderDetail;
import entities.Product;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDetailDAO {

    private static final Logger LOGGER = Logger.getLogger(OrderDetailDAO.class.getName());

    private static OrderDAO orderDAO = new OrderDAO();
    private static ProductDAO productDAO = new ProductDAO();

    public List<OrderDetail> getAllOrderDetails() {
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM OrderDetails")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderDetail orderDetail = mapResultSetToOrderDetail(resultSet);
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching order details", e);
        }
        return orderDetails;
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO OrderDetails (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)")) {
            statement.setInt(1, orderDetail.getOrderId());
            statement.setInt(2, orderDetail.getProductId());
            statement.setInt(3, orderDetail.getQuantity());
            statement.setBigDecimal(4, orderDetail.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding order detail", e);
        }
    }

    public void deleteOrderDetail(int detailId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM OrderDetails WHERE detail_id = ?")) {
            statement.setInt(1, detailId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting order detail with id: " + detailId, e);
        }
    }

    public void updateOrderDetail(OrderDetail orderDetail) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE OrderDetails SET order_id = ?, product_id = ?, quantity = ?, price = ? WHERE detail_id = ?")) {
            statement.setInt(1, orderDetail.getOrderId());
            statement.setInt(2, orderDetail.getProductId());
            statement.setInt(3, orderDetail.getQuantity());
            statement.setBigDecimal(4, orderDetail.getPrice());
            statement.setInt(5, orderDetail.getDetailId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating order detail with id: " + orderDetail.getDetailId(), e);
        }
    }

    public List<OrderDetail> getOrderDetailsWithPagination(int offset, int limit) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM OrderDetails LIMIT ? OFFSET ?")) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderDetail orderDetail = mapResultSetToOrderDetail(resultSet);
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching order details with pagination", e);
        }
        return orderDetails;
    }

    public int countOrderDetails() {
        int count = 0;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM OrderDetails")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while counting order details", e);
        }
        return count;
    }

    public List<OrderDetail> getOrderDetailsByUserId(int userId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM OrderDetails WHERE order_id IN (SELECT order_id FROM Orders WHERE user_id = ?)")) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderDetail orderDetail = mapResultSetToOrderDetail(resultSet);
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching order details by user id: " + userId, e);
        }
        return orderDetails;
    }

    private OrderDetail mapResultSetToOrderDetail(ResultSet resultSet) throws SQLException {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(resultSet.getInt("detail_id"));

        Order newOrder = orderDAO.getOrderById(resultSet.getInt("order_id"));
        orderDetail.setOrder(newOrder);

        Product newProduct = productDAO.getProductById(resultSet.getInt("product_id"));
        orderDetail.setProduct(newProduct);

        orderDetail.setQuantity(resultSet.getInt("quantity"));
        orderDetail.setPrice(resultSet.getBigDecimal("price"));

        return orderDetail;
    }
}
