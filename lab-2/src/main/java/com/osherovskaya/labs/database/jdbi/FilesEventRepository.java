////package com.osherovskaya.labs.database.jdbi;
////
////import com.osherovskaya.labs.database.exceptions.Exceptions;
////import com.osherovskaya.labs.database.model.File;
////
////import com.osherovskaya.labs.database.jdbi.v3.core.Jdbi;
//////import com.osherovskaya.labs.v3.sqlobject.SqlObjectPlugin;
////import com.osherovskaya.labs.database.hikariFactory.HikariConnectionFactory;
////
////import java.util.List;
////import java.util.Optional;
////
////public class FilesEventRepository {
////    private final FilesDAO dao;
////
////    public FilesEventRepository(HikariConnectionFactory factory) {
////        Jdbi jdbi = Jdbi.create(factory.getDataSource());
////        jdbi.installPlugin(new SqlObjectPlugin());
////        this.dao = jdbi.onDemand(FilesDAO.class);
////    }
////
////    public Optional<File> getById(int id) throws Exceptions {
////        try {
////            return dao.getById(id);
////        } catch (Exception e) {
////            throw new Exceptions("Failed to fetch NewsEvent (by id)", e);
////        }
////    }
////
////    public List<File> getAll() throws Exceptions {
////        try {
////            return dao.getAll();
////        } catch (Exception e) {
////            throw new Exceptions("Failed to fetch all NewsEvents", e);
////        }
////    }
////}
//
//
////package com.osherovskaya.labs.database.jdbi;
////
////import com.osherovskaya.labs.database.exceptions.Exceptions;
////import com.osherovskaya.labs.database.model.File;
//
////import com.osherovskaya.labs.database.jdbi..core.Jdbi;
////import com.osherovskaya.labs.v3.sqlobject.SqlObjectPlugin;
////import com.osherovskaya.labs.database.hikariFactory.HikariConnectionFactory;
//
//
//package com.osherovskaya.labs.database.repository.*;
//
//import com.osherovskaya.labs.database.exceptions.Exceptions;
//import com.osherovskaya.labs.database.hikariFactory.ConnectionFactory;
//import com.osherovskaya.labs.database.model.File;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class FilesEventRepository {
//
//    private final ConnectionFactory connectionFactory;
//    private final String schema;
//    private final String table;
//
//    public FilesEventRepository(ConnectionFactory connectionFactory, String schema, String table) throws Exceptions {
//        this.connectionFactory = connectionFactory;
//        this.schema = schema;
//        this.table = table;
//
//        createTables();
//    }
//
//    public Optional<File> getById(int id) throws Exceptions {
//        String sql = "SELECT id, customer_name, amount FROM " + table + " WHERE id = ?";
//        try (
//                Connection conn = connectionFactory.getConnection();
//                PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setObject(1, id);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    return Optional.of(map(rs));
//                }
//            }
//            return Optional.empty();
//        } catch (SQLException e) {
//            throw new Exceptions("Failed to fetch NewsEvent by id", e);
//        }
//    }
//
//    public List<File> getAll() throws Exceptions {
//        String sql = "SELECT id, customer_name, amount FROM " + table + " ORDER BY customer_name, amount";
//        try (
//                Connection conn = connectionFactory.getConnection();
//                PreparedStatement ps = conn.prepareStatement(sql);
//                ResultSet rs = ps.executeQuery()) {
//
//            List<File> result = new ArrayList<>(rs.getFetchSize());
//            while (rs.next()) {
//                result.add(map(rs));
//            }
//            return result;
//        } catch (SQLException e) {
//            throw new Exceptions("Failed to fetch all Orders", e);
//        }
//    }
//
//    public void save(File order) throws Exceptions {
//        String sql = "INSERT INTO " + table + " (id, customer_name, amount) VALUES (?, ?, ?)";
//        try (
//                Connection conn = connectionFactory.getConnection();
//                PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setObject(1, order.getFileName());
//            ps.setString(2, order.getFileName());
//            ps.setObject(3, order.getFileSize());
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new Exceptions("Failed to save NewsEvent", e);
//        }
//    }
//
//    private void createTables() throws Exceptions {
//        String createTable = "CREATE TABLE IF NOT EXISTS \"" + table + "\"" + """
//                (
//                id INTEGER PRIMARY KEY,
//                customer_name TEXT NOT NULL,
//                amount TEXT NOT NULL
//                )
//                """;
//        try (Connection conn = connectionFactory.getConnection()) {
//            try (Statement st = conn.createStatement()) {
//                st.execute(createTable);
//            }
//        } catch (SQLException e) {
//            throw new Exceptions("Failed to create tables", e);
//        }
//    }
//
//    private static File map(ResultSet rs) throws SQLException {
//        int id = (int) rs.getObject("id");
//        String customerName = rs.getString("customer_name");
//        String amount = rs.getString("amount");
//        return new File(id, );
//    }
//
//    public void update(File newObject) throws Exceptions {
//        String sql = "UPDATE " + this.table + " SET customer_name=?, amount=? WHERE id=?";;
//        try (
//                Connection conn = this.connectionFactory.getConnection();
//                PreparedStatement ps = conn.prepareStatement(sql);
//        ) {
//            ps.setObject(3, newObject.getFileID());
//            ps.setString(1, newObject.getFileName());
//            ps.setObject(2, newObject.getFileSize());
//
//            ps.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new Exceptions("Failed to INSERT Order", e);
//        }
//    }
//
//    public void deleteById(int id) throws Exceptions {
//        String sql = String.format("DELETE FROM %s WHERE id=?;", this.table);
//        try (
//                Connection conn = this.connectionFactory.getConnection();
//                PreparedStatement ps = conn.prepareStatement(sql);
//        ) {
//            ps.setObject(1, id);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new Exceptions("Failed to DELETE Orders", e);
//        }
//    }
//}
