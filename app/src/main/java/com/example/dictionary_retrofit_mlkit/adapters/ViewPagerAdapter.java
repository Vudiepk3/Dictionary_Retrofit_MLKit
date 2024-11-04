package com.example.dictionary_retrofit_mlkit.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dictionary_retrofit_mlkit.MLKitFragment;
import com.example.dictionary_retrofit_mlkit.RetrofitFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1){
            return new MLKitFragment();
        }
        return new RetrofitFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}