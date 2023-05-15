package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCT;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCTID;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCT_ID;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.adapter.ReviewAdapter;
import com.marwaeltayeb.souq.databinding.ActivityDetailsBinding;
import com.marwaeltayeb.souq.model.Cart;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.model.Review;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.utils.RequestCallback;
import com.marwaeltayeb.souq.viewmodel.ReviewViewModel;
import com.marwaeltayeb.souq.viewmodel.ToCartViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DetailsActivity";

    private ActivityDetailsBinding binding;
    private ReviewViewModel reviewViewModel;
    private ToCartViewModel toCartViewModel;
    private ReviewAdapter reviewAdapter;
    private List<Review> reviewList;
    private Product product;
    EditText quantityEditText;


    int quantityInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        toCartViewModel = ViewModelProviders.of(this).get(ToCartViewModel.class);
        binding.txtSeeAllReviews.setOnClickListener(this);
        binding.writeReview.setOnClickListener(this);
        binding.addToCart.setOnClickListener(this);
        binding.buy.setOnClickListener(this);
        quantityEditText = findViewById(R.id.quantityProductPage);
        quantityEditText.setText(String.valueOf(1));
        binding.decrementQuantity.setOnClickListener(this);
        binding.incrementQuantity.setOnClickListener(this);
        binding.button2.setOnClickListener(this);
        binding.button11.setOnClickListener(this);
        getProductDetails();

        setUpRecycleView();

        getReviewsOfProduct();
    }

    private void setUpRecycleView() {
        binding.listOfReviews.setHasFixedSize(true);
        binding.listOfReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.listOfReviews.setItemAnimator(null);
    }

    private void getProductDetails() {
        // Receive the product object
        product = getIntent().getParcelableExtra(PRODUCT);
        Log.d(TAG,"isFavourite " + product.isFavourite() + " isInCart " + product.isInCart());
        // Set Custom ActionBar Layout
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.action_bar_title_layout);
        ((TextView) findViewById(R.id.action_bar_title)).setText(product.getProductName());
        binding.nameOfProduct.setText(product.getProductName());
        //định dạng giá
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(product.getProductPrice());
        String formattedPriceOld = formatter.format(product.getPriceold());
        binding.priceOfProduct.setText(formattedPrice+"đ");
        SpannableString noidungspanned = new SpannableString(formattedPriceOld+"đ");
        noidungspanned.setSpan(new StrikethroughSpan(), 0,(formattedPriceOld+"đ").length() , 0);
        binding.priceOldOfProduct.setText(noidungspanned);
        //giảm giá
        int roundedNumber = (int) Math.round((100-((product.getProductPrice()*100)/product.getPriceold())));
        binding.textView5.setText("-" + roundedNumber+"%");

        binding.productdesc.setText(product.getDescribe());
        //thông số
        binding.thuonghieu.setText(product.getTrademark());
        binding.textView14.setText(product.getOrigin());
        binding.textView16.setText(product.getOrigin());
        binding.textView18.setText(product.getSkinproblems());
        binding.textView20.setText(product.getSex());
        binding.textView22.setText(product.getSkinproblems());
        String imageUrl = LOCALHOST + product.getProductImage().replaceAll("\\\\", "/");
        Glide.with(this)
                .load(imageUrl)
                .into(binding.imageOfProduct);
    }

    private void getReviewsOfProduct() {
        reviewViewModel.getReviews(product.getProductId()).observe(this, reviewApiResponse -> {
            if (reviewApiResponse != null) {
                reviewList = reviewApiResponse.getReviewList();
                binding.danhgia.setText(reviewList.size()==0?"Chưa có đánh giá":reviewList.size()+"đánh giá");
                double totalRating = 0.0;
                for (Review review : reviewList) {
                    totalRating += review.getReviewRate();
                }
                double averageRating = totalRating / reviewList.size();
                binding.sumrate.setText(reviewList.size()==0?"0.0":String.valueOf(averageRating));
                reviewAdapter = new ReviewAdapter(reviewList);
                binding.listOfReviews.setAdapter(reviewAdapter);
            }
            if(reviewList.size() == 0){
                binding.listOfReviews.setVisibility(View.GONE);
                binding.txtFirst.setVisibility(View.VISIBLE);
            }else {
                binding.listOfReviews.setVisibility(View.VISIBLE);
                binding.txtFirst.setVisibility(View.GONE);
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtSeeAllReviews) {
            Intent allReviewIntent = new Intent(DetailsActivity.this, AllReviewsActivity.class);
            allReviewIntent.putExtra(PRODUCT_ID,product.getProductId());
            startActivity(allReviewIntent);
        }
        else if (view.getId() == R.id.decrementQuantity) {
            String quantity = quantityEditText.getText().toString();
            //chuyển sang int
            quantityInt = Integer.parseInt(quantity);
            if (quantityInt > 1) {
                quantityInt--;
                quantityEditText.setText(String.valueOf(quantityInt));
            }
        } else if (view.getId() == R.id.incrementQuantity) {
            String quantity = quantityEditText.getText().toString();
            //chuyển sang int
            quantityInt = Integer.parseInt(quantity);
            quantityInt++;
            quantityEditText.setText(String.valueOf(quantityInt));
        }else if (view.getId() == R.id.writeReview) {
            Intent allReviewIntent = new Intent(DetailsActivity.this, WriteReviewActivity.class);
            allReviewIntent.putExtra(PRODUCT_ID,product.getProductId());
            startActivity(allReviewIntent);
        }else if(view.getId() == R.id.addToCart){
            String quantity = quantityEditText.getText().toString();
            //chuyển sang int
            quantityInt = Integer.parseInt(quantity);
            insertToCart(() -> product.setIsInCart(true));
            Intent cartIntent = new Intent(DetailsActivity.this, CartActivity.class);
            startActivity(cartIntent);
        }else if(view.getId() == R.id.buy){
            Intent shippingIntent = new Intent(DetailsActivity.this, ShippingAddressActivity.class);
            shippingIntent.putExtra(PRODUCTID, product.getProductId());
            startActivity(shippingIntent);
        }
    else if(view.getId() == R.id.button2){
        Intent shippingIntent = new Intent(DetailsActivity.this, ThanhPhanActivity.class);
        startActivity(shippingIntent);
    }
    else if(view.getId() == R.id.button11) {
            Intent shippingIntent = new Intent(DetailsActivity.this, HuongDanSuDungActivity.class);
            startActivity(shippingIntent);

        }


}
    private void insertToCart(RequestCallback callback) {
        Cart cart = new Cart(LoginUtils.getInstance(this).getUserInfo().getId(), product.getProductId(),quantityInt);
        toCartViewModel.addToCart(cart, callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getReviewsOfProduct();
    }
}
