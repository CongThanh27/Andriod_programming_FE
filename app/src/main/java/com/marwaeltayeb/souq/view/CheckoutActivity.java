package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCTID;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.adapter.DiaChiAdapter;
import com.marwaeltayeb.souq.adapter.OrderAdapter;
import com.marwaeltayeb.souq.adapter.ThongTinDonHangAdapter;

import com.marwaeltayeb.souq.model.DiaChiApiResponse;
import com.marwaeltayeb.souq.model.Diachi;
import com.marwaeltayeb.souq.model.Ordering;
import com.marwaeltayeb.souq.model.ProductApiResponse;
import com.marwaeltayeb.souq.model.ProductInCart;
import com.marwaeltayeb.souq.model.Shipping;
import com.marwaeltayeb.souq.model.User;
import com.marwaeltayeb.souq.net.APIService;
import com.marwaeltayeb.souq.net.RetrofitClient;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.viewmodel.DiaChiViewModel;
import com.marwaeltayeb.souq.viewmodel.OrderingViewModel;
import com.marwaeltayeb.souq.viewmodel.ShippingViewModel;
import com.marwaeltayeb.souq.viewmodel.UpdateInfoUserViewModel;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    private List<ProductInCart> cartList;
    private List<Diachi> diachisList;
    DiaChiViewModel diaChiViewModel;
    RecyclerView recyclerView;
    APIService apiService;
    TextView nameuser, diachi, sdt, tongtien, tamtinh, phivanchuyen, giamgia,txtSeeAllReviews;
    Button tienhanhdathang,btnchilannay, btnthongtindaco, btncapnhatlansaut, btncapnhatthongtin,btncapnhatthongtin2,btncapnhatthongtin3 ;
    private ThongTinDonHangAdapter thongTinDonHangAdapter;
    private ShippingViewModel shippingViewModel;
    private OrderingViewModel orderingViewModel;
    LinearLayout linearLayoutcapnhat, linearLayoutthongtin;
    EditText  edtdiachi, edtsdt;
    public String address , phone, city= "HCM", country = "Việt Nam" , zip= "123";
    RecyclerView recyclerDiaChi;
    DiaChiAdapter diaChiAdapter;
    User user;
    UpdateInfoUserViewModel updateInfoUserViewModel;

    private void  AnhXa(){
    nameuser= findViewById(R.id.txtnameuser);
    diachi = findViewById(R.id.txtđiachi);
    sdt = findViewById(R.id.txtsdt);
    tamtinh = findViewById(R.id.txttamtinh);
    tongtien = findViewById(R.id.txtthanhtien);
    phivanchuyen = findViewById(R.id.txtphivanchuyen);
    giamgia = findViewById(R.id.txtgiamgia);
    tienhanhdathang = findViewById(R.id.button);
    linearLayoutcapnhat = findViewById(R.id.capnhatthongtin);
    linearLayoutthongtin = findViewById(R.id.thongtindaco);
    edtdiachi = findViewById(R.id.editđiachi);
    edtsdt = findViewById(R.id.editsdt);
    btncapnhatthongtin=findViewById(R.id.btnthuchiencapnhat);
    btncapnhatthongtin2=findViewById(R.id.btnthuchiencapnhat2);
    btncapnhatthongtin3=findViewById(R.id.btnthuchiencapnhat3);
    btnchilannay=findViewById(R.id.btnchilannay);
    btnthongtindaco=findViewById(R.id.btnmacdinh);
    btncapnhatlansaut=findViewById(R.id.btnlansau);
    recyclerDiaChi = findViewById(R.id.listOfInfodiachi);
    txtSeeAllReviews = findViewById(R.id.txtSeeAllReviews);
        recyclerView = findViewById(R.id.listOfInfo);
    }

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dathang);
        AnhXa();
