package beans;

import dao.ProductCategoryMappingDAO;
import entities.ProductCategoryMapping;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProductCategoryMappingController implements Serializable {

    private ProductCategoryMappingDAO productCategoryMappingDAO;

    private List<ProductCategoryMapping> productCategoryMappings;
    private int currentPage = 1; // Şu anki sayfa numarası
    private int totalPages; // Toplam sayfa sayısı

    @PostConstruct
    public void init() {
        productCategoryMappingDAO = new ProductCategoryMappingDAO();
        loadProductCategoryMappings();
    }

    public void loadProductCategoryMappings() {
        productCategoryMappings = productCategoryMappingDAO.getMappingsWithPagination((currentPage - 1) * 10, 10); // 10 öğe sayfa başına
        calculateTotalPages();
    }

    private void calculateTotalPages() {
        int totalItems = productCategoryMappingDAO.getAllMappings().size();
        totalPages = (int) Math.ceil((double) totalItems / 10); // 10 öğe sayfa başına
    }

    public void nextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            loadProductCategoryMappings();
        }
    }

    public void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            loadProductCategoryMappings();
        }
    }

    // Getter ve setter metotları
    public List<ProductCategoryMapping> getProductCategoryMappings() {
        return productCategoryMappings;
    }

    public void setProductCategoryMappings(List<ProductCategoryMapping> productCategoryMappings) {
        this.productCategoryMappings = productCategoryMappings;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
