package com.ssd.ssd.auth;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.ssd.ssd.R;
import com.ssd.ssd.database.AppDatabase;
import com.ssd.ssd.database.entity.UserEntity;
import com.ssd.ssd.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.getLifecycleOwner();
        database = AppDatabase.getInstance(this);


        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = binding.edtfirstname.getText().toString().trim();
                String nama_belakang = binding.edtnamabelakang.getText().toString().trim();
                String no_hp = binding.edtnohp.getText().toString().trim();
                String email = binding.edtemail.getText().toString().trim();
                String password = binding.edtpassword.getText().toString().trim();
                String alamat = binding.edtalamat.getText().toString().trim();
                String kota = binding.edtkota.getText().toString().trim();

                if (!firstname.isEmpty() && !nama_belakang.isEmpty() && !no_hp.isEmpty()
                && !email.isEmpty() && !password.isEmpty() && !alamat.isEmpty() && !kota.isEmpty()) {
                    UserEntity userEntity = database.userDao().cekuser(email);
                    if (userEntity==null){
                        database.userDao().insertAuth(firstname,nama_belakang,no_hp,email,password,alamat,kota);
                        finish();
                    }else {
                        Snackbar.make(v, "email sudah terdaftar", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(v, "Isi semua kolom ", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
}