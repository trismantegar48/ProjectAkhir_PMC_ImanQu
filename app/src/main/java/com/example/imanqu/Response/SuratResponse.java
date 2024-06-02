package com.example.imanqu.Response;

import com.example.imanqu.Models.Surat;

import java.util.List;

public class SuratResponse {
    private List<Surat> data;

    public List<Surat> getData() {
        return data;
    }

    public void setData(List<Surat> data) {
        this.data = data;
    }
}
