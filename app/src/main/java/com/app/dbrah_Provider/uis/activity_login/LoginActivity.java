package com.app.dbrah_Provider.uis.activity_login;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.adapter.CountryAdapter;
import com.app.dbrah_Provider.databinding.ActivityLoginBinding;
import com.app.dbrah_Provider.databinding.DialogCountriesBinding;
import com.app.dbrah_Provider.model.CountryModel;
import com.app.dbrah_Provider.model.LoginModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.mvvm.ActivityLoginMvvm;
import com.app.dbrah_Provider.uis.activity_forget_password.ForgetPasswordActivity;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;
import com.app.dbrah_Provider.uis.activity_sign_up.SignUpActivity;
import com.app.dbrah_Provider.uis.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private String phone_code = "";
    private String phone = "";
    private LoginModel model;
    private ActivityResultLauncher<Intent> launcher;
    private List<CountryModel> countryModelList = new ArrayList<>();
    private CountryAdapter countriesAdapter;
    private AlertDialog dialog;
    private ActivityLoginMvvm activityLoginMvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }


    private void initView() {

        model = new LoginModel();
        binding.setModel(model);
        activityLoginMvvm = ViewModelProviders.of(this).get(ActivityLoginMvvm.class);
        binding.tvSignUp.setPaintFlags(binding.tvSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.setLang(getLang());
        activityLoginMvvm.getCoListMutableLiveData().observe(this, countryModels -> {
            if (countryModels != null && countryModels.size() > 0) {
                countryModelList.clear();
                countryModelList.addAll(countryModels);
            }
        });
        activityLoginMvvm.setCountry();



         activityLoginMvvm.getUserModelMutableLiveData().observe(this, userModel -> {
             setUserModel(userModel);
             navigateToHomeActivity();
         });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        });
        binding.tvSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
        binding.tvForgetPassword.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
            startActivity(intent);
        });

        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("0")) {
                    binding.edtPhone.setText("");
                }
            }
        });

        binding.consPhone.setVisibility(View.VISIBLE);
        binding.imFalg.setImageDrawable(getResources().getDrawable(R.drawable.flag_eg));
        model.setPhone_code("+20");
        model.setCode("EG");
        model.setType("phone");
        binding.llEmail.setVisibility(View.GONE);
        binding.tvEmail.setOnClickListener(view -> {
            binding.viewEmail.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            binding.viewPhone.setBackgroundColor(getResources().getColor(R.color.grey8));
            binding.tvEmail.setTextColor(getResources().getColor(R.color.colorAccent));
            binding.tvPhone.setTextColor(getResources().getColor(R.color.black2));
            binding.llEmail.setVisibility(View.VISIBLE);
            binding.consPhone.setVisibility(View.GONE);
            model.setType("email");

        });
        binding.tvPhone.setOnClickListener(view -> {
            binding.viewEmail.setBackgroundColor(getResources().getColor(R.color.grey8));
            binding.viewPhone.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            binding.tvEmail.setTextColor(getResources().getColor(R.color.black2));
            binding.tvPhone.setTextColor(getResources().getColor(R.color.colorAccent));
            binding.consPhone.setVisibility(View.VISIBLE);
            binding.llEmail.setVisibility(View.GONE);
            model.setType("phone");

        });
        binding.arrow.setOnClickListener(view -> dialog.show());
        sortCountries();
        createCountriesDialog();
        binding.btnLogin.setOnClickListener(view -> {
            if (model.isDataValid(LoginActivity.this)) {
                activityLoginMvvm.login(model,this);
            }
        });
    }


    private void createCountriesDialog() {

        dialog = new AlertDialog.Builder(this)
                .create();
        countriesAdapter = new CountryAdapter(this);
        countriesAdapter.updateList(countryModelList);
        DialogCountriesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_countries, null, false);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(countriesAdapter);

        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
    }

    private void sortCountries() {
        Collections.sort(countryModelList, (country1, country2) -> {
            return country1.getName().trim().compareToIgnoreCase(country2.getName().trim());
        });
    }

    private void navigateToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void setItemData(CountryModel countryModel) {
        dialog.dismiss();
        model.setPhone_code(countryModel.getDialCode());
        model.setCode(countryModel.getCode());
        binding.setModel(model);
        binding.imFalg.setImageResource(countryModel.getFlag());
    }
}