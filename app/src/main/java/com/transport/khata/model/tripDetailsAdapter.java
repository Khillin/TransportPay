package com.transport.khata.model;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.transport.khata.R;
import com.transport.khata.TruckEditDocumentFragment;
import com.transport.khata.editPartyCollection;

import java.io.Serializable;
import java.util.ArrayList;

public class tripDetailsAdapter extends ArrayAdapter<tripDetails>  {

    private Context contextVar;


    public tripDetailsAdapter(@NonNull Context context, ArrayList<tripDetails> arrayList) {
        super(context,0, arrayList);
        this.contextVar = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.row_item_trip, parent, false);
        }

        tripDetails currentNumberPosition = getItem(position);

        TextView partyName= currentItemView.findViewById(R.id.partyName);
        partyName.setText(currentNumberPosition.getpartyName());

        TextView partyNumber = currentItemView.findViewById(R.id.partyNumber);
        partyNumber.setText((Long.toString( currentNumberPosition.getdriverPhone())));

        TextView amount = currentItemView.findViewById(R.id.billAmount);
        amount.setText((Integer.toString( currentNumberPosition.getbillAmount())));

        TextView originCity = currentItemView.findViewById(R.id.originCity);
        originCity.setText(currentNumberPosition.getoriginAddress());

        TextView destinationCity = currentItemView.findViewById(R.id.destinationCity);
        destinationCity.setText(currentNumberPosition.getdestinationAddress());

        TextView advance = currentItemView.findViewById(R.id.advance);
        advance.setText((Integer.toString(currentNumberPosition.getAdvance())));

        TextView pendingAmount = currentItemView.findViewById(R.id.pendingAmount);
        pendingAmount.setText((Integer.toString( currentNumberPosition.getbillAmount() - currentNumberPosition.getAdvance())));

//        Spinner more_options = (Spinner) currentItemView.findViewById(R.id.more_options);
        Button button = (Button) currentItemView.findViewById(R.id.reqPayment);
        button.setText("Request Payment - " + Integer.toString( currentNumberPosition.getbillAmount() - currentNumberPosition.getAdvance()));

        TextView viewInvoice = currentItemView.findViewById(R.id.viewInvoice);


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Initializing the popup menu and giving the reference as current context
//                PopupMenu popupMenu = new PopupMenu(contextVar, button);
//
//                // Inflating popup menu from popup_menu.xml file
//                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        // Toast message on menu item clicked
//                        TruckEditDocumentFragment editFragment = new TruckEditDocumentFragment();
//                        Bundle args = new Bundle();
//                        args.putString("truckNo", currentNumberPosition.getRegdNo());
//                        args.putString("truckType", currentNumberPosition.getTruckType());
//                        args.putString("ownerId", currentNumberPosition.getOwner());
//                        FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        editFragment.setArguments(args);
//                        fragmentTransaction.replace(R.id.frame_layout, editFragment);
//                        fragmentTransaction.commit();
//                        return true;
//                    }
//                });
//                // Showing the popup menu
//                popupMenu.show();
//            }
//        });

//        if(position % 2 == 0){
//            currentItemView.setBackgroundColor(Color.parseColor("#F3F5F7"));
//        }

        viewInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPartyCollection editFragment = new editPartyCollection();
                Bundle args = new Bundle();
//                args.putString("partyName", currentNumberPosition.getpartyName());
//                args.putString("originCity", currentNumberPosition.getoriginAddress());
//                args.putString("destinationCity", currentNumberPosition.getdestinationAddress());
//                args.putString("ownerId", currentNumberPosition.getownerid());
//                args.putString("billType", currentNumberPosition.getbillType());
//                args.putString("amount", Integer.toString(currentNumberPosition.getbillAmount()));
//                args.putString("tripId", currentNumberPosition.gettripId());
                args.putSerializable("Trip", (Serializable) currentNumberPosition);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                editFragment.setArguments(args);
                fragmentTransaction.replace(R.id.party_activity_layout, editFragment);
                fragmentTransaction.commit();
            }
        });

        return currentItemView;
    }
}

