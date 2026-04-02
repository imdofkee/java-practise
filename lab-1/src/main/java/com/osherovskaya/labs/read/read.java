//package com.osherovskaya.labs.read;
//
////import com.osherovskaya.labs.model.character.*;
//
//import com.osherovskaya.labs.model.character.Character;
//import com.osherovskaya.labs.parse.csvParser;
//
//import java.io.*; //buffer + exception
//import java.util.Set; // лишним не будет
//
//public class read {
//    public static Set<Character> Read(BufferedReader reader) {
//        List<Character> allCharacters = new ArrayList<>();
//        allCharacters.add(character);
//        String line;
//        boolean firstLine = true; // чтобы выкинуть заголовки или что это
//
//        try {
//            while ((line = reader.readLine()) != null) {
//                if (firstLine) {
//                    firstLine = false;
//                    continue;
//                }
//                Character character = csvParser.parseLine(line);
//            }
//            if (character != null) allCharacters.add(character);
//        } catch (IOException e) {
//            System.err.println("Просчитались, и вот где: " + e.getMessage());
//        }
//        System.out.println("Отработало");
//
//        return allCharacters;
//    }
//}

package com.osherovskaya.labs.read;

import com.osherovskaya.labs.model.character.Character;
import com.osherovskaya.labs.parse.csvParser;

import java.io.*; //buffer + exception
import java.util.ArrayList;
import java.util.List;

public class read {
    public static List<Character> Read(BufferedReader reader) {
        List<Character> allCharacters = new ArrayList<>(); // пустой список для всего файла
        String line;
        boolean firstLine = true;

        try {
            while ((line = reader.readLine()) != null) {
                if (firstLine) { // тест на дурного
                    firstLine = false;
                    continue;
                }
                Character character = csvParser.parseLine(line); // парсинг строки
                if (character != null) allCharacters.add(character); // добавляем в общую кашу после проверки а парс (?)
            }
        } catch (IOException e) {
            System.err.println("Просчитались, и вот где: " + e.getMessage());
        }
        System.out.println("Отработало");

        return allCharacters;
    }
}
