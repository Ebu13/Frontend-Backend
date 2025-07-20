package beans;

import dao.UserDAO;
import entities.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private List<User> users;
    private User selectedUser;
    private User newUser;
    private int pageNumber = 1;
    private int pageSize = 10; // Sayfa başına görüntülenecek kullanıcı sayısı

    private final UserDAO userDAO;

    public UserController() {
        userDAO = new UserDAO();
    }

    @PostConstruct
    public void init() {
        refreshUsers();
        selectedUser = new User();
        newUser = new User();
    }

    public void loadUsers() {
        int offset = (pageNumber - 1) * pageSize;
        users = userDAO.getUsersWithPagination(offset, pageSize);
    }

    public void nextPage() {
        pageNumber++;
        loadUsers();
    }

    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
            loadUsers();
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void deleteUser(int userId) {
        try {
            userDAO.deleteUser(userId);
            refreshUsers();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting user with id: " + userId, ex);
        }
    }

    public void updateUser() {
        try {
            userDAO.updateUser(selectedUser);
            refreshUsers();
            selectedUser = new User(); // Güncelleme sonrasında formu temizle
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating user with id: " + selectedUser.getUserId(), ex);
        }
    }
    
    public String register(){
        addUser();
        return "/client/index?faces-redirect=true";
    }

    public void addUser() {
        try {
            // Set registration date as current timestamp
            newUser.setRegistrationDate(new Timestamp(System.currentTimeMillis()));

            // Kullanıcı türünü ekrana yazdır
            LOGGER.info("User type: " + newUser.getUserType());

            userDAO.addUser(newUser);
            refreshUsers();
            newUser = new User(); // Formu temizle


        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding user", ex);
        }
    }

    public void editUser(User user) {
        selectedUser = user;
    }

    private void refreshUsers() {
        try {
            loadUsers();
            LOGGER.info("User list size: " + users.size()); // Kullanıcı listesi boyutunu logla
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching users", ex);
        }
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
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
