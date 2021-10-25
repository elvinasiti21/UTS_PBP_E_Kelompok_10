package com.ssd.ssd.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.ssd.ssd.DetailFoodActivity;
import com.ssd.ssd.MainActivity;
import com.ssd.ssd.R;
import com.ssd.ssd.auth.LoginActivity;
import com.ssd.ssd.database.AppDatabase;
import com.ssd.ssd.database.entity.UserEntity;
import com.ssd.ssd.databinding.FragmentProfileBinding;
import com.ssd.ssd.session.Preferences;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private AppDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false);
        binding.getLifecycleOwner();

        database = AppDatabase.getInstance(requireContext().getApplicationContext());


        binding.txtfirstname.setText(Preferences.getIsFirstname(requireContext()));
        binding.txtnamabelakang.setText(Preferences.getIsNamabelakang(requireContext()));
        binding.txtnotelp.setText(Preferences.getIsNohp(requireContext()));
        binding.txtmail.setText(Preferences.getIsEmail(requireContext()));
        binding.txtpassword.setText(Preferences.getIsPassword(requireContext()));
        binding.txtalamat.setText(Preferences.getIsAlamat(requireContext()));
        binding.txtkota.setText(Preferences.getIsKota(requireContext()));
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.setIsLogin(requireContext(),false);
                Preferences.setIsFirstname(requireContext(),"");
                Preferences.setIsNamabelakang(requireContext(),"");
                Preferences.setIsNohp(requireContext(),"");
                Preferences.setIsEmail(requireContext(),"");
                Preferences.setIsPassword(requireContext(),"");
                Preferences.setIsAlamat(requireContext(),"");
                Preferences.setIsKota(requireContext(),"");
                Preferences.setIsIdUser(requireContext(),0);

                Intent intent = new Intent(requireContext().getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();

            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstname = binding.txtfirstname.getText().toString().trim();
                String namabelakang = binding.txtnamabelakang.getText().toString().trim();
                String notelp = binding.txtnotelp.getText().toString().trim();
                String email = binding.txtmail.getText().toString().trim();
                String password = binding.txtpassword.getText().toString().trim();
                String alamat = binding.txtalamat.getText().toString().trim();
                String kota = binding.txtkota.getText().toString().trim();
                database.userDao().updateauth(firstname,namabelakang,notelp,password,alamat,kota,email);
                Preferences.setIsFirstname(requireContext(),firstname);
                Preferences.setIsNamabelakang(requireContext(),namabelakang);
                Preferences.setIsNohp(requireContext(),notelp);
                Preferences.setIsEmail(requireContext(),email);
                Preferences.setIsPassword(requireContext(),password);
                Preferences.setIsAlamat(requireContext(),alamat);
                Preferences.setIsKota(requireContext(),kota);

                Snackbar.make(v, "Update berhasil", Snackbar.LENGTH_LONG).show();
            }
        });
        return  binding.getRoot();
    }
}