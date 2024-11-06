package com.example.dictionary_retrofit_mlkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.dictionary_retrofit_mlkit.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout; // Khai báo biến TabLayout để chứa các tab
    private ViewPager2 viewPager2; // Khai báo biến ViewPager2 để hiển thị các fragment
    private ViewPagerAdapter adapter; // Khai báo biến adapter để quản lý các fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Nạp layout cho activity

        // Khởi tạo các thành phần giao diện người dùng
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);

        // Thêm các tab vào TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Retrofit_Dictionary")); // Tab cho Retrofit Dictionary
        tabLayout.addTab(tabLayout.newTab().setText("MLKit_Dictionary")); // Tab cho MLKit Dictionary

        // Khởi tạo FragmentManager để quản lý các fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ViewPagerAdapter(fragmentManager, getLifecycle()); // Khởi tạo adapter với FragmentManager và lifecycle
        viewPager2.setAdapter(adapter); // Thiết lập adapter cho ViewPager2

        // Thiết lập sự kiện khi một tab được chọn
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition()); // Chuyển đến fragment tương ứng với tab đã chọn
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Xử lý khi tab không được chọn (có thể bỏ qua)
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Xử lý khi tab được chọn lại (có thể bỏ qua)
            }
        });

        // Thiết lập sự kiện khi trang trong ViewPager2 được thay đổi
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position)); // Cập nhật tab được chọn tương ứng với trang
            }
        });
    }
}
