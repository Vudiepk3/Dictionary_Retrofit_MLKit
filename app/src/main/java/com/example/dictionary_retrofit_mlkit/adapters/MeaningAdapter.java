package com.example.dictionary_retrofit_mlkit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dictionary_retrofit_mlkit.R;
import com.example.dictionary_retrofit_mlkit.models.Meaning;
import java.util.List;

// Adapter cho RecyclerView để hiển thị danh sách các Meaning
public class MeaningAdapter extends RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder> {

    private List<Meaning> meaningList;

    // Constructor - Khởi tạo adapter với danh sách Meaning
    public MeaningAdapter(List<Meaning> meaningList) {
        this.meaningList = meaningList;
    }

    // ViewHolder cho RecyclerView
    public static class MeaningViewHolder extends RecyclerView.ViewHolder {
        private final TextView partOfSpeechTextView;
        private final TextView definitionsTextView;
        private final TextView synonymsTitleTextView;
        private final TextView synonymsTextView;
        private final TextView antonymsTitleTextView;
        private final TextView antonymsTextView;

        // Constructor của ViewHolder
        public MeaningViewHolder(View itemView) {
            super(itemView);
            partOfSpeechTextView = itemView.findViewById(R.id.part_of_speech_textview);
            definitionsTextView = itemView.findViewById(R.id.definitions_textview);
            synonymsTitleTextView = itemView.findViewById(R.id.synonyms_title_textview);
            synonymsTextView = itemView.findViewById(R.id.synonyms_textview);
            antonymsTitleTextView = itemView.findViewById(R.id.antonyms_title_textview);
            antonymsTextView = itemView.findViewById(R.id.antonyms_textview);
        }

        // Phương thức để bind dữ liệu với các view
        public void bind(Meaning meaning) {
            // Đặt phần văn bản cho phần từ loại (part of speech)
            partOfSpeechTextView.setText(meaning.getPartOfSpeech());

            // Đặt phần văn bản cho định nghĩa, với mỗi định nghĩa được đánh số thứ tự
            definitionsTextView.setText(
                    meaning.getDefinitions().stream()
                            .map(definition -> {
                                int currentIndex = meaning.getDefinitions().indexOf(definition);
                                return (currentIndex + 1) + ". " + definition.getDefinition();
                            })
                            .reduce((acc, element) -> acc + "\n\n" + element)
                            .orElse("")
            );

            // Hiển thị hoặc ẩn phần từ đồng nghĩa (synonyms) dựa trên danh sách từ đồng nghĩa
            if (meaning.getSynonyms().isEmpty()) {
                synonymsTitleTextView.setVisibility(View.GONE);
                synonymsTextView.setVisibility(View.GONE);
            } else {
                synonymsTitleTextView.setVisibility(View.VISIBLE);
                synonymsTextView.setVisibility(View.VISIBLE);
                synonymsTextView.setText(String.join(", ", meaning.getSynonyms()));
            }

            // Hiển thị hoặc ẩn phần từ trái nghĩa (antonyms) dựa trên danh sách từ trái nghĩa
            if (meaning.getAntonyms().isEmpty()) {
                antonymsTitleTextView.setVisibility(View.GONE);
                antonymsTextView.setVisibility(View.GONE);
            } else {
                antonymsTitleTextView.setVisibility(View.VISIBLE);
                antonymsTextView.setVisibility(View.VISIBLE);
                antonymsTextView.setText(String.join(", ", meaning.getAntonyms()));
            }
        }
    }

    // Cập nhật dữ liệu mới và thông báo thay đổi
    public void updateNewData(List<Meaning> newMeaningList) {
        this.meaningList = newMeaningList;
        notifyDataSetChanged();
    }

    // Tạo ViewHolder mới khi RecyclerView cần
    @NonNull
    @Override
    public MeaningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho mỗi item trong RecyclerView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meaning_recycler_row, parent, false);
        return new MeaningViewHolder(itemView);
    }

    // Gán dữ liệu cho ViewHolder tại vị trí cụ thể
    @Override
    public void onBindViewHolder(MeaningViewHolder holder, int position) {
        holder.bind(meaningList.get(position));
    }

    // Trả về số lượng item trong RecyclerView
    @Override
    public int getItemCount() {
        return meaningList.size();
    }
}
