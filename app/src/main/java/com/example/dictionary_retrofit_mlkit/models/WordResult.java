package com.example.dictionary_retrofit_mlkit.models;

import java.util.List;

// Lớp đại diện cho kết quả của một từ
public class WordResult {
    public String word;
    public String phonetic;
    public List<Meaning> meanings;

    // Constructor
 

    // Getter và Setter
    public String getWord() {
        return word;
    }
    
    public String getPhonetic() {
        return phonetic;
    }
    
    public List<Meaning> getMeanings() {
        return meanings;
    }
    
}


