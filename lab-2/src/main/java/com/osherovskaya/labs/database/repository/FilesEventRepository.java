package com.osherovskaya.labs.database.repository;

import com.osherovskaya.labs.database.exceptions.Exceptions;
import com.osherovskaya.labs.database.hikariFactory.ConnectionFactory;
import com.osherovskaya.labs.database.model.File;
import com.osherovskaya.labs.database.repository.EntityRepository;

import java.sql.*;
import java.util.*;  // по факту только Array и ArrayList
//import java.util.Optional;

public class FilesEventRepository implements EntityRepository {

    private final ConnectionFactory connectionFactory;
    private final String schema;
    private final String table;

    public FilesEventRepository(ConnectionFactory connectionFactory, String schema, String table) throws Exceptions {
        this.connectionFactory = connectionFactory;
        this.schema = schema;
        this.table = table;
        createTables();
    }
    // Optional?
    public File findById(int id) {
        String sql = "SELECT id, file_name, size_in_kb FROM " + table + " WHERE id = ?";
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка в поиске по ID. Он точно существует?", e);
        }
    }

    public List<File> findAll() {
        String sql = "SELECT id, file_name, size_in_kb FROM " + table;
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
            throw new RuntimeException("Ошибка фетча файлов", e);
        }
    }

    public int save(File file) {
        String sql = "INSERT INTO " + table + " (id, file_name, size_in_kb) VALUES (?, ?, ?)";
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, file.getFileID());
            ps.setString(2, file.getFileName());
            ps.setObject(3, file.getFileSize());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка в сохранении", e);
        }
        return file.getFileID();
    }

    private void createTables() {
        String createTable = "CREATE TABLE IF NOT EXISTS \"" + table + "\"" + """
                (
                id INTEGER PRIMARY KEY,
                file_name TEXT NOT NULL,
                size_in_kb TEXT NOT NULL
                )
                """;
        try (Connection conn = connectionFactory.getConnection()) {
            try (Statement st = conn.createStatement()) {
                st.execute(createTable);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания таблицы", e);
        }
    }

    private static File map(ResultSet rs) throws SQLException {
        int id = (int) rs.getObject("id");
        String fileName = rs.getString("file_name");
        String sizeInKB = rs.getString("size_in_kb");
        return new File(id, fileName, sizeInKB);
    }

    public boolean update(File newObject) {
        String sql = "UPDATE " + this.table + "SET file_name=?, size_in_kb=? WHERE id=?";
        try (Connection conn = this.connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newObject.getFileName());
            ps.setInt(2, newObject.getFileSize());
            ps.setInt(3, newObject.getFileID());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось обновить файл", e);
        }
    }

    public File findByField(String filename) {
        String sql = "SELECT id, file_name, size_in_kb FROM " + table + " WHERE file_name = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, filename);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска по имени файла", e);
        }
    }

    public void deleteById(int id) {
        String sql = String.format("DELETE FROM %s WHERE id=?;", this.table);
        try (
                Connection conn = this.connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка! Удалить не вышло", e);
        }
    }
}
