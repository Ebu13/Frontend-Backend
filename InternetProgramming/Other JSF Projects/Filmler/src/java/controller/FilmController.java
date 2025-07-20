package controller;

import dao.FilmDAO;
import entity.Film;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class FilmController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(FilmController.class.getName());

    private List<Film> films;
    private Film selectedFilm = new Film();
    private Film newFilm = new Film();

    private FilmDAO filmDAO;

    @PostConstruct
    public void init() {
        filmDAO = new FilmDAO();
        refreshFilms();
    }

    public List<Film> getFilms() {
        return films;
    }

    public void deleteFilm(int id) {
        try {
            filmDAO.deleteFilm(id);
            refreshFilms();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting film with id: " + id, ex);
        }
    }

    public void updateFilm() {
        try {
            filmDAO.updateFilm(selectedFilm);
            selectedFilm = new Film();
            refreshFilms();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating film with id: " + selectedFilm.getId(), ex);
        }
    }

    public void addFilm() {
        try {
            filmDAO.addFilm(newFilm);
            newFilm = new Film();
            refreshFilms();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding film", ex);
        }
    }

    public void editFilm(Film film) {
        selectedFilm = film;
    }

    private void refreshFilms() {
        try {
            films = filmDAO.getAllFilms();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching films", ex);
        }
    }

    // Getters ve setters
    public Film getSelectedFilm() {
        return selectedFilm;
    }

    public void setSelectedFilm(Film selectedFilm) {
        this.selectedFilm = selectedFilm;
    }

    public Film getNewFilm() {
        return newFilm;
    }

    public void setNewFilm(Film newFilm) {
        this.newFilm = newFilm;
    }
}
