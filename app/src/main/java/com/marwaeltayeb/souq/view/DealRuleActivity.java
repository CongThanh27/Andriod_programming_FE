package com.marwaeltayeb.souq.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.marwaeltayeb.souq.R;

public class DealRuleActivity extends AppCompatActivity {

    TextView txtMuaNgay,txtThamGiaNgay,txtDangNhap,txtDangKy,txtDonHang,txtTaiKhoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_rule);

        txtMuaNgay = (TextView) findViewById(R.id.txtMuaNgay);
        txtThamGiaNgay = (TextView) findViewById(R.id.txtThamGiaNgay);
        txtDangNhap = (TextView) findViewById(R.id.txtDangNhap);
        txtDangKy = (TextView) findViewById(R.id.txtDangKy);
        txtDonHang = (TextView) findViewById(R.id.txtDonHang);
        txtTaiKhoan = (TextView) findViewById(R.id.txtTaiKhoan);

        txtMuaNgay.setOnClickListener(v -> {
            Intent chitietIntent = new Intent(this, ProductActivity.class);
            startActivity(chitietIntent);

        });

        txtThamGiaNgay.setOnClickListener(v -> {
            Intent chitietIntent = new Intent(this, ProductActivity.class);
            startActivity(chitietIntent);

        });

        txtDangNhap.setOnClickListener(v -> {
            Intent chitietIntent = new Intent(this, LoginActivity.class);
            startActivity(chitietIntent);

        });

        txtDangKy.setOnClickListener(v -> {
            Intent chitietIntent = new Intent(this, SignUpActivity.class);
            startActivity(chitietIntent);

        });

        txtDonHang.setOnClickListener(v -> {
            Intent chitietIntent = new Intent(this, OrdersActivity.class);
            startActivity(chitietIntent);

        });

        txtTaiKhoan.setOnClickListener(v -> {
            Intent chitietIntent = new Intent(this, AccountActivity.class);
            startActivity(chitietIntent);

        });


    }
}