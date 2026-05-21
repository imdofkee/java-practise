//package com.osherovskaya.labs.dto;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Schema(description = "Универсальный ответ API")
//public class UniversalResponse<T> {
//
//    @Schema(description = "Код ответа", example = "200")
//    private int code;
//
//    @Schema(description = "Сообщение", example = "Success")
//    private String message;
//
//    @Schema(description = "Данные ответа")
//    private T data;
//
//    public static <T> UniversalResponse<T> ok(T data) {
//        return UniversalResponse.<T>builder()
//                .code(200)
//                .message("Success")
//                .data(data)
//                .build();
//    }
//
//    public static <T> UniversalResponse<T> ok(T data, String message) {
//        return UniversalResponse.<T>builder()
//                .code(200)
//                .message(message)
//                .data(data)
//                .build();
//    }
//
//    public static <T> UniversalResponse<T> error(int code, String message) {
//        return UniversalResponse.<T>builder()
//                .code(code)
//                .message(message)
//                .build();
//    }
//}