package com.osherovskaya.labs.hashset.processing;

import com.osherovskaya.labs.model.character.Character;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FindUnique {
    public static Set<String> findUnique(List<Character> characters){
        Set<String> unique = new HashSet<>();
        for (Character i: characters) {
            String testPair = i.getStatus() + " - " + i.getSpecies();
            unique.add(testPair);
        }
        return unique;
    }
}
