package com.example.imanqu.Response;

import com.example.imanqu.Models.SuratDetail;
import com.google.gson.annotations.SerializedName;

public class DetailResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private SuratDetail data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public SuratDetail getData() {
        return data;
    }
}
