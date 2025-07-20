package beans;

import dao.OrderDAO;
import entities.Order;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class OrderController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());

    private List<Order> orders;
    private Order selectedOrder;
    private Order newOrder;
    private int pageNumber = 1; // Sayfa numarası
    private int pageSize = 10; // Sayfa başına gösterilecek sipariş sayısı

    private final OrderDAO orderDAO;

    public OrderController() {
        orderDAO = new OrderDAO();
    }

    @PostConstruct
    public void init() {
        refreshOrders();
        selectedOrder = null; // Başlangıçta null olarak ayarlıyoruz
        newOrder = new Order();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void deleteOrder(int orderId) {
        try {
            orderDAO.deleteOrder(orderId);
            refreshOrders();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting order with id: " + orderId, ex);
        }
    }

    public void updateOrder() {
        try {
            orderDAO.updateOrder(selectedOrder);
            refreshOrders();
            selectedOrder = null; // Güncelleme sonrasında formu temizle
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating order with id: " + selectedOrder.getOrderId(), ex);
        }
    }

    public void addOrder() {
        try {
            orderDAO.addOrder(newOrder);
            refreshOrders();
            newOrder = new Order();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding order", ex);
        }
    }

    public void editOrder(Order order) {
        selectedOrder = order;
    }

    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
            refreshOrders();
        }
    }

    public void nextPage() {
        pageNumber++;
        refreshOrders();
    }

    private void refreshOrders() {
        try {
            int offset = (pageNumber - 1) * pageSize;
            orders = orderDAO.getOrdersWithPagination(offset, pageSize);
            LOGGER.info("Order list size: " + orders.size()); // Sipariş listesi boyutunu logla
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching orders", ex);
        }
    }

    public Order getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(Order selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public Order getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(Order newOrder) {
        this.newOrder = newOrder;
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
