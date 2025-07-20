package beans;

import dao.ReturnAndCancellationRequestDAO;
import entities.ReturnAndCancellationRequest;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class ReturnAndCancellationRequestController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ReturnAndCancellationRequestController.class.getName());

    private List<ReturnAndCancellationRequest> requests;
    private ReturnAndCancellationRequest selectedRequest;
    private ReturnAndCancellationRequest newRequest;

    private final ReturnAndCancellationRequestDAO requestDAO;

    public ReturnAndCancellationRequestController() {
        requestDAO = new ReturnAndCancellationRequestDAO();
    }

    @PostConstruct
    public void init() {
        refreshRequests();
        selectedRequest = new ReturnAndCancellationRequest();
        newRequest = new ReturnAndCancellationRequest();
    }

    public List<ReturnAndCancellationRequest> getRequests() {
        return requests;
    }

    public void deleteRequest(int requestId) {
        try {
            requestDAO.deleteRequest(requestId);
            refreshRequests();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting request with id: " + requestId, ex);
        }
    }

    public void updateRequest() {
        try {
            requestDAO.updateRequest(selectedRequest);
            refreshRequests();
            selectedRequest = new ReturnAndCancellationRequest(); // Güncelleme sonrasında formu temizle
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating request with id: " + selectedRequest.getRequestId(), ex);
        }
    }

    public void addRequest() {
        try {
            requestDAO.addRequest(newRequest);
            refreshRequests();
            newRequest = new ReturnAndCancellationRequest();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding request", ex);
        }
    }

    public void editRequest(ReturnAndCancellationRequest request) {
        selectedRequest = request;
    }

    private void refreshRequests() {
        try {
            requests = requestDAO.getAllRequests();
            LOGGER.info("Request list size: " + requests.size()); // İstek listesi boyutunu logla
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching requests", ex);
            requests = new ArrayList<>(); // Hata durumunda listeyi boş bir liste ile başlat
        }
    }

    public ReturnAndCancellationRequest getSelectedRequest() {
        return selectedRequest;
    }

    public void setSelectedRequest(ReturnAndCancellationRequest selectedRequest) {
        this.selectedRequest = selectedRequest;
    }

    public ReturnAndCancellationRequest getNewRequest() {
        return newRequest;
    }

    public void setNewRequest(ReturnAndCancellationRequest newRequest) {
        this.newRequest = newRequest;
    }
}
