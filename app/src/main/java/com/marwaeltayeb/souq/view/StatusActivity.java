package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.utils.Constant.ORDER;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCTID;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.adapter.ProInOrderAdapter;
import com.marwaeltayeb.souq.adapter.ThongTinDonHangAdapter;
import com.marwaeltayeb.souq.databinding.ActivityStatusBinding;
import com.marwaeltayeb.souq.model.Order;
import com.marwaeltayeb.souq.model.PlacedOrderModel;
import com.marwaeltayeb.souq.model.ProductInCart;
import com.marwaeltayeb.souq.model.ProductInOrder;
import com.marwaeltayeb.souq.storage.LoginUtils;

import com.marwaeltayeb.souq.viewmodel.HuyDonHangViewModel;
import com.marwaeltayeb.souq.viewmodel.OrderViewModel;
import com.marwaeltayeb.souq.viewmodel.ProductInOrderViewModel;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener {

    private int productId;
    TextView orderNumber;
    TextView orderDate;
    TextView orderStatus;
    TextView userName;
    TextView userAddress;
    TextView userPhone, total, totalitem;
    String status , maDonHang;
    ProInOrderAdapter proInOrderAdapter;
    RecyclerView recyclerView;
    private List<ProductInOrder> proList;
    private ProductInOrderViewModel productInOrderViewModel;
    ImageView OR_T_IV,OC_T_IV,OFD_T_IV,OS_T_IV;
    View v1,v2,v3;
    TextView OR_H_TV,OR_D_TV,OC_H_TV,OC_D_TV,OFD_H_TV,OFD_D_TV,OS_H_TV,OS_D_TV;
    LottieAnimationView OR_A,OC_A,OFD_A,OS_A;
    TextView current_status_header,text_action,text_action1,text_action2,text_action3,text_actionmualai;
    ImageView OR_I,OC_I,OFD_I,OS_I,img_action1,img_action, img_actionmualai;
    LinearLayout action1,action2,action3;
    HuyDonHangViewModel huyDonHangViewModel;
    Order order;
    Bundle bundle;

    private PlacedOrderModel placedOrderModel;
    private  void Anhxa(){
        action1 = findViewById(R.id.layout_action1);
        text_actionmualai = findViewById(R.id.text_actionmualai);
        text_action = findViewById(R.id.text_action);
        text_action1 = findViewById(R.id.text_action1);
        text_action2 = findViewById(R.id.text_action2);
        text_action3 = findViewById(R.id.text_action3);
        img_actionmualai = findViewById(R.id.img_actionmualai);
        img_action = findViewById(R.id.img_action);
        img_action1 = findViewById(R.id.img_action1);
        total=(TextView) findViewById(R.id.total_amt_tv);
        totalitem=(TextView) findViewById(R.id.total_items_tv);
    orderNumber = (TextView) findViewById(R.id.orderNumber);
    orderDate = (TextView) findViewById(R.id.orderDate);
    //orderStatus = (TextView) findViewById(R.id.orderStatus);
    userName = (TextView) findViewById(R.id.userName4);
    userAddress = (TextView) findViewById(R.id.userAddress4);
    userPhone = (TextView) findViewById(R.id.userPhone4);
    recyclerView = findViewById(R.id.recyclerview);
    OR_T_IV = findViewById(R.id.tick_1);
    OC_T_IV = findViewById(R.id.tick_2);
    OFD_T_IV = findViewById(R.id.tick_3);
    OS_T_IV = findViewById(R.id.tick_4);

    v1 = findViewById(R.id.view_1);
    v2 = findViewById(R.id.view_2);
    v3 = findViewById(R.id.view_3);

    OR_H_TV = findViewById(R.id.tv_7);
    OR_D_TV = findViewById(R.id.tv_8);
    OC_H_TV = findViewById(R.id.tv_5);
    OC_D_TV = findViewById(R.id.tv_6);
    OFD_H_TV = findViewById(R.id.tv_3);
    OFD_D_TV = findViewById(R.id.tv_4);
    OS_H_TV = findViewById(R.id.tv_1);
    OS_D_TV = findViewById(R.id.tv_2);

    OR_A = findViewById(R.id.order_placed_anim);
    OC_A = findViewById(R.id.order_confirmed_anim);
    OFD_A = findViewById(R.id.order_out_anim);
    OS_A = findViewById(R.id.order_received_anim);

    current_status_header = findViewById(R.id.current_status);

    OR_I = findViewById(R.id.icon_4);
    OC_I = findViewById(R.id.icon_3);
    OFD_I = findViewById(R.id.icon_2);
    OS_I = findViewById(R.id.icon_1);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_track);
        Anhxa();
        // Receive Order object
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(ORDER);
        status = order.getOrderDateStatus();
        //Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
        bundle = intent.getExtras();
        proList = (List<ProductInOrder>) bundle.getSerializable("listProductInOrder");
        //Set values
        orderNumber.setText(order.getOrderNumber());
        orderDate.setText(order.getOrderDate());
        //orderStatus.setText(order.getOrderDateStatus());
        String username = LoginUtils.getInstance(this).getUserInfo().getName();
        userName.setText(username);
        userAddress.setText(proList.get(0).getShippingAddress());
        userPhone.setText(proList.get(0).getShippingPhone());


        //chạy vòng for qua proList tính sum price
        int sum = 0;
        for (int i = 0; i < proList.size(); i++) {
            sum += proList.get(i).getProductPrice()*proList.get(i).getQuantity();
        }
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(sum);
        total.setText("Tổng tiền phải trả:"+formattedPrice+"đ");
        totalitem.setText("Số lượng sản phẩm: "+proList.size());
        productInOrderViewModel = ViewModelProviders.of(this).get(ProductInOrderViewModel.class);
        setUpRecycleView(proList);
        updateTrackUIAccToStatus(status);
        huyDonHangViewModel = ViewModelProviders.of(this).get(HuyDonHangViewModel.class);
        maDonHang = order.getOrderNumber();
        text_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(StatusActivity.this, "Đánh giá", Toast.LENGTH_SHORT).show();
                //proList
                //Nếu có 1 sản phẩm trong proList
                if(proList.size()==1){
                    Intent intent = new Intent(StatusActivity.this, WriteReviewActivity.class);
                    intent.putExtra("ProductId", proList.get(0).getProductId());
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(StatusActivity.this, ListProFoRate.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("LOD123", (Serializable) proList);
                    intent.putExtras(bundle);
                    startActivity(intent);
               }

            }
        });
        text_action1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            HuyDon(maDonHang);
                Intent intent = new Intent(StatusActivity.this, ProductActivity.class);
                startActivity(intent);

            }
        });
        text_actionmualai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HuyDon(maDonHang);
                Intent intent = new Intent(StatusActivity.this, ProductActivity.class);
                startActivity(intent);

            }
        });
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

