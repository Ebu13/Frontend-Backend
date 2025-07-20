package dao;

import entity.Film;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilmDAO {

    private static final Logger LOGGER = Logger.getLogger(FilmDAO.class.getName());

    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM films ORDER BY id ASC")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Film film = new Film();
                film.setId(resultSet.getInt("id"));
                film.setName(resultSet.getString("name"));
                film.setDescription(resultSet.getString("description"));
                film.setText(resultSet.getString("text"));
                film.setTur(resultSet.getString("tur"));
                film.setDuration(resultSet.getInt("duration"));
                film.setYear(resultSet.getInt("year"));
                films.add(film);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching films", e);
        }
        return films;
    }

    public void addFilm(Film film) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO films (name, description, text, tur, duration, year) VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setString(3, film.getText());
            statement.setString(4, film.getTur());
            statement.setInt(5, film.getDuration()); 
            statement.setInt(6, film.getYear());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding film", e);
        }
    }

    public void deleteFilm(int id) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM films WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting film with id: " + id, e);
        }
    }

    public void updateFilm(Film film) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("UPDATE films SET name = ?, description = ?, text = ?, tur = ?, duration = ?, year = ? WHERE id = ?")) {
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setString(3, film.getText());
            statement.setString(4, film.getTur());
            statement.setInt(5, film.getDuration());
            statement.setInt(6, film.getYear());
            statement.setInt(7, film.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating film with id: " + film.getId(), e);
        }
    }
}
