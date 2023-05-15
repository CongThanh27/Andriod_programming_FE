package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.storage.LanguageUtils.getEnglishState;
import static com.marwaeltayeb.souq.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.souq.storage.LanguageUtils.setEnglishState;
import static com.marwaeltayeb.souq.storage.LanguageUtils.setLocale;
import static com.marwaeltayeb.souq.utils.CommunicateUtils.rateAppOnGooglePlay;
import static com.marwaeltayeb.souq.utils.CommunicateUtils.shareApp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivityAccountBinding;
import com.marwaeltayeb.souq.model.User;
import com.marwaeltayeb.souq.net.Api;
import com.marwaeltayeb.souq.net.RetrofitClient;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.utils.FlagsManager;
import com.marwaeltayeb.souq.viewmodel.DeleteUserViewModel;
import com.marwaeltayeb.souq.viewmodel.FromHistoryViewModel;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AccountActivity";
    private DeleteUserViewModel deleteUserViewModel;
    private FromHistoryViewModel fromHistoryViewModel;

    private Button btn_save_update;
    private EditText edit_name;

    Api api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        ActivityAccountBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_account);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.my_account));

        deleteUserViewModel = ViewModelProviders.of(this).get(DeleteUserViewModel.class);
        fromHistoryViewModel = ViewModelProviders.of(this).get(FromHistoryViewModel.class);


        binding.nameOfUser.setText(LoginUtils.getInstance(this).getUserInfo().getName());
        binding.emailOfUser.setText(LoginUtils.getInstance(this).getUserInfo().getEmail());
        binding.genderOfUser.setText(LoginUtils.getInstance(this).getUserInfo().getGender());
        binding.ageOfUser.setText(LoginUtils.getInstance(this).getUserInfo().getAge());

        binding.myOrders.setOnClickListener(this);
        binding.myWishList.setOnClickListener(this);
        binding.languages.setOnClickListener(this);
        binding.helpCenter.setOnClickListener(this);
        binding.shareWithFriends.setOnClickListener(this);
        binding.rateUs.setOnClickListener(this);
        binding.changePassword.setOnClickListener(this);
        binding.deleteAccount.setOnClickListener(this);

        User user = LoginUtils.getInstance(this).getUserInfo();

        edit_name= findViewById(R.id.nameOfUser);
        btn_save_update = findViewById(R.id.btn_save_update);
        btn_save_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user.getId()));
                RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), edit_name.getText().toString());
                RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user.getEmail()));
                RequestBody password = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user.getPassword()));
                RequestBody gender = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user.getGender()));
                RequestBody age = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user.getAge()));
                int isadmin = 0;
                if(user.isAdmin()){
                    isadmin = 1;
                }
                RequestBody isAdmin = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(isadmin));

                api = RetrofitClient.getInstance().getApi();
                Call<Void> call = api.updateUser(
                        id,
                        name

                );
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(AccountActivity.this,"User is updated",Toast.LENGTH_SHORT).show();

                            LoginUtils.getInstance(AccountActivity.this).clearAll();
                            Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AccountActivity.this, "fail to update user",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AccountActivity.this,"Error "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_signOut) {
            signOut();
            deleteAllProductsInHistory();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        LoginUtils.getInstance(this).clearAll();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void deleteAllProductsInHistory() {
        fromHistoryViewModel.removeAllFromHistory().observe(this, responseBody -> Log.d(TAG,getString(R.string.all_removed)));
        FlagsManager.getInstance().setHistoryDeleted(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myOrders:
                Intent ordersIntent = new Intent(this, OrdersActivity.class);
                startActivity(ordersIntent);
                break;
            case R.id.myWishList:
                Intent wishListIntent = new Intent(this, WishListActivity.class);
                startActivity(wishListIntent);
                break;
            case R.id.languages:
                showCustomAlertDialog();
                break;
            case R.id.helpCenter:
                Intent helpCenterIntent = new Intent(this, HelpActivity.class);
                startActivity(helpCenterIntent);
                break;
            case R.id.shareWithFriends:
                shareApp(this);
                break;
            case R.id.rateUs:
                rateAppOnGooglePlay(this);
                break;
            case R.id.changePassword:
                Intent passwordIntent = new Intent(this, PasswordActivity.class);
                startActivity(passwordIntent);
                break;
            case R.id.deleteAccount:
                deleteAccount();
                break;
            default: // Should not get here
        }
    }

    private void deleteAccount() {
        deleteUserViewModel.deleteUser(LoginUtils.getInstance(this).getUserToken(), LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, responseBody -> {
            if(responseBody!= null){
                LoginUtils.getInstance(getApplicationContext()).clearAll();
                try {
                    Toast.makeText(AccountActivity.this, responseBody.string() + "", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: delete account" + responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                goToLoginActivity();
            }
        });
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showCustomAlertDialog() {
        final Dialog dialog = new Dialog(AccountActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_language_dialog);

        Button english = dialog.findViewById(R.id.txtEnglish);
        Button vietnamese = dialog.findViewById(R.id.txtVietnamese);

        if(getEnglishState(this)){
            english.setEnabled(false);
            english.setAlpha(.5f);
            vietnamese.setEnabled(true);
        }else {
            vietnamese.setEnabled(false);
            vietnamese.setAlpha(.5f);
            english.setEnabled(true);
        }

        english.setOnClickListener(v -> {
            english.setEnabled(true);
            chooseEnglish();
            dialog.cancel();
        });

        vietnamese.setOnClickListener(v -> {
            vietnamese.setEnabled(true);
            chooseVietnamese();
            dialog.cancel();
        });

        dialog.show();
    }

    private void chooseEnglish() {
        setLocale(this,"en");
        recreate();
        Toast.makeText(this, "English", Toast.LENGTH_SHORT).show();
        setEnglishState(this, true);
    }

    private void chooseVietnamese() {
        setLocale(this,"ar");
        recreate();
        Toast.makeText(this, "Vietnamese", Toast.LENGTH_SHORT).show();
        setEnglishState(this, false);
    }
}