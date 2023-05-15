package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.utils.Constant.ORDER;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.adapter.OrderAdapter;
import com.marwaeltayeb.souq.databinding.ActivityOrdersBinding;
import com.marwaeltayeb.souq.model.Order;
import com.marwaeltayeb.souq.model.ProductInOrder;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.viewmodel.OrderViewModel;

import java.io.Serializable;
import java.util.List;

public class OrdersActivity extends AppCompatActivity implements OrderAdapter.OrderAdapterOnClickHandler {

    private ActivityOrdersBinding binding;
    private OrderViewModel orderViewModel;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orders);

        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        setUpRecycleView();

        getOrders();
    }

    private void setUpRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.orderList.setLayoutManager(layoutManager);
        binding.orderList.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.orderList.addItemDecoration(dividerItemDecoration);
    }

    private void getOrders() {
        orderViewModel.getOrders(LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, orderApiResponse -> {
            orderAdapter = new OrderAdapter( orderApiResponse.getOrderList(),this);
            binding.orderList.setAdapter(orderAdapter);
        });
    }

    @Override
    public void onClick(Order order , List<ProductInOrder > productInOrder){
        Intent intent = new Intent(OrdersActivity.this, StatusActivity.class);
        if (productInOrder == null) {
            //Toast.makeText(this, "Nhấn lần nữa", Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("listProductInOrder", (Serializable) productInOrder);
        intent.putExtras(bundle);
        intent.putExtra(ORDER, (order));
        startActivity(intent);
    }
}
