package com.transport.khata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.transport.khata.R;
import com.transport.khata.model.CreateTripHelperClass;
import com.transport.khata.model.driverListClass;

import java.util.ArrayList;
import java.util.List;

public class DriverTripInfoAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> truckList = new ArrayList<>();
    ArrayList <String> driverNameList = new ArrayList<>();
    ArrayList <String> driverPhoneList = new ArrayList<>();
    LayoutInflater layoutInflater;


    public DriverTripInfoAdapter(Context context, ArrayList<String> truckList, ArrayList<String> driverNameList, ArrayList<String> driverPhoneList) {
        this.context = context;
        this.truckList = truckList;
        this.driverNameList = driverNameList;
        this.driverPhoneList = driverPhoneList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return truckList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.list_driver_trip_info,null);
        TextView driverName = (TextView) view.findViewById(R.id.driver_name);
        TextView truckNo = (TextView) view.findViewById(R.id.regdno);
        ImageView phoneImg= (ImageView) view.findViewById(R.id.image_phone);

        driverName.setText(driverNameList.get(i).toString());
        truckNo.setText(truckList.get(i).toString());

        return view;
    }
}
