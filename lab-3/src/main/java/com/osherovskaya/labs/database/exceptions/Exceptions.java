package com.osherovskaya.labs.database.exceptions;

//отдельно отлавливаем ошибки

//public class Exceptions extends Exception {
//
//    public Exceptions(String message) {
//        super(message);
//    }
//
//    public Exceptions(String message, Throwable cause) {
//        super(message, cause);
//    }
//
////    public Exceptions(Throwable cause) {
////        super(cause);
////    }
//}

public class Exceptions extends Exception {

    public Exceptions(String message) {
        super(message);
    }

    public Exceptions(String message, Throwable cause) {
        super(message, cause);
    }
}