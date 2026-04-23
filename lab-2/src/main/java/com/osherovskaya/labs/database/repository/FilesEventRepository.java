package com.osherovskaya.database.repository;

import com.osherovskaya.database.exception.RepositoryException;
import com.osherovskaya.database.factory.ConnectionFactory;
import com.osherovskaya.database.model.File;

import java.sql.*;
import java.util.*;  // по факту только Array и ArrayList
import java.util.Optional;

public class FilesEventRepository implements FileRepository {

    private final ConnectionFactory connectionFactory;
    private final String schema;
    private final String table;

    public FilesEventRepository(ConnectionFactory connectionFactory, String schema, String table) throws RepositoryException {
        this.connectionFactory = connectionFactory;
        this.schema = schema;
        this.table = table;
        createTables();
    }

    @Override
    public int save(File file) throws RepositoryException {
        String sql = "INSERT INTO " + table + " (filename, size_kb) VALUES (?, ?)";
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, file.getFilename());
            ps.setInt(2, file.getSizeInKB());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new RepositoryException("Failed to get generated ID");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to save File", e);
        }
    }

    @Override
    public Optional<File> findById(int id) throws RepositoryException {
        String sql = "SELECT id, filename, size_kb FROM " + table + " WHERE id = ?";
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to fetch File by id", e);
        }
    }

    @Override
    public Optional<File> findByFilename(String filename) throws RepositoryException {
        String sql = "SELECT id, filename, size_kb FROM " + table + " WHERE filename = ?";
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, filename);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException("Просчитались, и вот где", e);
        }
    }

    @Override
    public List<File> findAll() throws RepositoryException {
        String sql = "SELECT id, filename, size_kb FROM " + table + " ORDER BY filename";
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            List<File> result = new ArrayList<>();
            while (rs.next()) {
                result.add(map(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RepositoryException("Просчитались, и вот где", e);
        }
    }

    @Override
    public boolean update(File file) throws RepositoryException {
        String sql = "UPDATE " + table + " SET filename = ?, size_kb = ? WHERE id = ?";
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, file.getFilename());
            ps.setInt(2, file.getSizeInKB());
            ps.setInt(3, file.getId());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RepositoryException("Просчитались, и вот где", e);
        }
    }

    @Override
    public void deleteById(int id) throws RepositoryException {
        String sql = "DELETE FROM " + table + " WHERE id = ?";
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Просчитались, и вот где", e);
        }
    }

    private void createTables() throws RepositoryException {
        String createTable = """
            CREATE TABLE IF NOT EXISTS """ + table + """ 
            (
                id SERIAL PRIMARY KEY,
                filename VARCHAR(255) NOT NULL UNIQUE,
                size_kb INT NOT NULL CHECK (size_kb > 0)
            )
            """;
        try (Connection conn = connectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            st.execute(createTable);
        } catch (SQLException e) {
            throw new RepositoryException("Просчитались, и вот где", e);
        }
    }

    private File map(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String filename = rs.getString("filename");
        int sizeInKB = rs.getInt("size_kb");
        return new File(id, filename, sizeInKB);
    }
}