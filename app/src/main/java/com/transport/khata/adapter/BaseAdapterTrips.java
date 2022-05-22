package com.transport.khata.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.GnssAntennaInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.ValueEventListener;
import com.transport.khata.R;
import com.transport.khata.ViewJobFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class BaseAdapterTrips extends BaseAdapter implements Filterable {
    Context context;
    BaseAdapterInterface buttonListener;
    ArrayList<String> partyNameList = new ArrayList<>();
    ArrayList<String> tripStatusList = new ArrayList<>();
    ArrayList<String> originList = new ArrayList<>();
    ArrayList<String> destinationList = new ArrayList<>();
    ArrayList<String> startDateList = new ArrayList<>();
    ArrayList<String> partyNameListAll = new ArrayList<>();
    ArrayList<String> tripStatusListAll = new ArrayList<>();
    ArrayList<String> originListAll = new ArrayList<>();
    ArrayList<String> destinationListAll = new ArrayList<>();
    ArrayList<String> startDateListAll = new ArrayList<>();
    LayoutInflater layoutInflater;

    public BaseAdapterTrips(Context context, ArrayList<String>  tripStatusList, ArrayList<String> partyNameList, ArrayList<String> originList, ArrayList<String> destinationList, ArrayList<String> startDateList, Fragment buttonListener) {
        this.context = context;
        this.tripStatusList = tripStatusList;
        this.partyNameList = partyNameList;
        this.originList = originList;
        this.destinationList = destinationList;
        this.startDateList = startDateList;
        this.tripStatusListAll = tripStatusList;
        this.partyNameListAll = partyNameList;
        this.originListAll = originList;
        this.destinationListAll = destinationList;
        this.startDateListAll = startDateList;
        this.buttonListener = (BaseAdapterInterface) buttonListener;
        layoutInflater = LayoutInflater.from(context);
    }


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
        Button upload = view.findViewById(R.id.status_update);

        partyNameListView.setText(partyNameList.get(i));
        tripStatusListView.setText(tripStatusList.get(i));
        originListView.setText(originList.get(i));
        destinationListView.setText(originList.get(i));
        startDateListView.setText(startDateList.get(i));


        //gallery option
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonListener.onClickBtn();
            }
        });

        return view;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<String> partyNameListFilter= new ArrayList<>();
            ArrayList<String> tripStatusListFilter= new ArrayList<>();
            ArrayList<String> originListFilter= new ArrayList<>();
            ArrayList<String> destinationListFilter= new ArrayList<>();
            ArrayList<String> startDateListFilter= new ArrayList<>();
            String searchText = constraint.toString().toLowerCase();
            String[] split = searchText.split(",");
            ArrayList<String > searchArr = new ArrayList<>(split.length);
            ArrayList<Integer> indexList = new ArrayList<>();

            if(searchText.isEmpty()){
                for(Integer min=0; min<partyNameListAll.size(); min++){
                    indexList.add(min);
                }
            } else {
                for(String tsplit:split){
                    String trim = tsplit.trim();
                    if(trim.length()>0){
                        searchArr.add(trim);
                    }

                    for(Integer i=0;i<partyNameListAll.size();i++){
                        if(partyNameListAll.get(i).toLowerCase().trim().contains(searchText)){
                           indexList.add(i);
                        } else if (tripStatusListAll.get(i).toLowerCase().trim().contains(searchText)){
                            indexList.add(i);
                        } else if(originListAll.get(i).toLowerCase().trim().contains(searchText)){
                            indexList.add(i);
                        } else if(destinationListAll.get(i).toLowerCase().trim().contains(searchText)){
                            indexList.add(i);
                        } else if(startDateListAll.get(i).toLowerCase().trim().contains(searchText)){
                            indexList.add(i);
                        }
                    }
                }

            }
            results.count=indexList.size();
            results.values=indexList;
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Integer> indexList = (ArrayList<Integer>) results.values;
            ArrayList<String> partyNameListFilter= new ArrayList<>();
            ArrayList<String> tripStatusListFilter= new ArrayList<>();
            ArrayList<String> originListFilter= new ArrayList<>();
            ArrayList<String> destinationListFilter= new ArrayList<>();
            ArrayList<String> startDateListFilter= new ArrayList<>();
            for(Integer i=0;i<indexList.size();i++){
                partyNameListFilter.add(partyNameListAll.get(indexList.get(i)));
                tripStatusListFilter.add(tripStatusListAll.get(indexList.get(i)));
                originListFilter.add(originListAll.get(indexList.get(i)));
                destinationListFilter.add(destinationListAll.get(indexList.get(i)));
                startDateListFilter.add(startDateListAll.get(indexList.get(i)));
            }

            partyNameList = partyNameListFilter;
            tripStatusList = tripStatusListFilter;
            originList =originListFilter;
            destinationList = destinationListFilter;
            startDateList = startDateListFilter;
            notifyDataSetChanged();
        }
    };

    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MyAdapter", "onActivityResult");
    }
}
