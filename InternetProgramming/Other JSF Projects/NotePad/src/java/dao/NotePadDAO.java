package dao;

import entity.NotePad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotePadDAO {

    private static final Logger LOGGER = Logger.getLogger(NotePadDAO.class.getName());

    public List<NotePad> getAllNotePads() {
        List<NotePad> notePads = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM NotePad ORDER BY id ASC")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                NotePad notePad = new NotePad();
                notePad.setId(resultSet.getInt("id"));
                notePad.setName(resultSet.getString("name"));
                notePad.setDescription(resultSet.getString("description"));
                notePad.setText(resultSet.getString("text"));
                notePads.add(notePad);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while fetching note pads", e);
        }
        return notePads;
    }

    public void addNotePad(NotePad notePad) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO NotePad (name, description, text) VALUES (?, ?, ?)")) {
            statement.setString(1, notePad.getName());
            statement.setString(2, notePad.getDescription());
            statement.setString(3, notePad.getText());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding note pad", e);
        }
    }
    
    public void deleteNotePad(int id) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM NotePad WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting note pad with id: " + id, e);
        }
    }

    public void updateNotePad(NotePad notePad) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement("UPDATE NotePad SET name = ?, description = ?, text = ? WHERE id = ?")) {
            statement.setString(1, notePad.getName());
            statement.setString(2, notePad.getDescription());
            statement.setString(3, notePad.getText());
            statement.setInt(4, notePad.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating note pad with id: " + notePad.getId(), e);
        }
    }
}
