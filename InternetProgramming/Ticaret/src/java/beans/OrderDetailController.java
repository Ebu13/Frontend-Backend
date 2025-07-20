package beans;

import dao.OrderDetailDAO;
import entities.OrderDetail;
import entities.Product;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class OrderDetailController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(OrderDetailController.class.getName());

    private List<OrderDetail> orderDetails;

    private OrderDetail selectedOrderDetail;

    private final OrderDetailDAO orderDetailDAO;

    private int currentPage;
    private int totalPages;
    private static final int PAGE_SIZE = 10;

    public OrderDetailController() {
        orderDetailDAO = new OrderDetailDAO();
        currentPage = 1;
    }

    @PostConstruct
    public void init() {
        refreshOrderDetails();
        calculateTotalPages();
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void refreshOrderDetails() {
        try {
            int offset = (currentPage - 1) * PAGE_SIZE;
            orderDetails = orderDetailDAO.getOrderDetailsWithPagination(offset, PAGE_SIZE);
            LOGGER.info("Order detail list size: " + orderDetails.size());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching order details", ex);
        }
    }

    public void deleteOrderDetail(int detailId) {
        try {
            orderDetailDAO.deleteOrderDetail(detailId);
            refreshOrderDetails();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting order detail with id: " + detailId, ex);
        }
    }

    public void updateOrderDetail() {
        try {
            orderDetailDAO.updateOrderDetail(selectedOrderDetail);
            refreshOrderDetails();
            selectedOrderDetail = new OrderDetail();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating order detail with id: " + selectedOrderDetail.getDetailId(), ex);
        }
    }

    public void addOrderDetail(Product product) {
        try {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(selectedOrderDetail.getQuantity());

            orderDetailDAO.addOrderDetail(orderDetail);
            refreshOrderDetails();

            selectedOrderDetail = new OrderDetail(); // Formu temizle
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding order detail", ex);
        }
    }

    public OrderDetail getSelectedOrderDetail() {
        return selectedOrderDetail;
    }

    public void setSelectedOrderDetail(OrderDetail selectedOrderDetail) {
        this.selectedOrderDetail = selectedOrderDetail;
    }

    public void nextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            refreshOrderDetails();
        }
    }

    public void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            refreshOrderDetails();
        }
    }

    private void calculateTotalPages() {
        int totalRecords = orderDetailDAO.countOrderDetails();
        totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
