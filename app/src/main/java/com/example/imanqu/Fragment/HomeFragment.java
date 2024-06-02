package com.example.imanqu.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imanqu.Adapter.SuratAdapter;
import com.example.imanqu.Api.ApiConfig;
import com.example.imanqu.Api.ApiService;
import com.example.imanqu.Models.Surat;
import com.example.imanqu.R;
import com.example.imanqu.Response.SuratResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ApiService apiService;
    private List<Surat> suratList = new ArrayList<>();
    private SuratAdapter suratAdapter;
    private LinearLayout main_container;
    private ProgressBar progressBar;
    private SearchView searchView;
    private TextView errorTextView;
    private Button retryButton;

    private Executor executors = Executors.newSingleThreadExecutor();
    private Handler handler = Handler.createAsync(Looper.myLooper());

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        main_container = view.findViewById(R.id.main_container);
        progressBar = view.findViewById(R.id.progressBar);
        searchView = view.findViewById(R.id.searchView);
        errorTextView = view.findViewById(R.id.errorTextView);
        retryButton = view.findViewById(R.id.retryButton);

        apiService = ApiConfig.getRetrofitInstance().create(ApiService.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        main_container.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        retryButton.setOnClickListener(v -> checkInternetAndFetchData());

        checkInternetAndFetchData();

        // buat searchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                suratAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                suratAdapter.filter(newText);
                return false;
            }
        });

        return view;
    }

    private void checkInternetAndFetchData() {
        if (isNetworkAvailable()) {
            errorTextView.setVisibility(View.GONE);
            retryButton.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            executors.execute(() -> {
                try {
                    Thread.sleep(2000); // Menunggu 2 detik
                    fetchSuratData();
                    handler.post(() -> {
                        main_container.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } else {
            main_container.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            errorTextView.setVisibility(View.VISIBLE);
            retryButton.setVisibility(View.VISIBLE);
        }
    }

    // memeriksa apakah perangkat Android saat ini memiliki koneksi jaringan
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE); // layanan yang menyediakan akses ke status koneksi jaringan
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Method untuk melakukan pemanggilan API dan menampilkan data surat
    private void fetchSuratData() {
        Call<SuratResponse> call = apiService.getAllSurat();
        call.enqueue(new Callback<SuratResponse>() {
            @Override
            public void onResponse(Call<SuratResponse> call, Response<SuratResponse> response) {
                if (response.isSuccessful()) {
                    SuratResponse data = response.body();
                    if (data != null && data.getData() != null && !data.getData().isEmpty()) {
                        suratList.addAll(data.getData());
                        suratAdapter = new SuratAdapter(suratList, getContext());
                        recyclerView.setAdapter(suratAdapter);
                        suratAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "No data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuratResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", t.getMessage(), t);
            }
        });
    }
}
