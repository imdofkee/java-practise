//package com.osherovskaya.labs.web_app;
//
//import io.javalin.Javalin;
//import com.osherovskaya.labs.service.FilesEventService;
//import com.osherovskaya.labs.controllers.FilesEventController;
//
//import java.util.Map;
//
//public class Web {
//
//    Javalin app;
//    FilesEventController filesEventController;
//
//    public Web(FilesEventService filesEventService) {
//
//        this.filesEventController = new FilesEventController(filesEventService);
//
//        this.app = Javalin.create(config -> {
//            config.jetty.defaultHost = "localhost";
//            config.jetty.defaultPort = 8080;
//
//            config.router.get("/test", ctx -> {
//                ctx.status(418).json(Map.of("id", 1));
//            });
//
//            config.router.post("/files", this.filesEventController::create);
//            config.router.get("/files/{id}", this.filesEventController::getById);
//            config.router.put("/files/{id}", this.filesEventController::update);
//            config.router.delete("/files/{id}", this.filesEventController::delete);
//            config.router.get("/files", this.filesEventController::getByName);
//            config.router.get("/", this.filesEventController::getAll);
//
//
////            config.router.post("/files", this.filesEventController::create);
////            javalinConfig.router.get("/files/{id}", this.filesEventController::getById);
////            javalinConfig.router.put("/files/{id}", this.filesEventController::update);
////            javalinConfig.router.delete("/files/{id}", this.filesEventController::delete);
////            javalinConfig.router.get("/files", this.filesEventController::getByName);
////            javalinConfig.router.get("/", this.filesEventController::getAll);
//                }
//        );
//    }
//
//    public void run(int port) {
//        this.app.start(port);
//    }
//}
