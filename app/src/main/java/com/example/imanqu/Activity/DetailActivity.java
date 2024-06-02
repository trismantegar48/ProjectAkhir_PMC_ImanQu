package com.example.imanqu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imanqu.Adapter.DetailAdapter;
import com.example.imanqu.Api.ApiService;
import com.example.imanqu.Models.SuratDetail;
import com.example.imanqu.R;
import com.example.imanqu.Response.DetailResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DetailAdapter detailAdapter;
    List<SuratDetail.Ayat> ayatList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        recyclerView = findViewById(R.id.recyclerView);
        detailAdapter = new DetailAdapter(ayatList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(detailAdapter);

        int nomorSurat = getIntent().getIntExtra("nomorSurat", 1); // Get the Surat number from Intent

        getSuratDetails(nomorSurat);

        ImageView backImageView = findViewById(R.id.back);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat Intent untuk kembali ke MainActivity
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                // Menjalankan Intent
                startActivity(intent);
                // Menutup DetailActivity agar tidak kembali lagi setelah kembali ke MainActivity
                finish();
            }
        });
    }

    private void getSuratDetails(int nomorSurat) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://equran.id/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<DetailResponse> call = apiService.getSurat(nomorSurat);

        call.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SuratDetail suratDetail = response.body().getData();
                    if (suratDetail != null) {
                        ayatList.addAll(suratDetail.getAyatList());
                        detailAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                Log.d("ppp", t.getMessage());
            }
        });
    }
}