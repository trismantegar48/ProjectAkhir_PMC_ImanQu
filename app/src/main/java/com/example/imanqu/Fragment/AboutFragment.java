package com.example.imanqu.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.imanqu.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AboutFragment extends Fragment {
    private TextView textViewAbout;
    private ProgressBar progressBar;

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler handler = Handler.createAsync(Looper.myLooper());

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        // Inisialisasi Tampilan
        textViewAbout = view.findViewById(R.id.textViewAbout);
        progressBar = view.findViewById(R.id.progressBar);

        textViewAbout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        executor.execute(() -> {
            handler.post(() -> {
                textViewAbout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            });
        });

        return view;
    }
}
