package com.osherovskaya.labs.service;

import com.osherovskaya.labs.database.model.File;

import java.util.List;

public interface EntityService {
    // Create (возвращает id созданной записи)
    int save(File file);

    // Read (бросает исключение, если запись не найдена)
    File findById(int id);

    // Read (бросает исключение, если запись не найдена)
    File findByField(String field);

    List<File> findAll();

    // Update (используйте id для поиска сущности и бросайте исключение, если запись не найдена)
    void update(File entity);

    // Delete
    void deleteById(int id);
}
