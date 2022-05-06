package com.transport.khata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.transport.khata.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    String ownerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        if (getIntent() != null && getIntent().getStringExtra("intentparamaeter") != null && getIntent().getStringExtra("intentparamaeter").toString().equalsIgnoreCase("AddTripFragment")){
//            replaceFragment(new AddTripFragment());
//        } else {
//            replaceFragment(new HomeFragment());
//        }

        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.home_fragment:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.add_trip_fragment:
                    replaceFragment(new AddTripFragment());
                    break;

                case R.id.view_trip_fragment:
                    replaceFragment(new ViewJobFragment());
                    break;

                case R.id.my_truck_fragment:
                    replaceFragment(new MyTruckFragment());
                    break;
            }


            return true;
        });
    }


    private  void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}