package controller;

import dao.NotePadDAO;
import entity.NotePad;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class NotePadController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(NotePadController.class.getName());

    private List<NotePad> notePads;
    private NotePad selectedNotePad = new NotePad();
    private NotePad newNotePad = new NotePad();

    private NotePadDAO notePadDAO;

    @PostConstruct
    public void init() {
        notePadDAO = new NotePadDAO();
        refreshNotePads();
    }

    public List<NotePad> getNotePads() {
        return notePads;
    }

    public void deleteNotePad(int id) {
        try {
            notePadDAO.deleteNotePad(id);
            refreshNotePads();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting note pad with id: " + id, ex);
        }
    }

    public void updateNotePad() {
        try {
            notePadDAO.updateNotePad(selectedNotePad);
            selectedNotePad = new NotePad(); 
            refreshNotePads();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating note pad with id: " + selectedNotePad.getId(), ex);
        }
    }

    public void addNotePad() {
        try {
            notePadDAO.addNotePad(newNotePad);
            newNotePad = new NotePad();
            refreshNotePads();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding note pad", ex);
        }
    }

    public void editNotePad(NotePad notePad) {
        selectedNotePad = notePad;
    }

    private void refreshNotePads() {
        try {
            notePads = notePadDAO.getAllNotePads();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching note pads", ex);
        }
    }

    // Getters ve setters
    public NotePad getSelectedNotePad() {
        return selectedNotePad;
    }

    public void setSelectedNotePad(NotePad selectedNotePad) {
        this.selectedNotePad = selectedNotePad;
    }

    public NotePad getNewNotePad() {
        return newNotePad;
    }

    public void setNewNotePad(NotePad newNotePad) {
        this.newNotePad = newNotePad;
    }
}
