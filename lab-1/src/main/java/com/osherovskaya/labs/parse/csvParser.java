package com.osherovskaya.labs.parse;

import com.osherovskaya.labs.model.character.Character;
//import lombok.*; // ??

public class csvParser {
    public static Character parseLine(String line) {
        if (line == null) return null;

        String[] field = line.split(",");

        String status = field[2].trim();
        String species = field[3].trim();

        Character character = new Character();
        character.setStatus(status);
        character.setSpecies(species);
        System.out.println("Отработало");
        return character;
    }
}
