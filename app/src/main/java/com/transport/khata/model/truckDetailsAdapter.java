package com.transport.khata.model;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.transport.khata.R;
import com.transport.khata.TruckEditDocumentFragment;

import java.util.ArrayList;

public class truckDetailsAdapter extends ArrayAdapter<TruckDetails>{

    private Context contextVar;
    ArrayList<TruckDetails> truckArrayListAll;


    public truckDetailsAdapter(@NonNull Context context, ArrayList<TruckDetails> arrayList) {
        super(context,0, arrayList);
        this.contextVar = context;
        this.truckArrayListAll= new ArrayList<TruckDetails>(arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);
        }

        TruckDetails currentNumberPosition = getItem(position);

        TextView regdNo= currentItemView.findViewById(R.id.regdNo);
        regdNo.setText(currentNumberPosition.getRegdNo());

        TextView truckType = currentItemView.findViewById(R.id.textView);
        truckType.setText(currentNumberPosition.getTruckType());

//        Spinner more_options = (Spinner) currentItemView.findViewById(R.id.more_options);
        Button button = (Button) currentItemView.findViewById(R.id.moreOptions);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(contextVar, button);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        TruckEditDocumentFragment editFragment = new TruckEditDocumentFragment();
                        Bundle args = new Bundle();
                        args.putString("truckNo", currentNumberPosition.getRegdNo());
                        args.putString("truckType", currentNumberPosition.getTruckType());
                        args.putString("ownerId", currentNumberPosition.getOwner());
                        args.putString("documentFor", "trucks");
                        FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        editFragment.setArguments(args);
                        fragmentTransaction.replace(R.id.frame_layout, editFragment);
                        fragmentTransaction.commit();
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

        if(position % 2 == 0){
            currentItemView.setBackgroundColor(Color.parseColor("#F3F5F7"));
        }

        currentItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TruckEditDocumentFragment editFragment = new TruckEditDocumentFragment();
                Bundle args = new Bundle();
                args.putString("key", currentNumberPosition.getRegdNo());
                args.putString("truckNo", currentNumberPosition.getRegdNo());
                args.putString("truckType", currentNumberPosition.getTruckType());
                args.putString("ownerId", currentNumberPosition.getOwner());
                args.putString("documentFor", "truck");
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                editFragment.setArguments(args);
                fragmentTransaction.replace(R.id.frame_layout, editFragment);
                fragmentTransaction.commit();
            }
        });

        return currentItemView;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<TruckDetails> arrayListFilter= new ArrayList<>();
            String searchText = constraint.toString().toLowerCase();
            String[] split = searchText.split(",");
            ArrayList<String > searchArr = new ArrayList<>(split.length);

            if(searchText.isEmpty()){
                arrayListFilter=truckArrayListAll;
            } else {
                for(String tsplit:split){
                    String trim = tsplit.trim();
                    if(trim.length()>0){
                        searchArr.add(trim);
                    }

                    for(TruckDetails truck : truckArrayListAll){
                        if(truck.getRegdNo().toLowerCase().trim().contains(searchText)){
                            arrayListFilter.add(truck);
                        }
                    }
                }

            }
            results.count=arrayListFilter.size();
            results.values=arrayListFilter;
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((ArrayList<TruckDetails>) results.values);
            notifyDataSetChanged();
        }
    };
}
