package com.osherovskaya.labs;

import com.osherovskaya.labs.model.character.Character;
import com.osherovskaya.labs.parse.csvParser;
import com.osherovskaya.labs.hashset.processing.findUnique;
import com.osherovskaya.labs.read.read;

import java.io.*;
import java.util.List;
import java.util.Set;


public class Main {

    private static final String inputFile = "C:/Users/User/IdeaProjects/iosys-lab1-m-java/lab-1/input/characters.csv";
    private static final String outputFile = "lab-1/src/main/resources/unique_pairs_from_characters.csv";

    public static void main(String[] args) {

        List<Character> allCharacters = readFrom();
        if (allCharacters == null) {
            System.err.println("Пустой файл!");
            return;
        }

        System.out.println("Загружено персонажей: " + allCharacters.size());

        Set<String> uniqueTypes = findUnique.FindUnique(allCharacters);
        // Сохранение в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String type : uniqueTypes) {
                writer.write(type);
                writer.newLine();
            }
            System.out.println("Результат сохранён в " + outputFile);
        } catch (IOException e) {
            System.err.println("Просчитались и вот где: " + e.getMessage());
        }
    }

    private static List<Character> readFrom() {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            return read.Read(br);  // ваш готовый метод
        } catch (IOException e) {
            System.err.println("Просчитались и вот где: " + e.getMessage());
            return null;
        }
    }
}
