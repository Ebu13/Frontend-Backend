package beans;

import dao.ProductDAO;
import dao.ReviewDAO;
import entities.Product;
import entities.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class ProductController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());

    @Inject
    private ReviewController rwController;

    private ProductDAO productDAO = new ProductDAO();
    private ReviewDAO rwDAO = new ReviewDAO();
    private List<Product> products;
    private List<Product> userProducts;
    private Product newProduct = new Product();
    private Product selectedProduct;
    private String searchText;
    private List<Product> searchResults;

    // Pagination variables
    private int currentPage = 1;
    private int pageSize = 10;
    private int totalProducts;

    @PostConstruct
    public void init() {
        refreshProducts();
        newProduct = new Product();
    }

    public void addProduct() {
        try {
            newProduct.setCreationDate(new Timestamp(System.currentTimeMillis()));
            newProduct.setPrice(new BigDecimal("0.00"));

            productDAO.addProduct(newProduct);
            refreshProducts();
            newProduct = new Product();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding product", ex);
        }
    }
    // Pagination methods

    public List<Product> getPaginatedProducts() {
        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, products.size());
        return products.subList(start, end);
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) totalProducts / pageSize);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public void nextPage() {
        if (currentPage < getTotalPages()) {
            currentPage++;
            refreshProducts();
        }
    }

    public void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            refreshProducts();
        }
    }

    public void addUserProduct(int userId) {
        try {
            if (newProduct.getUser() == null) {
                newProduct.setUser(new User());
            }

            newProduct.getUser().setUserId(userId);
            productDAO.addProduct(newProduct);
            refreshUserProducts(userId);
            newProduct = new Product();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding user product", ex);
        }
    }

    public void deleteProduct(int productId) {
        try {
            productDAO.deleteProduct(productId);
            refreshProducts();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting product with id: " + productId, ex);
        }
    }

    public void updateProduct() {
        try {
            productDAO.updateProduct(selectedProduct);
            refreshProducts();
            selectedProduct = null;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating product with id: " + selectedProduct.getProductId(), ex);
        }
    }

    public void editProduct(Product product) {
        selectedProduct = product;
    }

    public List<Product> getProducts() {
        if (products == null) {
            refreshProducts();
        }
        return products;
    }

    public List<Product> getUserProducts() {
        if (selectedProduct != null && userProducts == null) {
            refreshUserProducts(selectedProduct.getUser().getUserId());
        }
        return userProducts;
    }

    public void refreshProducts() {
        try {
            products = productDAO.getAllProducts();
            totalProducts = products.size();
            LOGGER.info("Product list size: " + products.size());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching products", ex);
        }
    }

    public void refreshUserProducts(int userId) {
        try {
            userProducts = productDAO.getAllProductsForUser(userId);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching user products", ex);
        }
    }

    public void searchProducts() {
        try {
            searchResults = productDAO.searchProducts(searchText);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while searching products", ex);
        }
    }

    public String viewProduct(int productId) {
        try {
            selectedProduct = productDAO.getProductById(productId);
            rwController.setfbReviews(rwDAO.getReviewsByProductId(selectedProduct.getProductId()));
            return "productDetail.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while viewing product with id: " + productId, ex);
            return null;
        }
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<Product> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Product> searchResults) {
        this.searchResults = searchResults;
    }

    public Product getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Product newProduct) {
        this.newProduct = newProduct;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

}
