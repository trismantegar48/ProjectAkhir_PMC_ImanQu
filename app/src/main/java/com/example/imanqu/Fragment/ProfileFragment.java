package com.example.imanqu.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.imanqu.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProfileFragment extends Fragment {
    private TextView textViewProfile;
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler handler = Handler.createAsync(Looper.myLooper());

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inisialisasi tampilan
        textViewProfile = view.findViewById(R.id.textViewProfile);
        textViewProfile.setVisibility(View.GONE);

        executor.execute(() -> {
            // Ambil nama pengguna dari SharedPreferences
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("LoggedInUser", "User");

            handler.post(() -> {
                textViewProfile.setText("Selamat Datang " + username);
                textViewProfile.setVisibility(View.VISIBLE);
            });
        });

        return view;
    }
}
