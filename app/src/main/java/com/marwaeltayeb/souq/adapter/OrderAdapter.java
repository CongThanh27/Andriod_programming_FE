package com.marwaeltayeb.souq.adapter;

import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.OrderListItemBinding;
import com.marwaeltayeb.souq.model.Order;
import com.marwaeltayeb.souq.model.ProductInOrder;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.viewmodel.ProductInOrderViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private final List<Order> orderList;
    private List<ProductInOrder> proList;
    private ProductInOrderViewModel productInOrderViewModel;
    private Order currentOrder;
    private double totalPrice = 0.0;

    private final OrderAdapter.OrderAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface OrderAdapterOnClickHandler {
        void onClick(Order order, List<ProductInOrder> proList);
    }

    public OrderAdapter(List<Order> orderList, OrderAdapter.OrderAdapterOnClickHandler clickHandler) {
        this.orderList = orderList;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        OrderListItemBinding orderListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.order_list_item,parent,false);
        return new OrderViewHolder(orderListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        currentOrder = orderList.get(position);
        String ordernumber =  currentOrder.getOrderNumber();
        holder.binding.noOfItems.setText("0 sản phẩm");
        productInOrderViewModel = ViewModelProviders.of((FragmentActivity) holder.itemView.getContext()).get(ProductInOrderViewModel.class);
        productInOrderViewModel.getProductsInOrder(LoginUtils.getInstance(holder.itemView.getContext()).getUserInfo().getId(), ordernumber).observe((FragmentActivity) holder.itemView.getContext(), productInOrderApiResponse -> {
            if (productInOrderApiResponse != null) {
                if(productInOrderApiResponse.getProductListInOrderList().size() > 0){

                    holder.binding.noOfItems.setText(productInOrderApiResponse.getProductListInOrderList().size()+" sản phẩm");
                    //vòng for qua các sản phẩm trong đơn hàng
                    for(int i = 0; i < productInOrderApiResponse.getProductListInOrderList().size(); i++){
                        totalPrice += productInOrderApiResponse.getProductListInOrderList().get(i).getProductPrice()*productInOrderApiResponse.getProductListInOrderList().get(i).getQuantity();
                        String img = "img" + (i+1);
                        ProductInOrder productInOrder = productInOrderApiResponse.getProductListInOrderList().get(i);
                        String imageUrl = LOCALHOST + productInOrder.getImage().replaceAll("\\\\", "/");
                        if(img.equals("img1")){
                            holder.binding.cv1.setVisibility(View.VISIBLE);
                            Glide.with(holder.itemView.getContext())
                                    .load(imageUrl)
                                    .into(holder.binding.img1);
                        }
                        if(img.equals("img2")){
                            holder.binding.cv2.setVisibility(View.VISIBLE);
                            Glide.with(holder.itemView.getContext())
                                    .load(imageUrl)
                                    .into(holder.binding.img2);
                        }
                        if(img.equals("img3")){
                            holder.binding.cv3.setVisibility(View.VISIBLE);
                            Glide.with(holder.itemView.getContext())
                                    .load(imageUrl)
                                    .into(holder.binding.img3);
                        }
                        if(img.equals("img4")){
                            holder.binding.cv4.setVisibility(View.VISIBLE);
                            Glide.with(holder.itemView.getContext())
                                    .load(imageUrl)
                                    .into(holder.binding.img4);
                        }

                    }
                    DecimalFormat formatter = new DecimalFormat("#,###,###");
                    String formattedPrice = formatter.format(totalPrice);
                    holder.binding.productPrice.setText(formattedPrice + " đ ");
                    totalPrice = 0.0;

                }
//
            }
        });

        holder.binding.orderNumber.setText("Mã vận đơn: "+currentOrder.getOrderNumber());
        holder.binding.orderDate.setText("Ngày: "+currentOrder.getOrderDate());
    }

    @Override
    public int getItemCount() {
        if (orderList == null) {
            return 0;
        }
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Create view instances
        private final OrderListItemBinding binding;

        private OrderViewHolder(OrderListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            // Get position of the product
            currentOrder = orderList.get(position);
            //lấy danh sách sản phẩm trong đơn hàng
            String ordernumber = currentOrder.getOrderNumber();
            productInOrderViewModel = ViewModelProviders.of((FragmentActivity) v.getContext()).get(ProductInOrderViewModel.class);
            productInOrderViewModel.getProductsInOrder(LoginUtils.getInstance(v.getContext()).getUserInfo().getId(), ordernumber).observe((FragmentActivity) v.getContext(), productInOrderApiResponse -> {
                if (productInOrderApiResponse != null) {
                    if(productInOrderApiResponse.getProductListInOrderList().size() > 0){
                        proList = productInOrderApiResponse.getProductListInOrderList();
                    }
                }
            });
            // Send product through click
            clickHandler.onClick(currentOrder,proList);
        }
    }
}
