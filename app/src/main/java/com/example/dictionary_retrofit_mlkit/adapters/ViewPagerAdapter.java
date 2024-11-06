package com.example.dictionary_retrofit_mlkit.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dictionary_retrofit_mlkit.MLKitFragment;
import com.example.dictionary_retrofit_mlkit.RetrofitFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    // Constructor nhận vào FragmentManager và Lifecycle
    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle); // Gọi constructor của lớp cha với fragmentManager và lifecycle
    }

    // Phương thức tạo fragment dựa trên vị trí
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Kiểm tra vị trí và trả về fragment tương ứng
        if (position == 1) {
            return new MLKitFragment(); // Nếu vị trí là 1, tạo và trả về MLKitFragment
        }
        return new RetrofitFragment(); // Ngược lại, tạo và trả về RetrofitFragment
    }

    // Phương thức trả về tổng số item (fragment) trong ViewPager2
    @Override
    public int getItemCount() {
        return 2; // Chúng ta có 2 fragment: RetrofitFragment và MLKitFragment
    }
}
