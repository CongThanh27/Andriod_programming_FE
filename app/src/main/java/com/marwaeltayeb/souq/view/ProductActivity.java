package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.souq.utils.Constant.CAMERA_PERMISSION_CODE;
import static com.marwaeltayeb.souq.utils.Constant.CATEGORY;
import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCT;
import static com.marwaeltayeb.souq.utils.Constant.READ_EXTERNAL_STORAGE_CODE;
import static com.marwaeltayeb.souq.utils.ImageUtils.getImageUri;
import static com.marwaeltayeb.souq.utils.ImageUtils.getRealPathFromURI;
import static com.marwaeltayeb.souq.utils.InternetUtils.isNetworkConnected;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.adapter.BrandAdapter;
import com.marwaeltayeb.souq.adapter.ProductAdapter;
import com.marwaeltayeb.souq.databinding.ActivityProductBinding;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.receiver.NetworkChangeReceiver;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.utils.FlagsManager;
import com.marwaeltayeb.souq.utils.OnNetworkListener;
import com.marwaeltayeb.souq.utils.Slide;
import com.marwaeltayeb.souq.viewmodel.HistoryViewModel;
import com.marwaeltayeb.souq.viewmodel.ProductViewModel;
import com.marwaeltayeb.souq.viewmodel.UploadPhotoViewModel;
import com.marwaeltayeb.souq.viewmodel.UserImageViewModel;

