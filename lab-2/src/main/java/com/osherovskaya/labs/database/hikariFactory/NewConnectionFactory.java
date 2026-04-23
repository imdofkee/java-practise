package com.osherovskaya.labs.database.hikariFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NewConnectionFactory implements ConnectionFactory {

    private final String url;
    private final String user;
    private final String password;
    private final String schema;

    public NewConnectionFactory(String url, String user, String password, String schema) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.schema = schema;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void closeDataSource() {}

    @Override
    public void close() {
        this.closeDataSource();
    }

    @Override
    public String getSchema() {
        return this.schema;
    }
}
