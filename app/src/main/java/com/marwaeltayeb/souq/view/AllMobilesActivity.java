package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCT;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.adapter.ProductAdapter;
import com.marwaeltayeb.souq.databinding.ActivityAllMobilesBinding;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.viewmodel.ProductViewModel;

public class AllMobilesActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterOnClickHandler{

    private ActivityAllMobilesBinding binding;
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_mobiles);
        ActionBar actionBar = getSupportActionBar();
        int userID = LoginUtils.getInstance(this).getUserInfo().getId();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String stringValue = bundle.getString("AllList");
        actionBar.setTitle("All " +stringValue);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        switch (stringValue){
            case "Makeup":
                productViewModel.loadMobiles("Makeup", userID);
            break;
            case "Skincare":
                productViewModel.loadLaptops("Skincare",userID);
                break;
            case "Favorite":
                productViewModel.loadFavorite(userID);
                break;
            case "Sold":
                productViewModel.loadSold(userID);
                break;
            case "Sales":
                productViewModel.loadFlashSale(userID);
                break;
            default:
        }

        setupRecyclerViews();

        getAll(stringValue);
    }
//getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT. Nếu biểu thức này là đúng (hướng màn hình là "portrait"), thì giá trị trả về sẽ là 2, ngược lại nếu biểu thức này là sai (hướng màn hình là "landscape"), thì giá trị trả về sẽ là 4.
    private void setupRecyclerViews() {
        // Mobiles
        binding.allMobilesRecyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 3 : 8));
        binding.allMobilesRecyclerView.setHasFixedSize(false);
        productAdapter = new ProductAdapter(this,this);
    }
    public void getAll(String stringValue) {
        switch (stringValue){
            case "Makeup":
                // Observe the productPagedList from ViewModel
                productViewModel.productPagedList.observe(this, products -> {
                    productAdapter.submitList(products);
                });
                binding.allMobilesRecyclerView.setAdapter(productAdapter);
                break;
            case "Skincare":
                productViewModel.laptopPagedList.observe(this, products -> {
                    productAdapter.submitList(products);
                });
                binding.allMobilesRecyclerView.setAdapter(productAdapter);
                break;
            case "Favorite":
                productViewModel.favoritePagedList.observe(this, products -> {
                    productAdapter.submitList(products);
                });
                binding.allMobilesRecyclerView.setAdapter(productAdapter);
                break;
            case "Sold":
                productViewModel.soldPagedList.observe(this, products -> {
                    productAdapter.submitList(products);
                });
                binding.allMobilesRecyclerView.setAdapter(productAdapter);
                break;
            case "Sales":
                productViewModel.flashSalePagedList.observe(this, products -> {
                    productAdapter.submitList(products);
                });
                binding.allMobilesRecyclerView.setAdapter(productAdapter);
                break;
            default:
        }

    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(AllMobilesActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }
}
