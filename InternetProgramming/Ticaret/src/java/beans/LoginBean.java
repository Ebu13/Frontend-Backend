package beans;

import dao.UserDAO;
import entities.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(LoginBean.class.getName());

    private String username;
    private String password;
    private User loggedInUser;
    private boolean editMode = false; // Düzenleme modu kontrolü

    public String login() {
        UserDAO userDAO = new UserDAO();
        if (userDAO.login(username, password)) {
            loggedInUser = userDAO.getUserByUsername(username);
            return navigateBasedOnRole();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", "Invalid username or password"));
            return null;
        }
    }

    private String navigateBasedOnRole() {
        if (loggedInUser == null) {
            return "/login/index.xhtml?faces-redirect=true";
        }

        switch (loggedInUser.getUserType()) {
            case "Admin":
                return "/admin/index.xhtml?faces-redirect=true";
            case "DomesticSupplier":
                return "/domesticsupplier/index.xhtml?faces-redirect=true";
            case "ForeignBuyer":
                return "/foreignbuyer/index.xhtml?faces-redirect=true";
            default:
                return "/client/index.xhtml?faces-redirect=true";
        }
    }

    public String logout() {
        loggedInUser = null;
        return "/client/index.xhtml?faces-redirect=true";
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    
    public String updateUser() {
        try {
            UserDAO userDAO = new UserDAO();
            userDAO.updateUser(loggedInUser);
        } catch (Exception ex) {
        }
        return null;
    }
    
    public void toggleEditMode() {
        editMode = !editMode;
    }

    // Getters ve setters burada

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
}
