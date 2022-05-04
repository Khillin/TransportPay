package com.transport.khata;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {

    CardView paySalary, myDriver, partySide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        paySalary = (CardView) view.findViewById(R.id.pay_salary);
        myDriver = (CardView) view.findViewById(R.id.my_driver);
        partySide = (CardView) view.findViewById(R.id.party_side);

        paySalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PayDriverSalaryActivity.class);
                startActivity(intent);
            }
        });

        myDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), driverListActivity.class);
                (getActivity()).startActivity(intent);
            }
        });

        partySide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), partyCollection.class);
                (getActivity()).startActivity(intent);
            }
        });
        return view;

    }
}