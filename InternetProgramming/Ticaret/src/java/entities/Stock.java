package entities;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private int stockId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public Stock(int stockId, Product product, int quantity, Timestamp lastUpdate) {
        this.stockId = stockId;
        this.product = product;
        this.quantity = quantity;
        this.lastUpdate = lastUpdate;
    }

    public Stock(Product product, int quantity, Timestamp lastUpdate) {
        this.product = product;
        this.quantity = quantity;
        this.lastUpdate = lastUpdate;
    }

    
    public Stock() {
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductId() {
        return this.product != null ? this.product.getProductId() : 0;
    }
    
    public String getProductName() {
        return this.product != null ? this.product.getProductName() : "";
    }
    
}
