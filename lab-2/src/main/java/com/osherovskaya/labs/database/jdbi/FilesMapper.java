//package com.osherovskaya.labs.database.jdbi;
//
//import org.jdbi.v3.core.mapper.RowMapper;
//
//import org.jdbi.v3.core.statement.StatementContext;
//import com.osherovskaya.labs.database.model.File;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class FilesMapper implements RowMapper<File> {
//    @Override
//    public File map(ResultSet rs, StatementContext ctx) throws SQLException {
//        int id = (int) rs.getObject("id");
//        String fileName = rs.getString("file_name");
//        int sizeInKB = (int) rs.getObject("size");
//
//        return new File(id, fileName, sizeInKB);
//    }
//}
