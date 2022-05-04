package com.transport.khata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;

    ArrayList <String> truckList;
    ArrayList <String> driverNameList;
    ArrayList <String> driverPhoneList;
    ArrayList <Integer> numbering;
//    String truckList[];
//    String driverNameList[];
//    String driverPhoneList[];
    LayoutInflater layoutInflater;

    public CustomBaseAdapter(Context context, ArrayList<String> truckList, ArrayList<String> driverNameList,ArrayList<String> driverPhoneList, ArrayList<Integer> numbering){
        this.context = context;
        this.truckList = truckList;
        this.driverNameList = driverNameList;
        this.driverPhoneList = driverPhoneList;
        this.numbering = numbering;
        layoutInflater = LayoutInflater.from(context);

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
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.activity_list_view_truck_info,null);
        TextView truckListview = (TextView) view.findViewById(R.id.Listview_truckNumber);
        TextView driverNameListView = (TextView) view.findViewById(R.id.Listview_DriverName);
        TextView driverPhoneListView = (TextView) view.findViewById(R.id.Listview_DriverPhone);
        TextView number= (TextView) view.findViewById(R.id.numbering);
//        TextView index = (TextView) view.findViewById(R.id.no_of_tucks);
        truckListview.setText(truckList.get(i));
        driverNameListView.setText(driverNameList.get(i));
        driverPhoneListView.setText(driverPhoneList.get(i));
        number.setText(numbering.get(i).toString());
//        index.setText(i);
        return view;
    }
}
