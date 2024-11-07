package com.example.dictionary_retrofit_mlkit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary_retrofit_mlkit.adapters.MeaningAdapter;
import com.example.dictionary_retrofit_mlkit.models.WordResult;
import com.example.dictionary_retrofit_mlkit.retrofit.RetrofitInstance;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class RetrofitFragment extends Fragment {
    // Biến cho adapter để hiển thị danh sách các Meaning
    private MeaningAdapter adapter;

    // ExecutorService để thực hiện các tác vụ không đồng bộ
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    // Handler để đăng các tác vụ trở lại luồng chính
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    // Views
    private Button searchBtn;
    private EditText searchInput;
    private TextView wordTextView;
    private TextView phoneticTextView;
    private ProgressBar progressBar;

    public RetrofitFragment() {
        // Required empty public constructor
    }

    public static RetrofitFragment newInstance(String param1, String param2) {
        RetrofitFragment fragment = new RetrofitFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retrofit, container, false);

        // Khởi tạo các view bằng findViewById
        searchBtn = view.findViewById(R.id.searchBtn);
        searchInput = view.findViewById(R.id.searchInput);
        wordTextView = view.findViewById(R.id.word_textview);
        phoneticTextView = view.findViewById(R.id.phonetic_textview);
        progressBar = view.findViewById(R.id.progressBar);
        RecyclerView meaningRecyclerView = view.findViewById(R.id.meaning_recycler_view);

        // Thiết lập sự kiện click cho nút tìm kiếm
        searchBtn.setOnClickListener(v -> {
            String word = searchInput.getText().toString();
            getMeaning(word);
        });

        // Khởi tạo adapter với danh sách rỗng và thiết lập cho RecyclerView
        adapter = new MeaningAdapter(Collections.emptyList());
        meaningRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        meaningRecyclerView.setAdapter(adapter);

        return view;
    }

    private void getMeaning(String word) {
        setInProgress(true); // Hiển thị tiến trình

        // Thực hiện cuộc gọi API trên luồng nền
        executorService.execute(() -> {
            try {
                // Gọi API để lấy nghĩa của từ
                Response<List<WordResult>> response = RetrofitInstance.dictionaryApi.getMeaning(word).execute();
                if (response.body() == null) {
                    throw new Exception(); // Ném ngoại lệ nếu không có kết quả
                }
                // Chuyển kết quả trở lại luồng chính để cập nhật giao diện người dùng
                mainHandler.post(() -> {
                    setInProgress(false); // Ẩn tiến trình
                    WordResult wordResult = response.body().get(0);
                    setUI(wordResult); // Cập nhật giao diện với kết quả
                });
            } catch (Exception e) {
                // Xử lý lỗi và thông báo cho người dùng
                mainHandler.post(() -> {
                    setInProgress(false); // Ẩn tiến trình
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    // Phương thức để cập nhật giao diện người dùng với kết quả tìm kiếm
    private void setUI(WordResult response) {
        wordTextView.setText(response.getWord());
        phoneticTextView.setText(response.getPhonetic());
        adapter.updateNewData(response.getMeanings());
    }

    // Phương thức để hiển thị hoặc ẩn tiến trình
    private void setInProgress(boolean inProgress) {
        if (inProgress) {
            searchBtn.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            searchBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
