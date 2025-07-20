package entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    public Product(int productId, User user, String productName, String description, BigDecimal price, Timestamp creationDate) {
        this.productId = productId;
        this.user = user;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.creationDate = creationDate;
    }

    public Product(User user, String productName, String description, BigDecimal price, Timestamp creationDate) {
        this.user = user;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.creationDate = creationDate;
    }

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        if (user != null) {
            return user.getUserId();
        } else {
            return 0; // veya başka bir varsayılan değer
        }
    }

    public void setUserId(int userId) {
        if (this.user == null) {
            this.user = new User(); // veya uygun bir şekilde yeni bir kullanıcı oluşturun
        }
        this.user.setUserId(userId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName() {
        if (user != null) {
            return user.getUsername();
        } else {
            return ""; // veya başka bir varsayılan değer
        }
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

}
