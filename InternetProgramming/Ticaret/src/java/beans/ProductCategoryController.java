package beans;

import dao.ProductCategoryDAO;
import entities.ProductCategory;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class ProductCategoryController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ProductCategoryController.class.getName());

    private List<ProductCategory> categories;
    private ProductCategory newCategory;
    private ProductCategory selectedCategory;

    private final ProductCategoryDAO categoryDAO;

    public ProductCategoryController() {
        categoryDAO = new ProductCategoryDAO();
    }

    @PostConstruct
    public void init() {
        refreshCategories();
        newCategory = new ProductCategory();
        selectedCategory = null;
    }

    public List<ProductCategory> getCategories() {
        return categories;
    }

    public void addCategory() {
        try {
            categoryDAO.addCategory(newCategory);
            refreshCategories();
            newCategory = new ProductCategory();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding product category", ex);
        }
    }

    public void deleteCategory(int categoryId) {
        try {
            categoryDAO.deleteCategory(categoryId);
            refreshCategories();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting product category with id: " + categoryId, ex);
        }
    }

    public void updateCategory() {
        try {
            categoryDAO.updateCategory(selectedCategory);
            refreshCategories();
            selectedCategory = null;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating product category with id: " + selectedCategory.getCategoryId(), ex);
        }
    }

    private void refreshCategories() {
        try {
            categories = categoryDAO.getAllCategories();
            LOGGER.info("Product category list size: " + categories.size());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching product categories", ex);
        }
    }

    public ProductCategory getNewCategory() {
        return newCategory;
    }

    public void setNewCategory(ProductCategory newCategory) {
        this.newCategory = newCategory;
    }

    public ProductCategory getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(ProductCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
}
