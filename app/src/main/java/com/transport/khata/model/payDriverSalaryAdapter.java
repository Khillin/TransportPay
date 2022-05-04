package com.transport.khata.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.transport.khata.PayDriver;
import com.transport.khata.PayDriverSalaryActivity;
import com.transport.khata.R;
import com.transport.khata.driverDocumentsFragment;

import java.util.ArrayList;

public class payDriverSalaryAdapter extends ArrayAdapter<driverListClass> {
    Context contextVar;

    public payDriverSalaryAdapter(@NonNull Context context, ArrayList<driverListClass> arrayList) {
        super(context, 0, arrayList);
        contextVar = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.row_item_pay_driver, parent, false);
        }

        driverListClass currentNumberPosition = getItem(position);
//

        TextView regdNo = currentItemView.findViewById(R.id.regdNo);
        regdNo.setText(currentNumberPosition.getdrivername());

        TextView truckType = currentItemView.findViewById(R.id.textView);
        truckType.setText(currentNumberPosition.getdriverPhone());
        Button button = (Button) currentItemView.findViewById(R.id.paySalary);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                Intent intent = new Intent(getContext().getApplicationContext(), PayDriver.class);
                contextVar.startActivity(intent);

            }
        });

        currentItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverDocumentsFragment editFragment = new driverDocumentsFragment();
                Log.e("TESTING", "testing");
                Bundle args = new Bundle();
                args.putString("key", currentNumberPosition.getdriverPhone());
                args.putString("driverNo", currentNumberPosition.getdriverPhone());
                args.putString("driverName", currentNumberPosition.getdrivername());
                args.putString("documentFor", "driver");
                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                editFragment.setArguments(args);
                fragmentTransaction.replace(R.id.driverList_layout, editFragment);
                fragmentTransaction.commit();
            }
        });

        return currentItemView;
    }
}
