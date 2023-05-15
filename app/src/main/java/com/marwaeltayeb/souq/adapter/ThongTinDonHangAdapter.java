package com.marwaeltayeb.souq.adapter;

import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.souq.R;

import com.marwaeltayeb.souq.model.ProductInCart;

import java.text.DecimalFormat;
import java.util.List;

public class ThongTinDonHangAdapter extends RecyclerView.Adapter<ThongTinDonHangAdapter.ThongTinDonHangAdapterHolder>{
    private List<ProductInCart> cartListList;
    private Context mContext;
    public ThongTinDonHangAdapter(List<ProductInCart> cartListList,Context mContext) {
        this.cartListList = cartListList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public ThongTinDonHangAdapter.ThongTinDonHangAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_thongtindonhang, null);
        return new ThongTinDonHangAdapter.ThongTinDonHangAdapterHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ThongTinDonHangAdapter.ThongTinDonHangAdapterHolder holder, int position) {
        ProductInCart productInCart = cartListList.get(position);
        if (productInCart == null) {
            return;
        }
        else {
            String imageUrl = LOCALHOST + productInCart.getProductImage().replaceAll("\\\\", "/");
            Glide.with(mContext)
                    .load(imageUrl)
                    .into(holder.imgProductImages);
            holder.ProductName.setText(productInCart.getProductName());
            //biến dổi productInCart.getProductPrice() thành string
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formattedPrice = formatter.format(productInCart.getProductPrice());
            holder.ProductPrice.setText(formattedPrice+"đ ");
            holder.ProductQuantity.setText("x"+productInCart.getCartquantity());
        }
    }

    @Override
    public int getItemCount() {

        if (cartListList != null) {
            return cartListList.size();
        }
        return 0;
    }
    class ThongTinDonHangAdapterHolder extends RecyclerView.ViewHolder {
        private ImageView imgProductImages;
        private TextView ProductName,ProductPrice,ProductQuantity;

        public ThongTinDonHangAdapterHolder(@NonNull View itemView) {
            super(itemView);
            imgProductImages = itemView.findViewById(R.id.imgProductImage);
            ProductName = itemView.findViewById(R.id.txtProductName);
            ProductPrice = itemView.findViewById(R.id.txtProductPrice);
            ProductQuantity = itemView.findViewById(R.id.txtProductQuantity);
        }
    }
}

