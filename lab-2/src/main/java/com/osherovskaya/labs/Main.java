package com.osherovskaya.labs;

import com.osherovskaya.labs.database.Migrator;
import com.osherovskaya.labs.database.exceptions.Exceptions;
import com.osherovskaya.labs.database.exceptions.LabotoryRuntimeException;
import com.osherovskaya.labs.database.hikariFactory.HikariConnectionFactory;
import com.osherovskaya.labs.database.repository.FilesEventRepository;
import com.osherovskaya.labs.database.model.File;


public class Main {
    public static void main(String[] args) throws Exception {
        HikariConnectionFactory factory = new HikariConnectionFactory(
                System.getenv("DATABASE_URL"),
                System.getenv("DATABASE_USERNAME"),
                System.getenv("DATABASE_PASSWORD"),
                System.getenv("APP_DATABASE_SCHEMA")
        );

        Migrator databaseMigrator = new Migrator(factory);
        databaseMigrator.runMigrations();

        try {
             FilesEventRepository repository = new FilesEventRepository(factory, "programming practise", "files");
             repository.save(new File(1, "My first Java Code", "1024"));
             System.out.println(repository.findAll());
             System.out.println(repository.findById(1));
             repository.update(new File(1, "My last Java Code (im a frontender)", "101"));
             repository.deleteById(2);
             System.out.println(repository.findAll());
        }
        catch (RuntimeException exception) {
            throw new LabotoryRuntimeException( //по факту отлавливается и обычным RuntimeException
                    "Просчитались, вот где: " + exception.getMessage(),
                    exception
            );
//            System.out.println("Просчитались, вот где: "+ Exception.getMessage());
//            System.out.println("И вот почему: "+ Exception.getCause());
        }
    }
}
