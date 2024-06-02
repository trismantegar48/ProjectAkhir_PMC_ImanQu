package com.example.imanqu.Api;

import com.example.imanqu.Response.DetailResponse;
import com.example.imanqu.Response.SuratResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("surat")
    Call<SuratResponse> getAllSurat();

    @GET("/api/v2/surat/{nomor}")
    Call<DetailResponse> getSurat(@Path("nomor") int nomor);
}
