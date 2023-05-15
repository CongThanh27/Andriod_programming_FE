package com.marwaeltayeb.souq.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.model.Diachi;
import com.marwaeltayeb.souq.view.CheckoutActivity;

import java.util.List;

public class DiaChiAdapter extends RecyclerView.Adapter<DiaChiAdapter.DiaChiAdapterHolder> {
    private List<Diachi> diachis;
    private Context mContext;
    private TextView diachi1,sodienthoai;
    private LinearLayout linearLayoutcapnhat,linearLayoutthongtin;
    private RecyclerView recyclerDiaChi;
    Button btncapnhatthongtin2,btncapnhatthongtin3,btncapnhatthongtin;


    public DiaChiAdapter(List<Diachi> diachis, Context mContext, TextView diachi1, TextView sodienthoai, LinearLayout linearLayoutcapnhat, LinearLayout linearLayoutthongtin, RecyclerView recyclerDiaChi, Button btncapnhatthongtin2, Button btncapnhatthongtin3, Button btncapnhatthongtin) {
        this.diachis = diachis;
        this.mContext = mContext;
        this.diachi1 = diachi1;
        this.sodienthoai = sodienthoai;
        this.linearLayoutcapnhat = linearLayoutcapnhat;
        this.linearLayoutthongtin = linearLayoutthongtin;
        this.recyclerDiaChi = recyclerDiaChi;
        this.btncapnhatthongtin2 = btncapnhatthongtin2;
        this.btncapnhatthongtin3 = btncapnhatthongtin3;
        this.btncapnhatthongtin = btncapnhatthongtin;
    }

    @NonNull
    @Override
    public DiaChiAdapter.DiaChiAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_diachi, null);
        return new DiaChiAdapter.DiaChiAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaChiAdapter.DiaChiAdapterHolder holder, int position) {
        Diachi diachi = diachis.get(position);
        if (diachi == null) {
            return;
        }
        else {
            holder.txtAddress.setText(diachi.getAddress());
            holder.txtPhone.setText(diachi.getPhone());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    diachi1.setText(diachi.getAddress());
                    sodienthoai.setText(diachi.getPhone());
                     linearLayoutcapnhat.setVisibility(View.GONE);
                     linearLayoutthongtin.setVisibility(View.VISIBLE);
                     recyclerDiaChi.setVisibility(View.GONE);
                     btncapnhatthongtin2.setVisibility(View.GONE);
                     btncapnhatthongtin3.setVisibility(View.VISIBLE);
                     btncapnhatthongtin.setVisibility(View.GONE);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if (diachis != null) {
            return diachis.size();
        }
        return 0;
    }
    class DiaChiAdapterHolder extends RecyclerView.ViewHolder{
        TextView txtAddress,txtPhone;
        LinearLayout linearLayout;
        public DiaChiAdapterHolder(@NonNull View itemView) {
            super(itemView);
            txtAddress = itemView.findViewById(R.id.txtdiachi);
            txtPhone = itemView.findViewById(R.id.txtSodienthoai);
            linearLayout = itemView.findViewById(R.id.lnlDiachi);
        }
    }
}
