package com.osherovskaya.labs.controllers;

//import com.osherovskaya.labs.database.exceptions.EntityNotFoundException;
import com.osherovskaya.labs.database.exceptions.LabotoryRuntimeException;
import com.osherovskaya.labs.database.model.File;
import com.osherovskaya.labs.service.FilesEventService;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.InternalServerErrorResponse;
import java.util.List;
import java.util.Map;

public class FilesEventController {
    private final FilesEventService filesService;

    public FilesEventController(FilesEventService filesService) {
        this.filesService = filesService;
    }

    // POST
    public void create(Context ctx) {
        File file = ctx.bodyAsClass(File.class);
        int savedId = filesService.save(file.getFileID(), file.getFileName(), file.getFileSize());
        if (savedId == -1) {
            ctx.status(500).json(Map.of("error", "Failed to save"));
            return;
        }

        ctx.status(201).json(Map.of("id", savedId));
    }

    // GET
    public void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));

        try {
            File order = filesService.findById(id);
            ctx.status(200).json(
                    Map.of(
                            "id", order.getFileID(),
                            "file_name", order.getFileName(),
                            "size_in_kb", order.getFileSize()
                    )
            );
        } catch (LabotoryRuntimeException e) {
            throw new NotFoundResponse("Файл с этим ID не найден: " + id);
        }
    }

    // GET /files?name=
    public void getByName(Context ctx) {
        String name = ctx.queryParam("file_name");
        if (name == null || name.isBlank()) {
            throw new BadRequestResponse("Введи название файла");
        }
        try {
            File files = filesService.findByField(name);
            ctx.json(files);
        } catch (LabotoryRuntimeException e) {
            throw new InternalServerErrorResponse("Ошибка! Файл не найден " + e);
        }
    }

    // GET
    public void getAll(Context ctx) {
        try {
            List<File> allFiles = filesService.findAll();
            ctx.json(allFiles);
        } catch (LabotoryRuntimeException e) {
            System.out.println(e);
            throw new InternalServerErrorResponse("Ошибка ");
        }
    }

    // PUT
    public void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        File updatedFiles = ctx.bodyAsClass(File.class);

        try {
            filesService.update(updatedFiles);
            ctx.status(204).json(Map.of("id", updatedFiles.getFileID()));
        } catch (LabotoryRuntimeException e) {
            ctx.status(500).json(Map.of("error", "Internal error [ID = " + id + "]"));
        }
    }

    // DELETE
    public void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        try {
            // вызов исключения
            filesService.findById(id);

            filesService.deleteById(id);
            ctx.status(204);
        } catch (LabotoryRuntimeException e) {
            ctx.status(500).json(Map.of("Просчитались", "Файл не найден с этим ID: " + id));
        }
    }
}