package com.osherovskaya.labs.database.hikariFactory;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    Connection getConnection() throws SQLException;
    void closeDataSource();

    String getSchema();

    void close();
}
