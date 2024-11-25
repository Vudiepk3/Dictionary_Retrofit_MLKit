package com.example.dictionary_retrofit_mlkit.models;

import java.util.List;

// Lớp đại diện cho nghĩa của từ
public class Meaning {
    private String partOfSpeech;
    private List<Definition> definitions;
    private List<String> synonyms;
    private List<String> antonyms;

    // Getter và Setter
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }


    public List<String> getSynonyms() {
        return synonyms;
    }


    public List<String> getAntonyms() {
        return antonyms;
    }


}