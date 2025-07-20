package controller;

import dao.UrunDAO;
import entities.Urun;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UrunController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(UrunController.class.getName());

    private List<Urun> urunler;
    private Urun selectedUrun;
    private Urun newUrun;

    private final UrunDAO urunDAO;

    public UrunController() {
        urunDAO = new UrunDAO();
    }

    @PostConstruct
    public void init() {
        refreshUrunler();
        selectedUrun = new Urun();
        newUrun = new Urun();
    }

    public List<Urun> getUrunler() {
        return urunler;
    }

    public void deleteUrun(Long urunId) {
        try {
            urunDAO.deleteUrun(urunId);
            refreshUrunler();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting urun with id: " + urunId, ex);
        }
    }

    public void updateUrun() {
        try {
            urunDAO.updateUrun(selectedUrun);
            refreshUrunler();
            selectedUrun = new Urun(); // Güncelleme sonrasında formu temizle
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating urun with id: " + selectedUrun.getId(), ex);
        }
    }

    public void addUrun() {
        try {
            urunDAO.insertUrun(newUrun);
            refreshUrunler();
            newUrun = new Urun();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding urun", ex);
        }
    }

    public void editUrun(Urun urun) {
        selectedUrun = urun;
    }

    private void refreshUrunler() {
        try {
            urunler = urunDAO.getAllUrunler();
            LOGGER.info("Urun list size: " + urunler.size()); // Urun listesi boyutunu logla
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching urunler", ex);
        }
    }

    public Urun getSelectedUrun() {
        return selectedUrun;
    }

    public void setSelectedUrun(Urun selectedUrun) {
        this.selectedUrun = selectedUrun;
    }

    public Urun getNewUrun() {
        return newUrun;
    }

    public void setNewUrun(Urun newUrun) {
        this.newUrun = newUrun;
    }
}
