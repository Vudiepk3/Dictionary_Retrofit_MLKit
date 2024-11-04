package com.example.dictionary_retrofit_mlkit.api;

import com.example.dictionary_retrofit_mlkit.models.WordResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import java.util.List;

// Giao diện Retrofit để định nghĩa các API endpoint
public interface DictionaryApi {

    // Định nghĩa một endpoint để lấy nghĩa của một từ
    @GET("en/{word}")
    Call<List<WordResult>> getMeaning(@Path("word") String word);
}
