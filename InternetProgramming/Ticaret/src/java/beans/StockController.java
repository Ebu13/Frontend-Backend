package beans;

import dao.StockDAO;
import entities.Stock;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class StockController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(StockController.class.getName());

    private List<Stock> stocks;
    private Stock selectedStock;
    private Stock newStock;
    private int pageNumber = 1;
    private int pageSize = 10; // Sayfa başına görüntülenecek stok sayısı

    private final StockDAO stockDAO;

    public StockController() {
        stockDAO = new StockDAO();
    }

    @PostConstruct
    public void init() {
        refreshStocks();
        selectedStock = new Stock();
        newStock = new Stock();
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void loadStocks() {
        int offset = (pageNumber - 1) * pageSize;
        stocks = stockDAO.getStocksWithPagination(offset, pageSize);
    }

    public void nextPage() {
        pageNumber++;
        loadStocks();
    }

    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
            loadStocks();
        }
    }

    public void deleteStock(int stockId) {
        try {
            stockDAO.deleteStock(stockId);
            refreshStocks();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting stock with id: " + stockId, ex);
        }
    }

    public void updateStock() {
        try {
            stockDAO.updateStock(selectedStock);
            refreshStocks();
            selectedStock = new Stock(); // Güncelleme sonrasında formu temizle
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating stock with id: " + selectedStock.getStockId(), ex);
        }
    }

    public void addStock() {
        try {
            stockDAO.addStock(newStock);
            refreshStocks();
            newStock = new Stock();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding stock", ex);
        }
    }

    public void editStock(Stock stock) {
        selectedStock = stock;
    }

    private void refreshStocks() {
        try {
            loadStocks();
            LOGGER.info("Stock list size: " + stocks.size()); // Stok listesi boyutunu logla
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching stocks", ex);
        }
    }

    public Stock getSelectedStock() {
        return selectedStock;
    }

    public void setSelectedStock(Stock selectedStock) {
        this.selectedStock = selectedStock;
    }

    public Stock getNewStock() {
        return newStock;
    }

    public void setNewStock(Stock newStock) {
        this.newStock = newStock;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