//        binding.reOrder.setOnClickListener(this);
    }
    private void HuyDon(String maDonHang ){
        huyDonHangViewModel.getHuyDonHang(maDonHang).observe(this, responseBody -> {
            try {
                Toast.makeText(this, responseBody.string(), Toast.LENGTH_SHORT).show();

                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void setUpRecycleView( List<ProductInOrder> proList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        proInOrderAdapter = new ProInOrderAdapter( proList, this);
        recyclerView.setAdapter(proInOrderAdapter);
    }
    private void updateTrackUIAccToStatus(String status){

        switch (status){
            case "0":
                // title
                current_status_header.setText("ORDE CANCLED");
                //current_status_header.setBackgroundColor(Integer.parseInt("#B73837"));
                // anims
                OR_A.setVisibility(View.INVISIBLE);
                OC_A.setVisibility(View.INVISIBLE);
                OFD_A.setVisibility(View.INVISIBLE);
                OS_A.setVisibility(View.INVISIBLE);

                // ticks
                OR_T_IV.setImageResource(R.drawable.nottick_48);
                OC_T_IV.setVisibility(View.INVISIBLE);
                OFD_T_IV.setVisibility(View.INVISIBLE);
                OS_T_IV.setVisibility(View.INVISIBLE);
//                OC_T_IV.setImageResource(R.drawable.grey_tick);
//                OFD_T_IV.setImageResource(R.drawable.grey_tick);
//                OS_T_IV.setImageResource(R.drawable.grey_tick);

                // line views
//                v1.setBackgroundColor(getResources().getColor(R.color.md_blue_grey_500));
//                v2.setBackgroundColor(getResources().getColor(R.color.md_blue_grey_500));
//                v3.setBackgroundColor(getResources().getColor(R.color.md_blue_grey_500));
                v1.setVisibility(View.INVISIBLE);
                v2.setVisibility(View.INVISIBLE);
                v3.setVisibility(View.INVISIBLE);

                // other text header
                OR_H_TV.setAlpha(1f);
                OC_H_TV.setAlpha(0.3f);
                OFD_H_TV.setAlpha(0.3f);
                OS_H_TV.setAlpha(0.3f);

                //other header
                OR_D_TV.setVisibility(View.VISIBLE);
                OC_D_TV.setVisibility(View.INVISIBLE);
                OFD_D_TV.setVisibility(View.INVISIBLE);
                OS_D_TV.setVisibility(View.INVISIBLE);
                //OR_D_TV.setText(OR_D_TV.getText()+placedOrderModel.getOrder_date_time().split("-")[0]);

                // Icons
                OR_I.setAlpha(1f);
                OC_I.setAlpha(0.3f);
                OFD_I.setAlpha(0.3f);
                OS_I.setAlpha(0.3f);
                //action
                text_action1.setVisibility(View.GONE);
                img_action1.setVisibility(View.GONE);
                text_actionmualai.setVisibility(View.VISIBLE);
                img_actionmualai.setVisibility(View.VISIBLE);
                break;

            case "1":
                // title
                current_status_header.setText("ORDER RECEIVED");
                // anims
                OR_A.setVisibility(View.VISIBLE);
                OC_A.setVisibility(View.INVISIBLE);
                OFD_A.setVisibility(View.INVISIBLE);
                OS_A.setVisibility(View.INVISIBLE);

                // ticks
                OR_T_IV.setImageResource(R.drawable.green_tick);
                OC_T_IV.setImageResource(R.drawable.grey_tick);
                OFD_T_IV.setImageResource(R.drawable.grey_tick);
                OS_T_IV.setImageResource(R.drawable.grey_tick);

                // line views
                v1.setBackgroundColor(getResources().getColor(R.color.md_blue_grey_500));
                v2.setBackgroundColor(getResources().getColor(R.color.md_blue_grey_500));
                v3.setBackgroundColor(getResources().getColor(R.color.md_blue_grey_500));

                // other text header
                OR_H_TV.setAlpha(1f);
                OC_H_TV.setAlpha(0.3f);
                OFD_H_TV.setAlpha(0.3f);
                OS_H_TV.setAlpha(0.3f);

                //other header
                OR_D_TV.setVisibility(View.VISIBLE);
                OC_D_TV.setVisibility(View.INVISIBLE);
                OFD_D_TV.setVisibility(View.INVISIBLE);
                OS_D_TV.setVisibility(View.INVISIBLE);
                //OR_D_TV.setText(OR_D_TV.getText()+placedOrderModel.getOrder_date_time().split("-")[0]);

                // Icons
                OR_I.setAlpha(1f);
                OC_I.setAlpha(0.3f);
                OFD_I.setAlpha(0.3f);
                OS_I.setAlpha(0.3f);
                break;


            case "2":
                // title
                current_status_header.setText("ORDER CONFIRMED");

                // anims
                OR_A.setVisibility(View.INVISIBLE);
                OC_A.setVisibility(View.VISIBLE);
                OFD_A.setVisibility(View.INVISIBLE);
                OS_A.setVisibility(View.INVISIBLE);

                // ticks
                OR_T_IV.setImageResource(R.drawable.green_tick);
                OC_T_IV.setImageResource(R.drawable.green_tick);
                OFD_T_IV.setImageResource(R.drawable.grey_tick);
                OS_T_IV.setImageResource(R.drawable.grey_tick);

                // line views
                v1.setBackgroundColor(getResources().getColor(R.color.md_green_500));
                v2.setBackgroundColor(getResources().getColor(R.color.md_blue_grey_500));
                v3.setBackgroundColor(getResources().getColor(R.color.md_blue_grey_500));

                // other text header
                OR_H_TV.setAlpha(1f);
                OC_H_TV.setAlpha(1f);
                OFD_H_TV.setAlpha(0.3f);
                OS_H_TV.setAlpha(0.3f);

                //details
                OR_D_TV.setVisibility(View.VISIBLE);
                OC_D_TV.setVisibility(View.VISIBLE);
                OFD_D_TV.setVisibility(View.INVISIBLE);
                OS_D_TV.setVisibility(View.INVISIBLE);
                //OR_D_TV.setText(OR_D_TV.getText()+placedOrderModel.getOrder_date_time().split("-")[0]);

                // Icons
                OR_I.setAlpha(1f);
                OC_I.setAlpha(1f);
                OFD_I.setAlpha(0.3f);
                OS_I.setAlpha(0.3f);

                //action
                text_action1.setVisibility(View.GONE);
                img_action1.setVisibility(View.GONE);



                break;



            case "3":
                // title
                current_status_header.setText("OUT FOR DELIVERY");

                // anims
                OR_A.setVisibility(View.INVISIBLE);
                OC_A.setVisibility(View.INVISIBLE);
                OFD_A.setVisibility(View.VISIBLE);
                OS_A.setVisibility(View.INVISIBLE);

                // ticks
                OR_T_IV.setImageResource(R.drawable.green_tick);
                OC_T_IV.setImageResource(R.drawable.green_tick);
                OFD_T_IV.setImageResource(R.drawable.green_tick);
                OS_T_IV.setImageResource(R.drawable.grey_tick);

                // line views
                v1.setBackgroundColor(getResources().getColor(R.color.md_green_500));
                v2.setBackgroundColor(getResources().getColor(R.color.md_green_500));
                v3.setBackgroundColor(getResources().getColor(R.color.md_blue_grey_500));

                // other text header
                OR_H_TV.setAlpha(1f);
                OC_H_TV.setAlpha(1f);
                OFD_H_TV.setAlpha(1f);
                OS_H_TV.setAlpha(0.3f);

                //details
                OR_D_TV.setVisibility(View.VISIBLE);
                OC_D_TV.setVisibility(View.VISIBLE);
                OFD_D_TV.setVisibility(View.VISIBLE);
                OS_D_TV.setVisibility(View.INVISIBLE);
                //OR_D_TV.setText(OR_D_TV.getText()+placedOrderModel.getOrder_date_time().split("-")[0]);

                // Icons
                OR_I.setAlpha(1f);
                OC_I.setAlpha(1f);
                OFD_I.setAlpha(1f);
                OS_I.setAlpha(0.3f);
                //action
                //action
                text_action1.setVisibility(View.GONE);
                img_action1.setVisibility(View.GONE);

                break;


            case "4":
                // title
                current_status_header.setText("ORDER SHIPPED");

                // anims
                OR_A.setVisibility(View.INVISIBLE);
                OC_A.setVisibility(View.INVISIBLE);
                OFD_A.setVisibility(View.INVISIBLE);
                OS_A.setVisibility(View.VISIBLE);

                // ticks
                OR_T_IV.setImageResource(R.drawable.green_tick);
                OC_T_IV.setImageResource(R.drawable.green_tick);
                OFD_T_IV.setImageResource(R.drawable.green_tick);
                OS_T_IV.setImageResource(R.drawable.green_tick);

                // line views
                v1.setBackgroundColor(getResources().getColor(R.color.md_green_500));
                v2.setBackgroundColor(getResources().getColor(R.color.md_green_500));
                v3.setBackgroundColor(getResources().getColor(R.color.md_green_500));

                // other text header
                OR_H_TV.setAlpha(1f);
                OC_H_TV.setAlpha(1f);
                OFD_H_TV.setAlpha(1f);
                OS_H_TV.setAlpha(1f);

                //details
                OR_D_TV.setVisibility(View.VISIBLE);
                OC_D_TV.setVisibility(View.VISIBLE);
                OFD_D_TV.setVisibility(View.VISIBLE);
                OS_D_TV.setVisibility(View.VISIBLE);
                //OS_D_TV.setText(OS_D_TV.getText()+placedOrderModel.getDelivery_date());
                //OR_D_TV.setText(OR_D_TV.getText()+placedOrderModel.getOrder_date_time().split("-")[0]);

                // Icons
                OR_I.setAlpha(1f);
                OC_I.setAlpha(1f);
                OFD_I.setAlpha(1f);
                OS_I.setAlpha(1f);
                //action
                //action
                text_action1.setVisibility(View.GONE);
                img_action1.setVisibility(View.GONE);
                text_action.setVisibility(View.VISIBLE);
                img_action.setVisibility(View.VISIBLE);

                break;


        }

    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.reOrder) {
            Intent reOrderIntent = new Intent(this, OrderProductActivity.class);
            reOrderIntent.putExtra(PRODUCTID, productId);
            startActivity(reOrderIntent);
        }
    }
}
