package com.osherovskaya.labs.crud;

import com.osherovskaya.labs.model.character.Character;
import com.osherovskaya.labs.parse.csvParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class crud {
    private final String inputFile;

    public crud(String inputFile) {
        this.inputFile = inputFile;
    }

    // READ
    private List<Character> readAllFromFile() {
        List<Character> characters = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                Character character = csvParser.parseLine(line);
                if (character != null) {
                    characters.add(character);
                }
            }
        } catch (IOException e) {
            System.err.println("Просчитались и вот где: " + e.getMessage());
        }
        return characters;
    }

    // SAVE
    private void saveAllToFile(List<Character> characters) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            writer.write("id,name,status,species,type,gender,origin_name,location_name,created");
            writer.newLine();

            for (Character c : characters) {
                writer.write(serializeCharacter(c));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Просчитались при сохранении и вот где: " + e.getMessage());
        }
    }

    // CREATE
    public boolean create(Character character) {
        if (character == null) return false;

        List<Character> characters = readAllFromFile();

        characters.add(character);
        saveAllToFile(characters);
        System.out.println("Создан персонаж: " + character.getName() + " (ID: " + character.getId() + ")");
        return true;
    }

    // READ
    public Character findById(Integer id) {
        if (id == null) return null;

        List<Character> characters = readAllFromFile();
        for (Character c : characters) {
            if (id.equals(c.getId())) {
                return c;
            }
        }
        return null;
    }

    // READ (все)
    public List<Character> findAll() {
        return readAllFromFile();
    }

    // UPDATE
    public boolean update(Integer id, Character updatedCharacter) {
        if (id == null) return false;

        List<Character> characters = readAllFromFile();

        for (int i = 0; i < characters.size(); i++) {
            if (id.equals(characters.get(i).getId())) {
                updatedCharacter.setId(id);
                characters.set(i, updatedCharacter);
                saveAllToFile(characters);
                System.out.println("Обновлён персонаж с ID: " + id);
                return true;
            }
        }

        System.err.println("Персонаж с ID " + id + " не найден");
        return false;
    }

    // DELETE
    public boolean delete(Integer id) {
        if (id == null) return false;

        List<Character> characters = readAllFromFile();

        for (int i = 0; i < characters.size(); i++) {
            if (id.equals(characters.get(i).getId())) {
                characters.remove(i);
                saveAllToFile(characters);
                System.out.println("Удалён персонаж с ID: " + id);
                return true;
            }
        }

        System.err.println("Персонаж с ID " + id + " не найден");
        return false;
    }

    private String serializeCharacter(Character c) {
        return String.join(",",
                String.valueOf(c.getId()),
                quoteIfNeeded(c.getName()),
                quoteIfNeeded(c.getStatus()),
                quoteIfNeeded(c.getSpecies()),
                quoteIfNeeded(c.getType()),
                quoteIfNeeded(c.getGender()),
                quoteIfNeeded(c.getOriginName()),
                quoteIfNeeded(c.getLocationName()),
                c.getCreated() != null ? c.getCreated().toString() : ""
        );
    }

    private String quoteIfNeeded(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}