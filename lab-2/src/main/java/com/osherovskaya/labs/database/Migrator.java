package com.osherovskaya.labs.database;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import com.osherovskaya.labs.database.hikariFactory.ConnectionFactory;
import com.osherovskaya.labs.database.hikariFactory.HikariConnectionFactory;

import java.sql.Connection;

public class Migrator {
    private final HikariConnectionFactory connectionFactory;

    public Migrator(HikariConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void runMigrations() {
        try (Connection connection = connectionFactory.getConnection()) {

            Liquibase liquibase = new Liquibase(
                    "db/changelog/db.changelog-master.xml",
                    new ClassLoaderResourceAccessor(),
                    new JdbcConnection(connection)
            );

            liquibase.update();
            System.out.println("Миграции применены");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при выполнении миграций", e);
        }
    }

}
