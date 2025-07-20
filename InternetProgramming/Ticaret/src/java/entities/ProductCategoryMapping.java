package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ProductCategoryMapping")
public class ProductCategoryMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private ProductCategory category;

    public ProductCategoryMapping(Product product, ProductCategory category) {
        this.product = product;
        this.category = category;
    }

    public ProductCategoryMapping() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public int getCategoryId() {
        return this.category.getCategoryId();
    }

    public int getProductId() {
        return this.product.getProductId();
    }
    
    
}