//        txtSeeAllReviews.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //recyclerView có độ cao là wrap_content
//            }
//        });

        shippingViewModel = ViewModelProviders.of(this).get(ShippingViewModel.class);
        orderingViewModel = ViewModelProviders.of(this).get(OrderingViewModel.class);
        diaChiViewModel = ViewModelProviders.of(this).get(DiaChiViewModel.class);
        updateInfoUserViewModel = ViewModelProviders.of(this).get(UpdateInfoUserViewModel.class);

        //nhận thông tin từ CartAtivity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cartList = (List<ProductInCart>) bundle.getSerializable("cartList");
        }

        //Thông tin đơn hàng

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        //chỉnh độ rộng phần tử con của recyclerview rộng hết màn hình

        recyclerView.setLayoutManager(layoutManager);
        thongTinDonHangAdapter = new ThongTinDonHangAdapter(cartList, this);
        recyclerView.setAdapter(thongTinDonHangAdapter);

        //thông tin địa chỉ
        getDiaChi();
        user = LoginUtils.getInstance(this).getUserInfo();
        if(user.getAddress().isEmpty() || user.getPhone_number().isEmpty() || user.getAddress() == null || user.getPhone_number() == null|| user.getAddress().equals("") || user.getPhone_number().equals("")){
            linearLayoutcapnhat.setVisibility(View.VISIBLE);
            linearLayoutthongtin.setVisibility(View.GONE);
        }
        // Nếu người dùng đã cập nhật thông tin
        else {
            diachi.setText(user.getAddress());
            sdt.setText(user.getPhone_number());
            address = user.getAddress();
            phone =  user.getPhone_number();
            btnthongtindaco.setVisibility(View.VISIBLE);
        }

        // thông tin người nhận

        nameuser.setText(user.getName());


        //tổng tiền
        //vòng for tính tổng tiền
        int tong = 0;
        for (int i = 0; i < cartList.size(); i++) {
            tong += cartList.get(i).getProductPrice() * cartList.get(i).getCartquantity();
        }
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(tong);
        tamtinh.setText((formattedPrice) + "đ");
        tongtien.setText((formattedPrice) + "đ");
        giamgia.setText("-0đ");


        //+ 2:huy, 3:chọn, 1 cap nhat
        btncapnhatthongtin.setOnClickListener(v -> {
            linearLayoutcapnhat.setVisibility(View.VISIBLE);
            linearLayoutthongtin.setVisibility(View.GONE);
            recyclerDiaChi.setVisibility(View.GONE);
            btncapnhatthongtin2.setVisibility(View.VISIBLE);
            btncapnhatthongtin3.setVisibility(View.GONE);
            btncapnhatthongtin.setVisibility(View.GONE);
        });
        btncapnhatthongtin2.setOnClickListener(v -> {
            linearLayoutcapnhat.setVisibility(View.GONE);
            linearLayoutthongtin.setVisibility(View.VISIBLE);
            recyclerDiaChi.setVisibility(View.GONE);
            btncapnhatthongtin2.setVisibility(View.GONE);
            btncapnhatthongtin3.setVisibility(View.VISIBLE);
            btncapnhatthongtin.setVisibility(View.GONE);
        });
        btncapnhatthongtin3.setOnClickListener(v -> {
            linearLayoutcapnhat.setVisibility(View.GONE);
            linearLayoutthongtin.setVisibility(View.GONE);
            recyclerDiaChi.setVisibility(View.VISIBLE);
            btncapnhatthongtin.setVisibility(View.VISIBLE);
            btncapnhatthongtin2.setVisibility(View.GONE);
            btncapnhatthongtin3.setVisibility(View.GONE);
        });

        TienHanhDatHang();

    }
    private void getDiaChi() {
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false);
        // chỉnh độ rộng layoutManager bằng cha
        recyclerDiaChi.setHasFixedSize(true);
        //chỉnh độ rộng phần tử con của recyclerview rộng hết màn hình

        recyclerDiaChi.setLayoutManager(layoutManager1);

        diaChiViewModel.getDiaChis(LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, DiaChiApiResponse -> {
            diachisList = DiaChiApiResponse.getDiaChiInfo();
            diaChiAdapter = new DiaChiAdapter(diachisList,this,diachi,sdt,linearLayoutcapnhat,linearLayoutthongtin,recyclerDiaChi,btncapnhatthongtin2,btncapnhatthongtin3,btncapnhatthongtin);
            recyclerDiaChi.setAdapter(diaChiAdapter);
        });
    }
    private void TienHanhDatHang() {
        user = LoginUtils.getInstance(this).getUserInfo();
        btnchilannay.setOnClickListener(v -> {
            address = edtdiachi.getText().toString();
            phone = edtsdt.getText().toString();
            if (address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
            else {
                diachi.setText(address);
                sdt.setText(phone);
                linearLayoutcapnhat.setVisibility(View.GONE);
                linearLayoutthongtin.setVisibility(View.VISIBLE);
            }
        });
        btnthongtindaco.setOnClickListener(v -> {
            //Toast.makeText(this, user.getPhone_number(), Toast.LENGTH_SHORT).show();
            edtdiachi.setText(user.getAddress());
            edtsdt.setText(user.getPhone_number());

        });
        btncapnhatlansaut.setOnClickListener(v -> {
            address = edtdiachi.getText().toString();
            phone = edtsdt.getText().toString();
            if (address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
            else {
                diachi.setText(address);
                sdt.setText(phone);
                linearLayoutcapnhat.setVisibility(View.GONE);
                linearLayoutthongtin.setVisibility(View.VISIBLE);
                updateInfoUserViewModel.UpdateInfoUsers(user.getId(),phone,address).observe(this, responseBody -> {
                    try {
                        Toast.makeText(this, responseBody.string(), Toast.LENGTH_SHORT).show();
                        //finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            }
        });

        //Cập nhật thông tin người dùng
        List<ProductInCart> cartList = this.cartList;
        user = LoginUtils.getInstance(this).getUserInfo();
        int userId = user.getId();
        address = diachi.getText().toString();
        phone = sdt.getText().toString();
        tienhanhdathang.setOnClickListener(v -> {
            address = diachi.getText().toString();
            phone = sdt.getText().toString();
            //Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
        if(  address.isEmpty() || phone.isEmpty()|| address.equals("") || phone.equals("")|| address==null || phone==null){
            Toast.makeText(this, "Vui lòng cập nhật thông tin người nhận", Toast.LENGTH_SHORT).show();
        }

        else {
            //vòng for qua từng sản phẩm trong giỏ hàng
            //random từ 100000 đến 999999
            int random = (int) (Math.random() * (999999 - 100000)) + 100000;
            for (int i = 0; i < cartList.size(); i++) {
                int productID = cartList.get(i).getProductId();
                int quantity = cartList.get(i).getCartquantity();
                Shipping shipping = new Shipping(address, city, country, zip, phone,userId, productID);
                shippingViewModel.addShippingAddress(shipping).observe(this, responseBody -> {
                    try {
                        Toast.makeText(this, responseBody.string()+"", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    orderProduct(productID,quantity,random);
                });
            }
        }
        });
    }
    private void orderProduct(int productID,int quantity, int random) {
        User user = LoginUtils.getInstance(this).getUserInfo();
        String nameOnCard = user.getName();
        String cardNumber = user.getPhone_number();
        String year = "2023";
        String month = "12";
        String fullDate = year + "-" + month + "-00";
        int maDonHang = random;
        //Toast.makeText(this, diachi.getText().toString(), Toast.LENGTH_SHORT).show();
        Ordering ordering = new Ordering(nameOnCard,cardNumber,fullDate,user.getId(),productID,quantity,random,diachi.getText().toString(),sdt.getText().toString());
        orderingViewModel.orderProduct(ordering).observe(this, responseBody -> {
            try {
                Toast.makeText(this, responseBody.string() + "", Toast.LENGTH_SHORT).show();
                finish();
                Intent homeIntent = new Intent(this, OrderPlacedActivity.class);
                homeIntent.putExtra("maDonHang111", maDonHang);
                startActivity(homeIntent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
