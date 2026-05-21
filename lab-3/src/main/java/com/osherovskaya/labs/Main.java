package com.osherovskaya.labs;

import com.osherovskaya.labs.database.Migrator;
import com.osherovskaya.labs.database.exceptions.Exceptions;
import com.osherovskaya.labs.database.hikariFactory.HikariConnectionFactory;
import com.osherovskaya.labs.database.repository.FilesEventRepository;
import com.osherovskaya.labs.service.FilesEventService;
//import com.osherovskaya.labs.web_app.Web;
import com.osherovskaya.labs.database.exceptions.Exceptions;

public class Main {
    public static void main(String[] args) throws Exceptions {
        HikariConnectionFactory factory = new HikariConnectionFactory(
                System.getenv("DATABASE_URL"),
                System.getenv("DATABASE_USERNAME"),
                System.getenv("DATABASE_PASSWORD"),
                System.getenv("APP_DATABASE_SCHEMA")
        );

        Migrator databaseMigrator = new Migrator(factory);
        databaseMigrator.runMigrations();

        try {
            FilesEventRepository repository = new FilesEventRepository(
                    factory,
                    System.getenv("APP_DATABASE_SCHEMA"),
                    "files"
            );

            FilesEventService service = new FilesEventService(repository);
//            Web app = new Web(service);
//            app.run(8080);
        } catch (Exceptions e) {
            System.err.println("Ошибка при создании репозитория: " + e.getMessage());
            e.printStackTrace();
        }
    }
}