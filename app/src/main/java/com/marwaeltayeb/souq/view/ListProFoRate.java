package com.marwaeltayeb.souq.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.adapter.ProInOrderAdapter;
import com.marwaeltayeb.souq.adapter.ProInRateAdapter;
import com.marwaeltayeb.souq.model.ProductInOrder;

import java.text.DecimalFormat;
import java.util.List;

public class ListProFoRate extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView  total, totalitem;
    TextView text_action2,text_action3;
    ProInRateAdapter proInRateAdapter;
    private List<ProductInOrder> proList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pro_fo_rate);
        //Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        proList = (List<ProductInOrder>) bundle.getSerializable("LOD123");
        //Ánh xạ
        recyclerView = findViewById(R.id.recyclerview);
        text_action2 = findViewById(R.id.text_action2);
        text_action3 = findViewById(R.id.text_action3);
        total=(TextView) findViewById(R.id.total_amt_tv);
        totalitem=(TextView) findViewById(R.id.total_items_tv);
        //Xử lý
        //chạy vòng for qua proList tính sum price
        int sum = 0;
        for (int i = 0; i < proList.size(); i++) {
            sum += proList.get(i).getProductPrice()*proList.get(i).getQuantity();
        }
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(sum);
        total.setText("Tổng tiền đã trả:"+formattedPrice+"đ");
        totalitem.setText("Số lượng sản phẩm: "+proList.size());
        //Click
        text_action2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tạo ra một Intent mới để mở một trang web đích trên trình duyệt web mặc định của thiết bị.
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.facebook.com/profile.php?id=100011055702857"));
                startActivity(intent);
            }
        });
        text_action3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tạo một Intent mới với hành động Intent.ACTION_CALL, chỉ định rằng Intent này sẽ thực hiện một cuộc gọi điện thoại.
                Intent intent = new Intent(Intent.ACTION_CALL);
                //Uri.parse("tel:" + "0387102216") là một phương thức tạo một đối tượng URI (Uniform Resource Identifier)
                // cho một số điện thoại được định dạng theo chuẩn URI.
                intent.setData(Uri.parse("tel:" + "0387102216"));
                startActivity(intent);
            }
        });

        setUpRecycleView(proList);

    }
    private void setUpRecycleView( List<ProductInOrder> proList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        proInRateAdapter = new ProInRateAdapter( proList, this);
        recyclerView.setAdapter(proInRateAdapter);
    }
}