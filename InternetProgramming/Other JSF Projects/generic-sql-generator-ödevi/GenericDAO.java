import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericDAO<T> {

    public boolean insert(T object, String[] columns, Object[] values) {
        String tableName = object.getClass().getSimpleName();
        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
                for (int i = 0; i < columns.length; i++) {
                    sql.append(columns[i]);
                    if (i < columns.length - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(") VALUES (");
                for (int i = 0; i < values.length; i++) {
                    sql.append("?");
                    if (i < values.length - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(")");

                try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
                    for (int i = 0; i < values.length; i++) {
                        statement.setObject(i + 1, values[i]);
                    }
                    int rowsInserted = statement.executeUpdate();
                    return rowsInserted > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(T object, String conditionColumn, Object conditionValue) {
        String tableName = object.getClass().getSimpleName();
        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                String sql = "DELETE FROM " + tableName + " WHERE " + conditionColumn + " = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setObject(1, conditionValue);
                    int rowsDeleted = statement.executeUpdate();
                    return rowsDeleted > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(T object, String[] columns, Object[] values, String conditionColumn, Object conditionValue) {
        String tableName = object.getClass().getSimpleName();
        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                StringBuilder sql = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
                for (int i = 0; i < columns.length; i++) {
                    sql.append(columns[i]).append(" = ?");
                    if (i < columns.length - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(" WHERE ").append(conditionColumn).append(" = ?");

                try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
                    int paramIndex = 1;
                    for (int i = 0; i < values.length; i++) {
                        statement.setObject(paramIndex++, values[i]);
                    }
                    statement.setObject(paramIndex, conditionValue);

                    int rowsUpdated = statement.executeUpdate();
                    return rowsUpdated > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet select(T object, String[] columns, String conditionColumn, Object conditionValue) {
        String tableName = object.getClass().getSimpleName();
        try {
            Connection connection = DBConnection.getConnection();
            if (connection != null) {
                StringBuilder sql = new StringBuilder("SELECT ");
                for (int i = 0; i < columns.length; i++) {
                    sql.append(columns[i]);
                    if (i < columns.length - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(" FROM ").append(tableName);
                if (conditionColumn != null && conditionValue != null) {
                    sql.append(" WHERE ").append(conditionColumn).append(" = ?");
                }

                PreparedStatement statement = connection.prepareStatement(sql.toString());
                if (conditionColumn != null && conditionValue != null) {
                    statement.setObject(1, conditionValue);
                }

                return statement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
