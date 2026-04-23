package com.osherovskaya.labs.database.jdbi;

import com.osherovskaya.labs.database.exceptions.Exceptions;
import com.osherovskaya.labs.database.model.File;

//public class FilesEventRepository {
//}



import com.osherovskaya.labs.database.jdbi.v3.core.Jdbi;
//import com.osherovskaya.labs.v3.sqlobject.SqlObjectPlugin;
import com.osherovskaya.labs.database.hikariFactory.HikariConnectionFactory;

import java.util.List;
import java.util.Optional;

public class FilesEventRepository {
    private final FilesDAO dao;

    public FilesEventRepository(HikariConnectionFactory factory) {
        Jdbi jdbi = Jdbi.create(factory.getDataSource());
        jdbi.installPlugin(new SqlObjectPlugin());
        this.dao = jdbi.onDemand(FilesDAO.class);
    }

    public Optional<File> getById(int id) throws Exceptions {
        try {
            return dao.getById(id);
        } catch (Exception e) {
            throw new Exceptions("Failed to fetch NewsEvent by id", e);
        }
    }

    public List<File> getAll() throws RepositoryException {
        try {
            return dao.getAll();
        } catch (Exception e) {
            throw new Exceptions("Failed to fetch all NewsEvents", e);
        }
    }
}
