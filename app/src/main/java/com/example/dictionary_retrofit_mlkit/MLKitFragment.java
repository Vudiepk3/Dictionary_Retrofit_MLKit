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

import androidx.fragment.app.Fragment;

import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class MLKitFragment extends Fragment {

    private Translator englishGermanTranslator; // Khai báo biến trình dịch

    public MLKitFragment() {
        // Constructor rỗng cần thiết
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Nạp layout cho fragment
        View view = inflater.inflate(R.layout.fragment_m_l_kit, container, false);

        // Khởi tạo các thành phần giao diện người dùng
        EditText inputEditText = view.findViewById(R.id.inputEditText);
        Button translateButton = view.findViewById(R.id.translateButton);
        TextView outputText = view.findViewById(R.id.outputText);

        // Thiết lập tùy chọn trình dịch (ngôn ngữ nguồn và ngôn ngữ đích)
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH) // Ngôn ngữ nguồn là tiếng Anh
                .setTargetLanguage(TranslateLanguage.GERMAN)  // Ngôn ngữ đích là tiếng Đức
                .build();

        // Khởi tạo trình dịch
        englishGermanTranslator = Translation.getClient(options);

        // Thiết lập điều kiện cho việc tải xuống mô hình (chỉ tải xuống khi kết nối Wi-Fi)
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();

        // Tải mô hình nếu cần
        englishGermanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(aVoid -> {
                    // Khi mô hình tải xuống thành công
                    // Thiết lập trình xử lý click cho nút dịch
                    translateButton.setOnClickListener(v -> {
                        String input = inputEditText.getText().toString(); // Lấy văn bản đầu vào
                        translateText(input, outputText); // Gọi phương thức dịch
                    });
                })
                .addOnFailureListener(e -> {
                    // Hiển thị thông báo lỗi nếu việc tải mô hình thất bại
                    Toast.makeText(requireActivity(), "Tải mô hình thất bại", Toast.LENGTH_SHORT).show();
                });
        return view; // Trả về view đã được nạp
    }

    // Phương thức dịch văn bản
    @SuppressLint("SetTextI18n")
    public void translateText(String inputText, TextView outputText) {
        // Gọi phương thức dịch của trình dịch
        englishGermanTranslator.translate(inputText)
                .addOnSuccessListener(outputText::setText) // Sử dụng method reference để cập nhật TextView
                .addOnFailureListener(e -> {
                    // Hiển thị thông báo lỗi nếu dịch thất bại

                    outputText.setText("Error");
                    Toast.makeText(requireActivity(), "Dịch Thuật Bị Lỗi", Toast.LENGTH_SHORT).show();
                });
    }
}
