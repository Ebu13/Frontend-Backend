
package beans;


import dao.OrderDetailDAO;
import entities.OrderDetail;
import entities.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class MyOrdersController implements Serializable {

    private List<OrderDetail> orderDetails;
    private OrderDetailDAO orderDetailDAO;
    private int userId; // For input field in XHTML form

    @PostConstruct
    public void init() {
        orderDetailDAO = new OrderDetailDAO();
        // Initially load orders for current user (if needed)
        loadOrders();
    }

    public void filterOrders(User user) {
        orderDetails = orderDetailDAO.getOrderDetailsByUserId(user.getUserId());
    }

    private void loadOrders() {
        // Assuming you have a method to get currently logged in user's ID
        int userId = getCurrentUserId(); // Implement this method according to your application logic
        orderDetails = orderDetailDAO.getOrderDetailsByUserId(userId);
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Replace this method with your actual logic to get current user's ID
    private int getCurrentUserId() {
        // Implement this method to retrieve current user's ID from session or context
        // For demonstration, returning a sample user ID
        return 0; // Replace with your actual logic to get user ID
    }
}
