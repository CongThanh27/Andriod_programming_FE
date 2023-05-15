package com.marwaeltayeb.souq.adapter;

import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.model.ProductInOrder;

import java.text.DecimalFormat;
import java.util.List;

public class ProInOrderAdapter extends RecyclerView.Adapter<ProInOrderAdapter.ProInOrderAdapterHolder> {
    private List<ProductInOrder> ordersList;
    private Context mContext;

    public ProInOrderAdapter(List<ProductInOrder> ordersList, Context mContext) {
        this.ordersList = ordersList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ProInOrderAdapter.ProInOrderAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, null);
        return new ProInOrderAdapter.ProInOrderAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProInOrderAdapter.ProInOrderAdapterHolder holder, int position) {
        ProductInOrder productInOrder = ordersList.get(position);
        if (productInOrder == null) {
            return;
        }
        else {
            String imageUrl = LOCALHOST + productInOrder.getImage().replaceAll("\\\\", "/");
            Glide.with(mContext)
                    .load(imageUrl)
                    .into(holder.imgProductImages);
            holder.ProductName.setText(productInOrder.getProductName());
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formattedPrice = formatter.format(productInOrder.getProductPrice());
            holder.ProductPrice.setText(formattedPrice+"đ ");
            holder.ProductQuantity.setText("Qty:"+productInOrder.getQuantity());
            holder.tinhtien.setText(formattedPrice+"đ x"+productInOrder.getQuantity()+ " = "+formatter.format(productInOrder.getQuantity()*productInOrder.getProductPrice())+ "đ ");
        }
    }

    @Override
    public int getItemCount() {
        if (ordersList != null) {
            return ordersList.size();
        }
        return 0;
    }
    class ProInOrderAdapterHolder extends RecyclerView.ViewHolder {
        private ImageView imgProductImages;
        private TextView ProductName,ProductPrice,ProductQuantity, tinhtien;

        public ProInOrderAdapterHolder(@NonNull View itemView) {
            super(itemView);
            imgProductImages = itemView.findViewById(R.id.image_cartlist);
            ProductName = itemView.findViewById(R.id.cart_prtitle);
            ProductPrice = itemView.findViewById(R.id.cart_prprice);
            ProductQuantity = itemView.findViewById(R.id.cart_prcount);
            tinhtien = itemView.findViewById(R.id.total_card_amount);
        }
    }
}
