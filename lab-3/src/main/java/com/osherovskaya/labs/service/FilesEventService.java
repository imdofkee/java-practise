package com.osherovskaya.labs.service;

//import com.osherovskaya.labs.database.exceptions.Exceptions;
import com.osherovskaya.labs.database.repository.FilesEventRepository;
import com.osherovskaya.labs.database.model.File;
import com.osherovskaya.labs.service.EntityService;
import java.util.List;

public class FilesEventService implements EntityService{
    private final FilesEventRepository repository;

    // Конструктор принимает репозиторий
    public FilesEventService(FilesEventRepository repository) {
        this.repository = repository;
    }

    // Create - возвращает ID созданной записи
    public int save(File file) {
        try {
            return repository.save(file);
        } catch (RuntimeException e) {
            System.err.println("Ошибка при сохранении: " + e.getMessage());
            return -1;
        }
    }

    // Create с отдельными параметрами
    public int save(int id, String name, int sizeInKb) {
        return save(new File(id, name, String.valueOf(sizeInKb)));
    }

    // Read by id - бросает исключение, если запись не найдена
    public File findById(int id) throws RuntimeException {
        File file = repository.findById(id);
        if (file != null) {
            return file;
        } else {
            throw new RuntimeException("Файл с ID=" + id + " не найден");
        }
    }

    // Read by name - поиск по полю
    public File findByField(String filename) throws RuntimeException {
        File file = repository.findByField(filename);
        if (file != null) {
            return file;
        } else {
            throw new RuntimeException("Файл с именем '" + filename + "' не найден");
        }
    }

    // Read all
    public List<File> findAll() {
        return repository.findAll();
    }

    // Update - бросает исключение, если запись не найдена
    public void update(File file) throws RuntimeException {
        File existingFile = repository.findById(file.getFileID());
        if (existingFile == null) {
            throw new RuntimeException("Невозможно обновить: файл с ID=" + file.getFileID() + " не существует");
        }

        boolean updated = repository.update(file);
        if (!updated) {
            throw new RuntimeException("Не удалось обновить файл с ID=" + file.getFileID());
        }
    }

    // Update с отдельными параметрами
    public void update(int id, String newName, int newSizeInKb) throws RuntimeException {
        update(new File(id, newName, String.valueOf(newSizeInKb)));
    }

    // Delete
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    // Delete с проверкой существования
    public void deleteByIdSafe(int id) throws RuntimeException {
        File existingFile = repository.findById(id);
        if (existingFile == null) {
            throw new RuntimeException("Невозможно удалить: файл с ID=" + id + " не существует");
        }
        repository.deleteById(id);
    }

    // Проверка существования файла
    public boolean exists(int id) {
        return repository.findById(id) != null;
    }

    public boolean exists(String filename) {
        return repository.findByField(filename) != null;
    }
}