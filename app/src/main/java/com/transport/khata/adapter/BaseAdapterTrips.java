package com.transport.khata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.transport.khata.R;

import java.util.ArrayList;

public class BaseAdapterTrips extends BaseAdapter {
    Context context;
//    String partyNameList[];
    String tripStatusList[];
//    String originList[];
//    String destinationList[];
//    String startDateList[];
    ArrayList<String> partyNameList = new ArrayList<>();
    //        ArrayList<String> tripStatusList = new ArrayList<>();
    ArrayList<String> originList = new ArrayList<>();
    ArrayList<String> destinationList = new ArrayList<>();
    ArrayList<String> startDateList = new ArrayList<>();
    LayoutInflater layoutInflater;

    public BaseAdapterTrips(Context context, String[] tripStatusList, ArrayList<String> partyNameList, ArrayList<String> originList, ArrayList<String> destinationList, ArrayList<String> startDateList) {
        this.context = context;
        this.tripStatusList = tripStatusList;
        this.partyNameList = partyNameList;
        this.originList = originList;
        this.destinationList = destinationList;
        this.startDateList = startDateList;
        layoutInflater = LayoutInflater.from(context);
    }

//    public BaseAdapterTrips(Context context, String [] partyNameList, String [] tripStatusList, String [] originList, String [] destinationList, String [] startDateList){
//        this.context = context;
//        this.partyNameList = partyNameList;
//        this.tripStatusList = tripStatusList;
//        this.originList = originList;
//        this.destinationList = destinationList;
//        this.startDateList = startDateList;
//        layoutInflater = LayoutInflater.from(context);
//    }
    @Override
    public int getCount() {
        return partyNameList.size();
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
        view = layoutInflater.inflate(R.layout.activity_list_view_trips,null);
        TextView partyNameListView = (TextView) view.findViewById(R.id.party_name);
        TextView tripStatusListView = (TextView) view.findViewById(R.id.trip_status);
        TextView originListView = (TextView) view.findViewById(R.id.origin);
        TextView destinationListView = (TextView) view.findViewById(R.id.destination);
        TextView startDateListView = (TextView) view.findViewById(R.id.start_date);

        partyNameListView.setText(partyNameList.get(i));
        tripStatusListView.setText(tripStatusList[i]);
        originListView.setText(originList.get(i));
        destinationListView.setText(originList.get(i));
        startDateListView.setText(startDateList.get(i));
        return view;
    }
}
