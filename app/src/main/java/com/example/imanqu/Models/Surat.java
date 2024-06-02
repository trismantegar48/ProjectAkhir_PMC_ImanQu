package com.example.imanqu.Models;

import com.google.gson.annotations.SerializedName;

public class Surat {
    @SerializedName("nomor")
    private int nomor;
    @SerializedName("namaLatin")
    private String namaLatin;
    @SerializedName("jumlahAyat")
    private int jumlahAyat;
    @SerializedName("arti")
    private String arti;

    // Getters and Setters
    public int getNomor() {
        return nomor;
    }

    public void setNomor(int nomor) {
        this.nomor = nomor;
    }

    public String getNamaLatin() {
        return namaLatin;
    }

    public void setNamaLatin(String namaLatin) {
        this.namaLatin = namaLatin;
    }

    public int getJumlahAyat() {
        return jumlahAyat;
    }

    public void setJumlahAyat(int jumlahAyat) {
        this.jumlahAyat = jumlahAyat;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }
}
