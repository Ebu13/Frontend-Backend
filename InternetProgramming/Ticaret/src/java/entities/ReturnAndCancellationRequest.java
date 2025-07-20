package entities;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ReturnAndCancellationRequests")
public class ReturnAndCancellationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private int requestId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "request_date")
    private Timestamp requestDate;

    @Column(name = "request_type", nullable = false)
    private String requestType;

    @Column(name = "status", nullable = false)
    private String status;

    public ReturnAndCancellationRequest(int requestId, Order order, User user, Timestamp requestDate, String requestType, String status) {
        this.requestId = requestId;
        this.order = order;
        this.user = user;
        this.requestDate = requestDate;
        this.requestType = requestType;
        this.status = status;
    }

    public ReturnAndCancellationRequest(Order order, User user, Timestamp requestDate, String requestType, String status) {
        this.order = order;
        this.user = user;
        this.requestDate = requestDate;
        this.requestType = requestType;
        this.status = status;
    }

    public ReturnAndCancellationRequest() {
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        if (this.user != null) {
            return this.user.getUserId();
        } else {
            return 0; // Veya uygun bir hata durumu işlemini gerçekleştirin
        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getOrderId() {
        if (this.order != null) {
            return this.order.getOrderId();
        } else {
            return 0; // Veya uygun bir hata durumu işlemini gerçekleştirin
        }
    }

}
