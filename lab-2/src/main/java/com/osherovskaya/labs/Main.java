package com.osherovskaya;

import com.osherovskaya.database.migrator;
import com.osherovskaya.database.exception.exception;
import com.osherovskaya.database.factory.ConnectionFactory;
import com.osherovskaya.database.factory.NewConnectionFactory;
import com.osherovskaya.database.factory.HikariConnectionFactory;
import com.osherovskaya.database.jdbi_example.OrdersEventRepository;
import com.osherovskaya.database.model.File;


public class Main {
    public static void main(String[] args) throws RepositoryException {
        // Не забудь добавить в при запуске все переменные окружения!
        HikariConnectionFactory factory = new HikariConnectionFactory(
                System.getenv("DATABASE_URL"),
                System.getenv("DATABASE_USERNAME"),
                System.getenv("DATABASE_PASSWORD"),
                System.getenv("APP_DATABASE_SCHEMA")
        );

        DatabaseMigrator databaseMigrator = new migrator(factory);
        databaseMigrator.runMigrations();

        try {
            FilesEventRepository repo_jdbi = new FilesEventRepository(factory);
            System.out.println(repo_jdbi.getAll());

             FilesEventRepository repository = new FilesEventRepository(factory, "shop", "orders");
             repository.save(new File(1, "My first Java Code", 1024));
             System.out.println(repository.getAll());
             System.out.println(repository.getById(1));
             repository.update(new File(2, "My last Java Code", 101));
             repository.deleteById(2);
             System.out.println(repository.getAll());
        }
        catch (RepositoryException exception) {
            System.out.println("Просчитались, вот где: " + exception.getMessage());
            System.out.println("И вот почему: " + exception.getCause());
        }
    }
}
