package controller;

import dao.MusteriDAO;
import entities.Musteri;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class MusteriController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(MusteriController.class.getName());

    private List<Musteri> musteriler;
    private Musteri selectedMusteri;
    private Musteri newMusteri;

    private final MusteriDAO musteriDAO;

    public MusteriController() {
        musteriDAO = new MusteriDAO();
    }

    @PostConstruct
    public void init() {
        refreshMusteriler();
        selectedMusteri = new Musteri();
        newMusteri = new Musteri();
    }

    public List<Musteri> getMusteriler() {
        return musteriler;
    }

    public void deleteMusteri(Long musteriId) {
        try {
            musteriDAO.deleteMusteri(musteriId);
            refreshMusteriler();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting musteri with id: " + musteriId, ex);
        }
    }

    public void updateMusteri() {
        try {
            musteriDAO.updateMusteri(selectedMusteri);
            refreshMusteriler();
            selectedMusteri = new Musteri(); // Güncelleme sonrasında formu temizle
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating musteri with id: " + selectedMusteri.getId(), ex);
        }
    }

    public void addMusteri() {
        try {
            musteriDAO.insertMusteri(newMusteri);
            refreshMusteriler();
            newMusteri = new Musteri();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding musteri", ex);
        }
    }

    public void editMusteri(Musteri musteri) {
        selectedMusteri = musteri;
    }

    private void refreshMusteriler() {
        try {
            musteriler = musteriDAO.getAllMusteriler();
            LOGGER.info("Musteri list size: " + musteriler.size()); // Musteri listesi boyutunu logla
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching musteriler", ex);
        }
    }

    public Musteri getSelectedMusteri() {
        return selectedMusteri;
    }

    public void setSelectedMusteri(Musteri selectedMusteri) {
        this.selectedMusteri = selectedMusteri;
    }

    public Musteri getNewMusteri() {
        return newMusteri;
    }

    public void setNewMusteri(Musteri newMusteri) {
        this.newMusteri = newMusteri;
    }
}
