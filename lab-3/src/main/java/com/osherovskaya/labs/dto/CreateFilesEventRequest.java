//package com.osherovskaya.labs.dto;
//
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Schema(description = "Запрос на создание файла")
//
//public class CreateFilesEventRequest {
//
//    @Schema(description = "ID файла", example = "1")
//    @NotNull(message = "ID не может быть пустым!")
////    @Min(value = 1, message = "ID должен быть > 0")
//    private Integer id;
//
//    @Schema(description = "Имя файла", example = "My first Java Code")
//    @NotBlank(message = "Имя файла не может быть пустым")
//    @Size(min = 1, max = 255, message = "Имя файла должно быть от 1 до 255 символов")
//    private String fileName;
//
//    @Schema(description = "Размер файла в KB", example = "1024")
//    @NotBlank(message = "Файл не может не весить")
//    private String sizeInKB;
//}