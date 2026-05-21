package com.osherovskaya.labs.controllers;

//import com.osherovskaya.labs.database.exceptions.EntityNotFoundException;
import com.osherovskaya.labs.database.model.File;
import com.osherovskaya.labs.service.FilesEventService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.Map;

public class FilesEventController {
    private final FilesEventService service;

    public FilesEventController(FilesEventService service) {
        this.service = service;
    }

    public void start(int port) {
        Javalin app = Javalin.create().start(port);

        app.post("/files", ctx -> {
            File file = ctx.bodyAsClass(File.class);
            int id = service.save(file);
            ctx.status(201).json(Map.of("id", id));
        });

        app.get("/files/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            File file = service.findById(id);
            ctx.json(file);
        });

        app.get("/files", ctx -> ctx.json(service.findAll()));

        app.put("/files", ctx -> {
            File file = ctx.bodyAsClass(File.class);
            service.update(file);
            ctx.status(204);
        });

        app.delete("/files/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            service.deleteById(id);
            ctx.status(204);
        });

        app.exception(RuntimeException.class, (e, ctx) -> {
            ctx.status(404).json(Map.of("Ошибка", e.getMessage()));
        });
    }
}


