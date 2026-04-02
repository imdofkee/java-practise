package com.osherovskaya.labs.output;
//вывод

import java.util.Set;
import java.io.*;

public class result {
    public static void Result(Set<String> types, String outputPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            for (String type : types) {
                writer.write(type == null ? "" : type);
                writer.newLine();
            }
            System.out.println("Финиш. Результат смотреть здесь: " + outputPath);
        } catch (IOException e) {
            System.err.println("Просчитались, и вот где: " + e.getMessage());
        }
    }
}


