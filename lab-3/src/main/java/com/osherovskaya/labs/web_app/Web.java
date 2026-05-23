package com.osherovskaya.labs.web_app;

import io.javalin.Javalin;
import com.osherovskaya.labs.service.FilesEventService;
import com.osherovskaya.labs.controllers.FilesEventController;
import io.javalin.http.Context;
import io.javalin.http.Header;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.RouteRole;

import java.util.*;
import static io.javalin.apibuilder.ApiBuilder.*;

// Роли для доступа
enum Role implements RouteRole {
    ANYONE, USER_READ, USER_WRITE
}

// Класс для аутентификации и авторизации
class Auth {
    private static final Map<Pair, List<Role>> userRolesMap = Map.of(
            new Pair("admin", "admin123"), List.of(Role.USER_READ, Role.USER_WRITE)
    );

    record Pair(String username, String password) {}

    public static List<Role> userRoles(Context ctx) {
        return Optional.ofNullable(ctx.basicAuthCredentials())
                .map(credentials -> userRolesMap.getOrDefault(
                        new Pair(credentials.getUsername(), credentials.getPassword()),
                        List.of()))
                .orElse(List.of());
    }

    // Проверка доступа
    public static void handleAccess(Context ctx) {
        var permittedRoles = ctx.routeRoles();

        // Проверяем, есть ли у пользователя необходимая роль
        if (userRoles(ctx).stream().anyMatch(permittedRoles::contains)) {
            return;
        }

        // Доступ запрещен
        ctx.header(Header.WWW_AUTHENTICATE, "Доступ запрещен");
        throw new UnauthorizedResponse();
    }
}

public class Web {
    private Javalin app;
    private final FilesEventController filesEventController;

    public Web(FilesEventService filesEventService) {
        this.filesEventController = new FilesEventController(filesEventService);

        this.app = Javalin.create(config -> {
            config.router.mount(router -> {
                router.beforeMatched(Auth::handleAccess);
            }).apiBuilder(() -> {
                // Публичные эндпоинты (доступны всем)
                get("/", ctx -> {
                    ctx.json(Map.of("message", "Welcome to Files API"));
                }, Role.ANYONE);

                get("/test", ctx -> {
                    ctx.status(418).json(Map.of("id", 1));
                }, Role.ANYONE);

                // Эндпоинты для работы с файлами
                path("files", () -> {
                    // GET /files - поиск по имени (доступен всем, кто может читать)
                    get(ctx -> {
                        String name = ctx.queryParam("name");
                        if (name != null && !name.isEmpty()) {
                            filesEventController.getByName(ctx);
                        } else {
                            filesEventController.getAll(ctx);
                        }
                    }, Role.USER_READ);

                    // POST /files - создание
                    post(filesEventController::create, Role.USER_WRITE);

                    // Эндпоинты с параметром id
                    path("{id}", () -> {
                        // GET /files/{id} - получение по ID
                        get(filesEventController::getById, Role.USER_READ);

                        // PUT /files/{id} - обновление
                        put(filesEventController::update, Role.USER_WRITE);

                        // DELETE /files/{id} - удаление
                        delete(filesEventController::delete, Role.USER_WRITE);
                    });
                });
            });
        }).start(7070);
    }

    public void run(int port) {
        if (this.app != null) {
            this.app.stop(); // Останавливаем текущий экземпляр если запущен
            this.app.start(port);
        }
    }

    public void stop() {
        if (this.app != null) {
            this.app.stop();
        }
    }
}