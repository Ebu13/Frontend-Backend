package dao;

import entities.Product;
import entities.Stock;
import java.sql.*;
import util.DBConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockDAO {

    private static final Logger LOGGER = Logger.getLogger(StockDAO.class.getName());
    private static ProductDAO productDAO = new ProductDAO();

    public List<Stock> getAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM Stocks")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Stock stock = new Stock();
                stock.setStockId(resultSet.getInt("stock_id"));

                Product newProduct = new Product();
                newProduct = productDAO.getProductById(resultSet.getInt("product_id"));
                stock.setProduct(newProduct);

                stock.setQuantity(resultSet.getInt("quantity"));
                stock.setLastUpdate(resultSet.getTimestamp("last_update"));
                stocks.add(stock);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching stocks", e);
        }
        return stocks;
    }

    public void addStock(Stock stock) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO Stocks (product_id, quantity) VALUES (?, ?)")) {
            statement.setInt(1, stock.getProductId());
            statement.setInt(2, stock.getQuantity());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding stock", e);
        }
    }

    public void deleteStock(int stockId) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM Stocks WHERE stock_id = ?")) {
            statement.setInt(1, stockId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting stock with id: " + stockId, e);
        }
    }

    public void updateStock(Stock stock) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("UPDATE Stocks SET product_id = ?, quantity = ?, last_update = ? WHERE stock_id = ?")) {
            statement.setInt(1, stock.getProductId());
            statement.setInt(2, stock.getQuantity());
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // Güncel zamanı kullanarak Timestamp oluştur
            statement.setInt(4, stock.getStockId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating stock with id: " + stock.getStockId(), e);
        }
    }

    public List<Stock> getStocksWithPagination(int offset, int limit) {
        List<Stock> stocks = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM Stocks LIMIT ? OFFSET ?")) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Stock stock = new Stock();
                stock.setStockId(resultSet.getInt("stock_id"));
                Product newProduct = new Product();
                newProduct = productDAO.getProductById(resultSet.getInt("product_id"));
                stock.setProduct(newProduct);

                stock.setQuantity(resultSet.getInt("quantity"));
                stock.setLastUpdate(resultSet.getTimestamp("last_update"));
                stocks.add(stock);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching stocks with pagination", e);
        }
        return stocks;
    }

}
