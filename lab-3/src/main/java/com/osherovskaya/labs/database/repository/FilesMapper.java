package com.osherovskaya.labs.database.repository;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import com.osherovskaya.labs.database.model.File;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilesMapper implements RowMapper<File> {
    @Override
    public File map(ResultSet rs, StatementContext ctx) throws SQLException {
        int id = rs.getInt("id");
        String file_name = rs.getString("file_name");
        String size_in_kb = rs.getString("size_in_kb");

        return new File(id, file_name, size_in_kb);
    }
}
