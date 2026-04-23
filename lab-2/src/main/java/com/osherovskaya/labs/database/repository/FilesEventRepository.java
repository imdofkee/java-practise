package com.osherovskaya.labs.database.repository;

import com.osherovskaya.labs.database.exceptions.Exceptions;
import com.osherovskaya.labs.database.hikariFactory.ConnectionFactory;
import com.osherovskaya.labs.database.model.File;

import java.sql.*;
import java.util.*;  // по факту только Array и ArrayList
import java.util.Optional;

public class FilesEventRepository {

    private final ConnectionFactory connectionFactory;
    private final String schema;
    private final String table;

    public FilesEventRepository(ConnectionFactory connectionFactory, String schema, String table) throws Exceptions {
        this.connectionFactory = connectionFactory;
        this.schema = schema;
        this.table = table;
        createTables();
    }

    public Optional<File> getById(int id) throws Exceptions {
        String sql = "SELECT id, file_name, sizeInKB FROM " + table + " WHERE id = ?";
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new Exceptions("Failed to fetch NewsEvent by id", e);
        }
    }

    public List<File> getAll() throws Exceptions {
        String sql = "SELECT id, file_name, sizeInKB FROM " + table + " WHERE id = ?";
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            List<File> result = new ArrayList<>(rs.getFetchSize());
            while (rs.next()) {
                result.add(map(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new Exceptions("Failed to fetch all Files", e);
        }
    }

    public void save(File file) throws Exceptions {
        String sql = "INSERT INTO " + table + " (id, file_name, sizeInKB) VALUES (?, ?, ?)";
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, file.getFileID());
            ps.setString(2, file.getFileName());
            ps.setObject(3, file.getFileSize());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exceptions("Failed to save NewsEvent", e);
        }
    }

    private void createTables() throws Exceptions {
        String createTable = "CREATE TABLE IF NOT EXISTS \"" + table + "\"" + """
                (
                id INTEGER PRIMARY KEY,
                file_name TEXT NOT NULL,
                sizeInKB TEXT NOT NULL
                )
                """;
        try (Connection conn = connectionFactory.getConnection()) {
            try (Statement st = conn.createStatement()) {
                st.execute(createTable);
            }
        } catch (SQLException e) {
            throw new Exceptions("Failed to create tables", e);
        }
    }

    private static File map(ResultSet rs) throws SQLException {
        int id = (int) rs.getObject("id");
        String fileName = rs.getString("file_name");
        String sizeInKB = rs.getString("size");
        return new File(id, fileName, sizeInKB);
    }

    public void update(File newObject) throws Exceptions {
        String sql = "UPDATE " + this.table + " SET file_name=?, sizeInKB=? WHERE id=?";;
        try (
                Connection conn = this.connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setObject(3, newObject.getFileID());
            ps.setString(1, newObject.getFileName());
            ps.setObject(2, newObject.getFileSize());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exceptions("Failed to INSERT File", e);
        }
    }

    public void deleteById(int id) throws Exceptions {
        String sql = String.format("DELETE FROM %s WHERE id=?;", this.table);
        try (
                Connection conn = this.connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exceptions("Failed to DELETE File", e);
        }
    }
}