import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener, OnNetworkListener, ProductAdapter.ProductAdapterOnClickHandler,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ProductActivity";
    private ActivityProductBinding binding;

    private ProductAdapter mobileAdapter;
    private ProductAdapter laptopAdapter;
    private ProductAdapter historyAdapter;
    private ProductAdapter flashSaleAdapter;
    private ProductAdapter favoriteAdapter;

    private ProductAdapter soldAdapter;
    private ProductViewModel productViewModel;
    private HistoryViewModel historyViewModel;
    private UploadPhotoViewModel uploadPhotoViewModel;
    private UserImageViewModel userImageViewModel;
    private TextView txtGio, txtPhut, txtGiay;

    private Snackbar snack;

    private CircleImageView circleImageView;

    private NetworkChangeReceiver mNetworkReceiver;
    Bundle bundle;
    private static final String[] mImageUrls = {
            "https://bienquangcaohn.net/wp-content/uploads/2019/06/logo-thuong-hieu-my-pham-2-1.jpg",
            "https://bienquangcaohn.net/wp-content/uploads/2019/06/logo-thuong-hieu-my-pham-1.jpg",
            "https://bienquangcaohn.net/wp-content/uploads/2019/06/logo-thuong-hieu-my-pham-2.jpg",
            "https://bienquangcaohn.net/wp-content/uploads/2019/06/logo-thuong-hieu-my-pham-5.jpg",
            "https://bienquangcaohn.net/wp-content/uploads/2019/06/thiet-ke-logo-thuong-hieu-formylook.jpg",
            "https://lambanner.com/wp-content/uploads/2022/11/MNT-DESIGN-LOGO-MY-PHAM-24.jpg",
            "https://lambanner.com/wp-content/uploads/2022/11/MNT-DESIGN-LOGO-MY-PHAM-25.jpg",
            "https://lambanner.com/wp-content/uploads/2022/11/MNT-DESIGN-LOGO-MY-PHAM-27.jpg",
            "https://lambanner.com/wp-content/uploads/2022/11/MNT-DESIGN-LOGO-MY-PHAM-17.jpg",
            "https://lambanner.com/wp-content/uploads/2022/11/MNT-DESIGN-LOGO-MY-PHAM-18.jpg",
            "https://lambanner.com/wp-content/uploads/2022/11/MNT-DESIGN-LOGO-MY-PHAM-20.jpg",
            "https://lambanner.com/wp-content/uploads/2022/11/MNT-DESIGN-LOGO-MY-PHAM-22.jpg",
            "https://lambanner.com/wp-content/uploads/2022/11/MNT-DESIGN-LOGO-MY-PHAM-26.jpg",
            "https://lambanner.com/wp-content/uploads/2022/11/MNT-DESIGN-LOGO-MY-PHAM-21.jpg",
            "https://ttagencyads.com/wp-content/uploads/2021/05/10-logo-thuong-hieu-my-pham-noi-tieng-1.jpg"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);

        int userID = LoginUtils.getInstance(this).getUserInfo().getId();

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.loadFlashSale(userID);
        productViewModel.loadMobiles("Makeup", userID);
        productViewModel.loadLaptops("Skincare",userID);
        productViewModel.loadFavorite(userID);
        productViewModel.loadSold(userID);



        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        historyViewModel.loadHistory(userID);
        uploadPhotoViewModel = ViewModelProviders.of(this).get(UploadPhotoViewModel.class);
        userImageViewModel = ViewModelProviders.of(this).get(UserImageViewModel.class);

        snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);

        binding.included.content.txtSeeAllMobiles.setOnClickListener(this);
        binding.included.content.txtSeeAllLaptops.setOnClickListener(this);
        binding.included.content.textSeeAllSales.setOnClickListener(this);
        binding.included.content.textSeeAllFavorite.setOnClickListener(this);
        binding.included.content.textSeeAllSold.setOnClickListener(this);
        binding.included.content.imageView111.setOnClickListener(this);
        binding.included.content.imageView6.setOnClickListener(this);
        binding.included.content.imageView121.setOnClickListener(this);
        binding.included.content.imageView7.setOnClickListener(this);
        binding.included.content.txtCash.setOnClickListener(this);
        binding.included.content.txtReturn.setOnClickListener(this);
        binding.included.txtSearch.setOnClickListener(this);
        binding.included.content.imageView101.setOnClickListener(this);
        binding.included.content.imageView8.setOnClickListener(this);
        binding.included.content.imageView9.setOnClickListener(this);
        setUpViews();

        getSales();
        getMobiles();
        getLaptops();
        getHistory();
        getFavorite();
        getSold();
        getUserImage();

        flipImages(Slide.getSlides());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new BrandAdapter(mImageUrls));

        mNetworkReceiver = new NetworkChangeReceiver();
        mNetworkReceiver.setOnNetworkListener(this);
    }

    private void setUpViews() {
        Toolbar toolbar = binding.included.toolbar;
        setSupportActionBar(toolbar);

        DrawerLayout drawer = binding.drawerLayout;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        View headerContainer = binding.navView.getHeaderView(0);
        circleImageView = headerContainer.findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(this);
        TextView userName = headerContainer.findViewById(R.id.nameOfUser);
        userName.setText(LoginUtils.getInstance(this).getUserInfo().getName());
        TextView userEmail = headerContainer.findViewById(R.id.emailOfUser);
        userEmail.setText(LoginUtils.getInstance(this).getUserInfo().getEmail());

        binding.included.content.listOfMobiles.setHasFixedSize(true);
        binding.included.content.listOfMobiles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.listOfMobiles.setItemAnimator(null);

        binding.included.content.listOfLaptops.setHasFixedSize(true);
        binding.included.content.listOfLaptops.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.listOfLaptops.setItemAnimator(null);

        binding.included.content.historyList.setHasFixedSize(true);
        binding.included.content.historyList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.historyList.setItemAnimator(null);

        binding.included.content.listOfSale.setHasFixedSize(true);
        binding.included.content.listOfSale.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.listOfSale.setItemAnimator(null);

        binding.included.content.listOfFavorite.setHasFixedSize(true);
        binding.included.content.listOfFavorite.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.listOfFavorite.setItemAnimator(null);

        binding.included.content.listOfSold.setHasFixedSize(true);
        binding.included.content.listOfSold.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.listOfSold.setItemAnimator(null);

        mobileAdapter = new ProductAdapter(this, this);
        flashSaleAdapter = new ProductAdapter(this, this);
        laptopAdapter = new ProductAdapter(this, this);
        historyAdapter = new ProductAdapter(this, this);
        favoriteAdapter = new ProductAdapter(this, this);
        soldAdapter = new ProductAdapter(this, this);

        if (FlagsManager.getInstance().isHistoryDeleted()) {
            binding.included.content.textViewHistory.setVisibility(View.GONE);
        }
    }

    private void getMobiles() {
        if (isNetworkConnected(this)) {
            productViewModel.productPagedList.observe(this, products -> mobileAdapter.submitList(products));
            //binding.included.content.listOfSale.setAdapter(flashSaleAdapter);
            binding.included.content.listOfMobiles.setAdapter(mobileAdapter);

        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }
    private void getSold() {
        if (isNetworkConnected(this)) {
            productViewModel.soldPagedList.observe(this, products -> soldAdapter.submitList(products));
            binding.included.content.listOfSold.setAdapter(soldAdapter);

        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }
    private void getFavorite() {
        if (isNetworkConnected(this)) {
            productViewModel.favoritePagedList.observe(this, products -> favoriteAdapter.submitList(products));
            binding.included.content.listOfFavorite.setAdapter(favoriteAdapter);

        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }
    private void getSales() {
        //Toast.makeText(this, "getSales",Toast.LENGTH_SHORT).show();
        if (isNetworkConnected(this)) {
            productViewModel.flashSalePagedList.observe(this, products -> flashSaleAdapter.submitList(products));
            binding.included.content.listOfSale.setAdapter(flashSaleAdapter);
        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }

    private void getLaptops() {
        if (isNetworkConnected(this)) {
            productViewModel.laptopPagedList.observe(this, products -> laptopAdapter.submitList(products));

            binding.included.content.listOfLaptops.setAdapter(laptopAdapter);
        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }

    private void getHistory() {
        if (isNetworkConnected(this)) {
            historyViewModel.historyPagedList.observe(this, products -> {
                binding.included.content.historyList.setAdapter(historyAdapter);
                historyAdapter.submitList(products);
                historyAdapter.notifyDataSetChanged();
                
                products.addWeakCallback(null, productCallback);
            });
        } else {
            showOrHideViews(View.INVISIBLE);
            binding.included.content.textViewHistory.setVisibility(View.GONE);
            showSnackBar();
        }

    }

    private void flipImages(List<Integer> images) {
        for (int image : images) {
            ImageView imageView = new ImageView(this);

            Glide.with(this)
                    .load(image)
                    .transform(new RoundedCorners(30))
                    .into(imageView);

            binding.included.content.imageSlider.addView(imageView);
        }


        Glide.with(this)
                .load(R.drawable.s8)
                .transform(new RoundedCorners(20))
                .into(binding.included.content.image1);

        binding.included.content.imageSlider.setFlipInterval(2000);
        binding.included.content.imageSlider.setAutoStart(true);

        // Set the animation for the view that enters the screen
        binding.included.content.imageSlider.setInAnimation(this, R.anim.slide_in_right);
        // Set the animation for the view leaving the screen
        binding.included.content.imageSlider.setOutAnimation(this, R.anim.slide_out_left);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSeeAllMobiles:
            case R.id.imageView111:
                Intent mobileIntent = new Intent(this, AllMobilesActivity.class);
                bundle = new Bundle();
                bundle.putString("AllList", "Makeup");
                mobileIntent.putExtras(bundle);
                startActivity(mobileIntent);
                break;
            case R.id.txtSeeAllLaptops:
            case R.id.imageView121:
                Intent laptopIntent = new Intent(this, AllMobilesActivity.class);
                bundle = new Bundle();
                bundle.putString("AllList", "Skincare");
                laptopIntent.putExtras(bundle);
                startActivity(laptopIntent);
                break;
            case R.id.textSeeAllSales:
            case R.id.imageView6:
                Intent salesIntent = new Intent(this, AllMobilesActivity.class);
                bundle = new Bundle();
                bundle.putString("AllList", "Sales");
                salesIntent.putExtras(bundle);
                startActivity(salesIntent);
                break;
            case R.id.textSeeAllFavorite:
                Intent FavoriteIntent = new Intent(this, AllMobilesActivity.class);
                bundle = new Bundle();
                bundle.putString("AllList", "Favorite");
                FavoriteIntent.putExtras(bundle);
                startActivity(FavoriteIntent);
                break;
            case R.id.textSeeAllSold:
            case R.id.imageView7:
                Intent SoldIntent = new Intent(this, AllMobilesActivity.class);
                bundle = new Bundle();
                bundle.putString("AllList", "Sold");
                SoldIntent.putExtras(bundle);
                startActivity(SoldIntent);
                break;
            case R.id.profile_image:
                showCustomAlertDialog();
                break;
            case R.id.txtCash:
                showNormalAlertDialog(getString(R.string.cash));
                break;
            case R.id.txtReturn:
                showNormalAlertDialog(getString(R.string.returnProduct));
                break;
            case R.id.txtSearch:
                Intent searchIntent = new Intent(ProductActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.imageView101:
                Intent newIntent = new Intent(this, DealsActivity.class);
                bundle = new Bundle();
                bundle.putString("New", "New");
                newIntent.putExtras(bundle);
                startActivity(newIntent);
                break;
            case R.id.imageView8:
                Intent wishListIntent = new Intent(this, WishListActivity.class);
                startActivity(wishListIntent);
                break;
            case R.id.imageView9:
                Intent intent = new Intent(this, OrdersActivity.class);
                startActivity(intent);
                break;
            default: // Should not get here
        }
    }

    private void showNormalAlertDialog(String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null).show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.darkGreen));
    }

    private void showCustomAlertDialog() {
        final Dialog dialog = new Dialog(ProductActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_image_dialog);

        Button takePicture = dialog.findViewById(R.id.takePicture);
        Button useGallery = dialog.findViewById(R.id.useGallery);

        takePicture.setEnabled(true);
        useGallery.setEnabled(true);

        takePicture.setOnClickListener(v -> {
            launchCamera();
            dialog.cancel();
        });

        useGallery.setOnClickListener(v -> {
            getImageFromGallery();
            dialog.cancel();
        });

        dialog.show();
    }

    private void getImageFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_CODE);
            } else {
                try {
                    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    getIntent.setType("image/*");

                    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
                    getImageFromGallery.launch(chooserIntent);
                } catch (Exception exp) {
                    Log.i("Error", exp.toString());
                }
            }
        }
    }

    private void launchCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getImageFromCamera.launch(cameraIntent);
            }
        }
    }

    ActivityResultLauncher<Intent> getImageFromGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri imageUri = data.getData();
                    circleImageView.setImageURI(imageUri);

                    String filePath = getRealPathFromURI(this, imageUri);
                    Log.d(TAG, "getImageFromGallery: " + filePath);

                    uploadPhoto(filePath);
                }
            });

    ActivityResultLauncher<Intent> getImageFromCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    circleImageView.setImageBitmap(photo);

                    Uri imageUri = getImageUri(this, photo);
                    String filePath = getRealPathFromURI(this, imageUri);
                    Log.d(TAG, "getImageFromCamera: " + filePath);

                    uploadPhoto(filePath);
                }
            });


    private void uploadPhoto(String pathname) {
        uploadPhotoViewModel.uploadPhoto(pathname).observe(this, responseBody -> {
            Log.d(TAG, "Image Uploaded");
            getUserImage();
        });
    }

    private void getUserImage() {
        userImageViewModel.getUserImage(LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, response -> {
            Log.d(TAG,  "getUserImage");

            if (response != null) {
                String imageUrl = LOCALHOST + response.getImagePath().replaceAll("\\\\", "/");

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.profile_picture)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH)
                        .dontAnimate()
                        .dontTransform();

                Glide.with(getApplicationContext())
                        .load(imageUrl)
                        .apply(options)
                        .into(circleImageView);
            }
        });
    }

    public void showSnackBar() {
        snack.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        snack.show();
    }

    public void hideSnackBar() {
        snack.dismiss();
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerNetworkBroadcastForNougat();
        txtGio = (TextView) findViewById(R.id.txtGio);
        txtPhut = (TextView) findViewById(R.id.txtPhut);
        txtGiay = (TextView) findViewById(R.id.txtGiay);

        long now = System.currentTimeMillis();
        long midnight = getMidnight(now);
        CountDownTimer timer = new CountDownTimer(midnight - now, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int hours = (int) (millisUntilFinished / (60 * 60 * 1000));
                int minutes = (int) ((millisUntilFinished / (60 * 1000)) % 60);
                int seconds = (int) ((millisUntilFinished / 1000) % 60);
                String timeGio = String.format("%02d", hours);
                String timePhut = String.format("%02d", minutes);
                String timeGiay = String.format("%02d", seconds);

                txtGio.setText(timeGio);
                txtPhut.setText(timePhut);
                txtGiay.setText(timeGiay);
            }

            @Override
            public void onFinish() {
                txtGio.setText("00");
                txtPhut.setText("00");
                txtGiay.setText("00");
            }
        };
        timer.start();
    }

    private long getMidnight(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mNetworkReceiver);
    }

    @Override
    public void onNetworkConnected() {
        hideSnackBar();
        showOrHideViews(View.VISIBLE);
        getMobiles();
        getLaptops();
        getHistory();
        Log.d(TAG, "onNetworkConnected");
        getUserImage();
    }

    @Override
    public void onNetworkDisconnected() {
        showSnackBar();
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(ProductActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem addMenu = menu.findItem(R.id.action_addProduct);
        boolean isAdmin = LoginUtils.getInstance(this).getUserInfo().isAdmin();
        addMenu.setVisible(isAdmin);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent cartIntent = new Intent(this, CartActivity.class);
                startActivity(cartIntent);
                return true;
            case R.id.action_qr:
                Intent qrIntent = new Intent(this, QrCodeActivity.class);
                startActivity(qrIntent);
                return true;
            case R.id.action_addProduct:
                Intent addProductIntent = new Intent(this, AddProductActivity.class);
                startActivity(addProductIntent);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void showOrHideViews(int view) {
        binding.included.content.textViewMobiles.setVisibility(view);
        binding.included.content.txtSeeAllMobiles.setVisibility(view);
        binding.included.content.textViewLaptops.setVisibility(view);
        binding.included.content.txtSeeAllLaptops.setVisibility(view);
        binding.included.content.txtCash.setVisibility(view);
        binding.included.content.txtReturn.setVisibility(view);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_mobiles) {
            Intent laptopIntent = new Intent(this, AllMobilesActivity.class);
            bundle = new Bundle();
            bundle.putString("AllList", "Makeup");
            laptopIntent.putExtras(bundle);
            startActivity(laptopIntent);
        } else if (id == R.id.nav_laptops) {
            Intent laptopIntent = new Intent(this, AllMobilesActivity.class);
            bundle = new Bundle();
            bundle.putString("AllList", "Skincare");
            laptopIntent.putExtras(bundle);
            startActivity(laptopIntent);
        } else if (id == R.id.nav_babies) {
            goToCategoryActivity("Baby");
        } else if (id == R.id.nav_toys) {
            Intent dealIntent = new Intent(this, DealsActivity.class);
            bundle = new Bundle();
            bundle.putString("Deal", "Deal");
            dealIntent.putExtras(bundle);
            startActivity(dealIntent);
        } else if (id == R.id.nav_trackOrder) {
            Intent orderIntent = new Intent(this, OrdersActivity.class);
            startActivity(orderIntent);
        } else if (id == R.id.nav_myAccount) {
            Intent accountIntent = new Intent(this, AccountActivity.class);
            startActivity(accountIntent);
        } else if (id == R.id.nav_newsFeed) {
            Intent newsFeedIntent = new Intent(this, NewsFeedActivity.class);
            startActivity(newsFeedIntent);
        } else if (id == R.id.nav_wishList) {
            Intent wishListIntent = new Intent(this, WishListActivity.class);
            startActivity(wishListIntent);
        }
        else if (id == R.id.app_tour) {
            Intent tourIntent = new Intent(this, WelcomeActivity.class);
            startActivity(tourIntent);
        } else if (id == R.id.explore) {
            tapview();
        }
        else if (id == R.id.logout) {
            signOut();
            return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void signOut() {
        LoginUtils.getInstance(this).clearAll();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void tapview() {

        new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(findViewById(R.id.action_qr), "Qr Code", "Bạn có thể check giá của sản phẩm nhanh hơn ở đây nhé !")
                                .targetCircleColor(R.color.white)
                                .titleTextColor(R.color.white)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.blue)
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.centerColor),
                        TapTarget.forView(findViewById(R.id.action_cart), "Giỏ hàng", "Nơi chứa đựng các sản phẩm mà bạn muốn mua !")
                                .targetCircleColor(R.color.white)
                                .titleTextColor(R.color.white)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.orange)
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.centerColor),
                        TapTarget.forView(findViewById(R.id.imageView5), "Danh mục", "Chứa danh mục sác lại sản phẩm !")
                                .targetCircleColor(R.color.white)
                                .titleTextColor(R.color.white)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.orange)
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.centerColor),
                        TapTarget.forView(findViewById(R.id.imageView6), "Deals", "Nơi chứa những mã giảm giá mới nhất !")
                                .targetCircleColor(R.color.white)
                                .titleTextColor(R.color.white)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.white)
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.colorAccent),
                        TapTarget.forView(findViewById(R.id.imageView7), "Bán chạy", "Danh sách những sản phẩm bán chạy !")
                                .targetCircleColor(R.color.dark)
                                .titleTextColor(R.color.dark)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.dark)
                                .drawShadow(true)
                                .cancelable(false)// Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.white),
                        TapTarget.forView(findViewById(R.id.imageView8), "Yêu thích", "Danh sách các sản phẩm yêu thích !")
                                .targetCircleColor(R.color.colorAccent)
                                .titleTextColor(R.color.colorAccent)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.orange)
                                .descriptionTextColor(R.color.orange)
                                .drawShadow(true)
                                .cancelable(false)// Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.dark))
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                        //session.setFirstTime(false);
                        //Toasty.success(ProductActivity.this, " You are ready to go !", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ProductActivity.this,"Bắt đầu mua hàng thôi nào !",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        // Boo
                    }
                }).start();

    }
    private void goToCategoryActivity(String category) {
        Intent categoryIntent = new Intent(this, CategoryActivity.class);
        categoryIntent.putExtra(CATEGORY, category);
        startActivity(categoryIntent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            closeApplication();
        }
    }

    private void closeApplication() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.want_to_exit)
                .setPositiveButton(R.string.ok, (dialog, which) -> finish())
                .setNegativeButton(R.string.cancel, null)
                .show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this,R.color.darkGreen));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.darkGreen));
    }


    @Override
    protected void onResume() {
        super.onResume();
        productViewModel.invalidate();
        getMobiles();
        getLaptops();
        historyViewModel.invalidate();
        getHistory();
    }

    private final PagedList.Callback productCallback = new PagedList.Callback() {
        @Override
        public void onChanged(int position, int count) {
            Log.d(TAG, "onChanged: "+ count);
        }

        @Override
        public void onInserted(int position, int count) {
            Log.d(TAG, "onInserted: "+ count);
            if (count != 0) {
                binding.included.content.textViewHistory.setVisibility(View.VISIBLE);
                historyAdapter.notifyOnInsertedItem(position);
                getHistory();
            }
        }

        @Override
        public void onRemoved(int position, int count) {
            Log.d(TAG, "onRemoved: "+ count);
        }
    };

}
