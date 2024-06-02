package com.example.imanqu.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuratDetail {
    @SerializedName("ayat")
    private List<Ayat> ayatList;

    public List<Ayat> getAyatList() {
        return ayatList;
    }

    public static class Ayat {

        @SerializedName("nomorAyat")
        private int nomorAyat;

        @SerializedName("teksArab")
        private String teksArab;

        @SerializedName("teksLatin")
        private String teksLatin;

        @SerializedName("teksIndonesia")
        private String teksIndonesia;

        // Getters and Setters

        public int getNomorAyat() {
            return nomorAyat;
        }

        public String getTeksArab() {
            return teksArab;
        }

        public String getTeksLatin() {
            return teksLatin;
        }

        public String getTeksIndonesia() {
            return teksIndonesia;
        }


        }
    }

