package com.osherovskaya.labs;

import com.osherovskaya.labs.database.Migrator;
import com.osherovskaya.labs.database.exceptions.Exceptions;
import com.osherovskaya.labs.database.hikariFactory.HikariConnectionFactory;
import com.osherovskaya.labs.database.repository.FilesEventRepository;
import com.osherovskaya.labs.database.model.File;


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
            FilesEventRepository repo_jdbi = new FilesEventRepository(factory);
            System.out.println(repo_jdbi.getClass());

             FilesEventRepository repository = new FilesEventRepository(factory, "office", "files");
             repository.save(new File(1, "My first Java Code", "1024"));
             System.out.println(repository.getAll());
             System.out.println(repository.getById(1));
             repository.update(new File(2, "My last Java Code", "101"));
             repository.deleteById(2);
             System.out.println(repository.getAll());
        }
        catch (Exceptions exception) {
            System.out.println("Просчитались, вот где: " + exception.getMessage());
            System.out.println("И вот почему: " + exception.getCause());
        }
    }
}
