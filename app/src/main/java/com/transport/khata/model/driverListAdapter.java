package com.transport.khata.model;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.transport.khata.R;
import com.transport.khata.driverDocumentsFragment;

import java.util.ArrayList;

public class driverListAdapter extends ArrayAdapter<driverListClass> {
    Context contextVar;
    ArrayList<driverListClass> driverArrayListAll;
    public driverListAdapter(@NonNull Context context, ArrayList<driverListClass> arrayList) {
        super(context,0, arrayList);
        contextVar = context;
        this.driverArrayListAll= new ArrayList<driverListClass>(arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);
        }

        driverListClass currentNumberPosition = getItem(position);
//

        TextView regdNo= currentItemView.findViewById(R.id.regdNo);
        regdNo.setText(currentNumberPosition.getdrivername());

        TextView truckType = currentItemView.findViewById(R.id.textView);
        truckType.setText(currentNumberPosition.getdriverPhone());
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
                        driverDocumentsFragment editFragment = new driverDocumentsFragment();
                        Log.e("TESTING","testing");
                        Bundle args = new Bundle();
                        args.putString("driverNo", currentNumberPosition.getdriverPhone());
                        args.putString("driverName", currentNumberPosition.getdrivername());
                        FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        editFragment.setArguments(args);
                        fragmentTransaction.replace(R.id.driverList_layout, editFragment);
                        fragmentTransaction.commit();
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

        currentItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverDocumentsFragment editFragment = new driverDocumentsFragment();
                Log.e("TESTING","testing");
                Bundle args = new Bundle();
                args.putString("key", currentNumberPosition.getdriverPhone());
                args.putString("driverNo", currentNumberPosition.getdriverPhone());
                args.putString("driverName", currentNumberPosition.getdrivername());
                args.putString("documentFor", "driver");
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                editFragment.setArguments(args);
                fragmentTransaction.replace(R.id.driverList_layout, editFragment);
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
            ArrayList<driverListClass> arrayListFilter= new ArrayList<>();
            String searchText = constraint.toString().toLowerCase();
            String[] split = searchText.split(",");
            ArrayList<String > searchArr = new ArrayList<>(split.length);

            if(searchText.isEmpty()){
                arrayListFilter=driverArrayListAll;
            } else {
                for(String tsplit:split){
                    String trim = tsplit.trim();
                    if(trim.length()>0){
                        searchArr.add(trim);
                    }

                    for(driverListClass driver : driverArrayListAll){
                        if(driver.getdrivername().toLowerCase().trim().contains(searchText)){
                            arrayListFilter.add(driver);
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
            addAll((ArrayList<driverListClass>) results.values);
            notifyDataSetChanged();
        }
    };
}
