package com.example.imanqu.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imanqu.Models.SuratDetail;
import com.example.imanqu.R;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    private List<SuratDetail.Ayat> ayatList;

    public DetailAdapter(List<SuratDetail.Ayat> ayatList) {
        this.ayatList = ayatList;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ayat, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        SuratDetail.Ayat ayat = ayatList.get(position);
        holder.tvNomorAyat.setText(String.valueOf(ayat.getNomorAyat()));
        holder.tvTeksArab.setText(ayat.getTeksArab());
        holder.tvTeksLatin.setText(String.valueOf(ayat.getTeksLatin())); // Mengubah ke String
        holder.tvTeksIndonesia.setText(ayat.getTeksIndonesia());
    }

    @Override
    public int getItemCount() {
        return ayatList.size();
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomorAyat, tvTeksArab, tvTeksLatin, tvTeksIndonesia;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomorAyat = itemView.findViewById(R.id.tvnomorAyat);
            tvTeksArab = itemView.findViewById(R.id.tvteksArab);
            tvTeksLatin = itemView.findViewById(R.id.tvteksLatin);
            tvTeksIndonesia = itemView.findViewById(R.id.tvteksIndonesia);
        }
    }
}
