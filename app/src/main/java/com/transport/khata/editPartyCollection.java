package com.transport.khata;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.transport.khata.model.TruckDetails;
import com.transport.khata.model.tripDetails;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editPartyCollection#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editPartyCollection extends Fragment {

    String billType, destinationAddress,driverName,originAddress,ownerid,partyName,status, tripId,truckNum, billTypeFin ;
    int advance, billAmount;
    JSONObject startDate;
    Long driverPhone;

    EditText editBillAmount;
    TextView partyNameText, originPlace, destinationPlace;

    Button makeEditable, billType1, billType2, billType3, editPArtyCollectionSubmit;

    FirebaseDatabase rootNode;
    DatabaseReference referenceTrip;

    tripDetails tripDemo;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public editPartyCollection() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment editPartyCollection.
     */
    // TODO: Rename and change types and number of parameters
    public static editPartyCollection newInstance(String param1, String param2) {
        editPartyCollection fragment = new editPartyCollection();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_party_collection, container, false);
        Bundle bundle = this.getArguments();
        rootNode = FirebaseDatabase.getInstance();
        referenceTrip = rootNode.getReference("Trips");
        tripDemo = (tripDetails) getArguments().getSerializable("Trip");
//        billType = bundle.getString("billType");
//        destinationAddress = bundle.getString("destinationCity");
//        originAddress = bundle.getString("originCity");
//        partyName = bundle.getString("partyName");
//        billAmount = Integer.parseInt(bundle.getString("amount"));
//        tripId = bundle.getString("tripId");
        editBillAmount = view.findViewById(R.id.editBillAmount);
        editBillAmount.setText(Integer.toString(tripDemo.getbillAmount()));
        partyNameText = view.findViewById(R.id.partyNameEdit);
        partyNameText.setText(tripDemo.getpartyName());
        originPlace = view.findViewById(R.id.originPlace);
        originPlace.setText(tripDemo.getoriginAddress());
        destinationPlace = view.findViewById(R.id.destinationPlace);
        destinationPlace.setText(tripDemo.getdestinationAddress());
        billType1 = view.findViewById(R.id.billType1);
        billType2 = view.findViewById(R.id.billType2);
        billType3 = view.findViewById(R.id.billType3);
        billTypeFin = tripDemo.getbillType();
        Log.e("TRIP", tripDemo.getpartyName());

        editPArtyCollectionSubmit = view.findViewById(R.id.editPArtyCollectionSubmit);

        makeEditable = view.findViewById(R.id.makeEditableType);
        makeEditable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                truckNo.setFocusable(true);
//                truckNo.setCursorVisible(true);
                editBillAmount.setEnabled(true);
            }});
        billType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billTypeFin = "billType1";
                billType1.setBackgroundColor(Color.parseColor("#1C439D"));
                billType1.setTextColor(Color.parseColor("#FFFFFF"));
                billType2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                billType3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                billType2.setTextColor(Color.parseColor("#212121"));
                billType3.setTextColor(Color.parseColor("#212121"));
            }});

        billType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billTypeFin = "billType2";
                billType2.setBackgroundColor(Color.parseColor("#1C439D"));
                billType2.setTextColor(Color.parseColor("#FFFFFF"));
                billType1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                billType3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                billType1.setTextColor(Color.parseColor("#212121"));
                billType3.setTextColor(Color.parseColor("#212121"));

            }});

        billType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billTypeFin = "billType3";
                billType3.setBackgroundColor(Color.parseColor("#1C439D"));
                billType3.setTextColor(Color.parseColor("#FFFFFF"));
                billType2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                billType1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                billType1.setTextColor(Color.parseColor("#212121"));
                billType2.setTextColor(Color.parseColor("#212121"));
            }});


        switch (billTypeFin){
            case "truckType1":
                billType1.setBackgroundColor(Color.parseColor("#1C439D"));
                billType1.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case "truckType2":
                billType2.setBackgroundColor(Color.parseColor("#1C439D"));
                billType2.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case "tone":
                billType3.setBackgroundColor(Color.parseColor("#1C439D"));
                billType3.setTextColor(Color.parseColor("#FFFFFF"));
                break;
        }

        editPArtyCollectionSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tripDetails trip = new tripDetails(tripDemo.getAdvance(), Integer.parseInt(String.valueOf(editBillAmount.getText())), billTypeFin, tripDemo.getdestinationAddress(), tripDemo.getdriverName(), tripDemo.getdriverPhone(), tripDemo.getoriginAddress(), tripDemo.getownerid(), tripDemo.getpartyName(), tripDemo.getstartDate(),tripDemo.getstatus(), tripDemo.gettripId(), tripDemo.gettruckNum());
//                referenceTrip.child(tripDemo.gettripId().toString()).removeValue();
                referenceTrip.child(tripDemo.gettripId().toString()).setValue(trip);
            }
        });
        return view;
    }
}