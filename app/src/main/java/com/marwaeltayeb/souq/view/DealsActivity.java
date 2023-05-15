package com.marwaeltayeb.souq.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.adapter.DoiTacAdapter;
import com.marwaeltayeb.souq.adapter.NCCAdapter;


public class DealsActivity extends AppCompatActivity {



    private static final String[] mImageUrls = {
            "https://cdn0.fahasa.com/media/wysiwyg/Thang-04-2023/McBooksT4_Email_600x350.jpg",
            "https://cdn0.fahasa.com/media/wysiwyg/Thang-04-2023/Platinum_minhlong_Email_600x350up.jpg",
            "https://cdn0.fahasa.com/media/wysiwyg/Thang-04-2023/SLIVER_NCCMOREPRODUCTION_T42023_Email_600x350.png",
            "https://cdn0.fahasa.com/media/wysiwyg/Thang-04-2023/PLATINUM_NCC1980_T42023_Email_600x350.png",
            "https://cdn0.fahasa.com/media/wysiwyg/Thang-04-2023/McBooksT4_Email_600x350.jpg",
            "https://cdn0.fahasa.com/media/wysiwyg/Thang-04-2023/Platinum_minhlong_Email_600x350up.jpg",
            "https://cdn0.fahasa.com/media/wysiwyg/Thang-04-2023/SLIVER_NCCMOREPRODUCTION_T42023_Email_600x350.png",
            "https://cdn0.fahasa.com/media/wysiwyg/Thang-04-2023/PLATINUM_NCC1980_T42023_Email_600x350.png"
    };

    private static final String[] mImageUrls1 = {
            "https://cdn0.fahasa.com/media/wysiwyg/Thang-04-2023/pnj%20600x350.png",
            "https://cdn0.fahasa.com/media/wysiwyg/Thang-04-2023/FAHASA-Potico600X350.png",
            "https://cdn0.fahasa.com/media/wysiwyg/Thang-04-2023/ZaloPayT523_600x350_Email.jpg",
            "https://cdn0.fahasa.com/media/wysiwyg/Thang-04-2023/pnj%20600x350.png",
            "https://cdn0.fahasa.com/media/wysiwyg/Thang-04-2023/FAHASA-Potico600X350.png",
            "https://cdn0.fahasa.com/media/wysiwyg/Thang-04-2023/ZaloPayT523_600x350_Email.jpg"
    };
    private TextView txtChiTietTheLe,txtChiTietTheLe1,txtChiTietTheLe2,txtChiTietTheLe3,txtChiTietTheLe4,txtChiTietTheLe5;
    private TextView txtLayMaNgay1,txtLayMaNgay2,txtLayMaNgay3,txtLayMaNgay4,txtLayMaNgay5,txtLayMaNgay6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);


        RecyclerView recyclerView = findViewById(R.id.recyclerViewDeal);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new NCCAdapter(mImageUrls));

        RecyclerView recyclerView1 = findViewById(R.id.recyclerViewDoiTac);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView1.setAdapter(new DoiTacAdapter(mImageUrls1));


        txtChiTietTheLe = (TextView) findViewById(R.id.txtChiTietTheLe);
        txtChiTietTheLe1 = (TextView) findViewById(R.id.txtChiTietTheLe1);
        txtChiTietTheLe2 = (TextView) findViewById(R.id.txtChiTietTheLe2);
        txtChiTietTheLe3 = (TextView) findViewById(R.id.txtChiTietTheLe3);
        txtChiTietTheLe4 = (TextView) findViewById(R.id.txtChiTietTheLe4);
        txtChiTietTheLe5 = (TextView) findViewById(R.id.txtChiTietTheLe5);
        txtLayMaNgay1 = (TextView) findViewById(R.id.txtLayMaNgay1);
        txtLayMaNgay2 = (TextView) findViewById(R.id.txtLayMaNgay2);
        txtLayMaNgay3 = (TextView) findViewById(R.id.txtLayMaNgay3);
        txtLayMaNgay4 = (TextView) findViewById(R.id.txtLayMaNgay4);
        txtLayMaNgay5 = (TextView) findViewById(R.id.txtLayMaNgay5);
        txtLayMaNgay6 = (TextView) findViewById(R.id.txtLayMaNgay6);

        txtChiTietTheLe.setOnClickListener(v -> {
            Intent chitietIntent = new Intent(this, DealRuleActivity.class);
            startActivity(chitietIntent);
        });

        txtChiTietTheLe1.setOnClickListener(v -> {
            Intent chitietIntent = new Intent(this, DealRuleActivity.class);
            startActivity(chitietIntent);
        });

        txtChiTietTheLe2.setOnClickListener(v -> {
            Intent chitietIntent = new Intent(this, DealRuleActivity.class);
            startActivity(chitietIntent);
        });

        txtChiTietTheLe3.setOnClickListener(v -> {
            Intent chitietIntent = new Intent(this, DealRuleActivity.class);
            startActivity(chitietIntent);
        });

        txtChiTietTheLe4.setOnClickListener(v -> {
            Intent chitietIntent = new Intent(this, DealRuleActivity.class);
            startActivity(chitietIntent);
        });

        txtChiTietTheLe5.setOnClickListener(v -> {
            Intent chitietIntent = new Intent(this, DealRuleActivity.class);
            startActivity(chitietIntent);
        });


        txtLayMaNgay1.setOnClickListener(v -> {
            Toast.makeText(this, "Lấy mã thành công", Toast.LENGTH_SHORT).show();
        });

        txtLayMaNgay2.setOnClickListener(v -> {
            Toast.makeText(this, "Lấy mã thành công", Toast.LENGTH_SHORT).show();
        });

        txtLayMaNgay3.setOnClickListener(v -> {
            Toast.makeText(this, "Lấy mã thành công", Toast.LENGTH_SHORT).show();
        });

        txtLayMaNgay4.setOnClickListener(v -> {
            Toast.makeText(this, "Lấy mã thành công", Toast.LENGTH_SHORT).show();
        });

        txtLayMaNgay5.setOnClickListener(v -> {
            Toast.makeText(this, "Lấy mã thành công", Toast.LENGTH_SHORT).show();
        });

        txtLayMaNgay6.setOnClickListener(v -> {
            Toast.makeText(this, "Lấy mã thành công", Toast.LENGTH_SHORT).show();
        });


    }
}