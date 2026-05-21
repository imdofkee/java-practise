package com.osherovskaya.labs.database.repository;

import com.osherovskaya.labs.database.model.File;

import java.util.List;
//public class EntityRepository {
//}

// FileEntity на минималках

public interface EntityRepository {
    // Create (возвращает id созданной записи (в моем случае файла)
    int save(File entity);

    // Read (null, если файл не найдена)
    File findById(int id);

    // Read по второму параметру сущности ()
    File findByField(String field);

    List<File> findAll();

    // Update (используется id для поиска сущности, return false, если файл не найдена)
    boolean update(File entity);

    // Delete
    void deleteById(int id);
}

