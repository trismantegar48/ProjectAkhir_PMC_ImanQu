package com.example.imanqu.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imanqu.Activity.DetailActivity;
import com.example.imanqu.Models.Surat;
import com.example.imanqu.R;

import java.util.ArrayList;
import java.util.List;

public class SuratAdapter extends RecyclerView.Adapter<SuratAdapter.SuratViewHolder> {
    private List<Surat> suratList;
    private List<Surat> originalsuratList;
    Context context;


    public SuratAdapter(List<Surat> suratList, Context context) {
        this.suratList = suratList;
        this.context = context;
        this.originalsuratList = new ArrayList<>(suratList);
    }

    @NonNull
    @Override
    public SuratViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_surah, parent, false);
        return new SuratViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuratViewHolder holder, int position) {
        Surat surat = suratList.get(position);
        holder.tvNumber.setText(String.valueOf(surat.getNomor()));
        holder.tvNamaLatin.setText(surat.getNamaLatin());
        holder.tvJumlahAyat.setText(String.valueOf(surat.getJumlahAyat()));
        holder.tvArti.setText(surat.getArti());

        holder.btnBaca.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("nomorSurat", surat.getNomor());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return suratList.size();
    }

    static class SuratViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNumber, tvNamaLatin, tvJumlahAyat, tvArti;
        private Button btnBaca;

        public SuratViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvNamaLatin = itemView.findViewById(R.id.tvNamaLatin);
            tvJumlahAyat = itemView.findViewById(R.id.tvJumlahAyat);
            tvArti = itemView.findViewById(R.id.tvArti);
            btnBaca = itemView.findViewById(R.id.btnBaca);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String text) {
        suratList.clear();
        if (text.isEmpty()) {
            suratList.addAll(originalsuratList);
        } else {
            text = text.toLowerCase();
            for (Surat surat : originalsuratList) {
                if (surat.getNamaLatin().toLowerCase().contains(text)) {
                    suratList.add(surat);
                }
            }
        }
        notifyDataSetChanged();
    }

}
