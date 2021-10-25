package com.ssd.ssd.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.ssd.ssd.MainActivity;
import com.ssd.ssd.R;
import com.ssd.ssd.database.AppDatabase;
import com.ssd.ssd.database.dao.UsersDao;
import com.ssd.ssd.database.entity.UserEntity;
import com.ssd.ssd.databinding.ActivityLoginBinding;
import com.ssd.ssd.session.Preferences;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AppDatabase database;
    private List<UserEntity> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.getLifecycleOwner();

        database = AppDatabase.getInstance(this);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.edtemail.getText().toString().trim();
                String password = binding.edtpassword.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()){
                    final UsersDao userDao = database.userDao();
                    UserEntity userEntity =  userDao.login(email,password);
                    if (userEntity==null){
                        Snackbar.make(v, "Login Gagal ", Snackbar.LENGTH_LONG).show();
                    }else {
                        Preferences.setIsLogin(getBaseContext(),true);
                        Preferences.setIsFirstname(getBaseContext(),userEntity.getFirstname());
                        Preferences.setIsNamabelakang(getBaseContext(),userEntity.getNama_belakang());
                        Preferences.setIsNohp(getBaseContext(),userEntity.getNomor_hp());
                        Preferences.setIsEmail(getBaseContext(),userEntity.getEmail());
                        Preferences.setIsPassword(getBaseContext(),userEntity.getPassword());
                        Preferences.setIsAlamat(getBaseContext(),userEntity.getAlamat());
                        Preferences.setIsKota(getBaseContext(),userEntity.getKota());
                        Preferences.setIsIdUser(getBaseContext(),userEntity.getId_user());

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }else {
                    Snackbar.make(v, "Isi semua kolom ", Snackbar.LENGTH_LONG).show();
                }

            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Preferences.getIsLogin(getBaseContext())){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}