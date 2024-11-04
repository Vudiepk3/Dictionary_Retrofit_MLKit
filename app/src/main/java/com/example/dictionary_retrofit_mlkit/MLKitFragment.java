package com.example.dictionary_retrofit_mlkit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class MLKitFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Translator englishGermanTranslator;

    public MLKitFragment() {
        // Required empty public constructor
    }

    public static MLKitFragment newInstance(String param1, String param2) {
        MLKitFragment fragment = new MLKitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Handle parameters if needed
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_m_l_kit, container, false);

        // Initialize UI components
        EditText inputEditText = view.findViewById(R.id.inputEditText);
        Button translateButton = view.findViewById(R.id.translateButton);
        TextView outputText = view.findViewById(R.id.outputText);

        // Set up translator options (source and target language)
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH) // Source language is English
                        .setTargetLanguage(TranslateLanguage.GERMAN)  // Target language is German
                        .build();

        // Initialize translator
        englishGermanTranslator = Translation.getClient(options);

        // Set conditions for model download (only download when connected to Wi-Fi)
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();

        // Download model if needed
        englishGermanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // When the model is downloaded successfully, set up the click listener for the translate button
                        translateButton.setOnClickListener(v -> {
                            String input = inputEditText.getText().toString(); // Get input text
                            translateText(input, outputText); // Call translate method
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Show error message if model download fails
                        Toast.makeText(requireActivity(), "Model download failed", Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }

    // Method to translate text
    public void translateText(String inputText, TextView outputText) {
        // Call the translate method of the translator
        englishGermanTranslator.translate(inputText)
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String translatedText) {
                        // Update translated text in TextView
                        outputText.setText(translatedText);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Show error message if translation fails
                        outputText.setText("Error");
                        Toast.makeText(requireActivity(), "Dịch Thuật B Lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
