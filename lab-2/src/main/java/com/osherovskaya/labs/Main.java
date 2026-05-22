package com.osherovskaya.labs;

import com.osherovskaya.labs.database.Migrator;
import com.osherovskaya.labs.database.exceptions.LabotoryRuntimeException;
import com.osherovskaya.labs.database.hikariFactory.HikariConnectionFactory;
import com.osherovskaya.labs.database.repository.FilesEventRepository;
import com.osherovskaya.labs.database.model.File;

import java.util.List;


public class Main {
    public static void main(String[] args) throws LabotoryRuntimeException {
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
            // Очистка таблицы перед тестированием
            List<File> allFiles = repository.findAll();
            for (File file : allFiles) {
                repository.deleteById(file.getFileID());
            }

            repository.save(new File(1, "My first Java code", "1024"));
//            System.out.println(repository.findAll());
            System.out.println(repository.findById(1));
            repository.update(new File(1, "My last Java code, i will be a frontender! (i'll be a homeless)", "101"));
            repository.deleteById(2);
            System.out.println(repository.findAll());

        } catch (LabotoryRuntimeException exception) {
            throw new LabotoryRuntimeException(
                    "Просчитались, вот где: " + exception.getMessage(),
                    exception
            );
        }
    }
}