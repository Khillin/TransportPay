package com.transport.khata;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.transport.khata.model.TruckDetails;


public class TruckEditDocumentFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    String ownerId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    Button truckType1;
    Button truckType2;
    Button truckType3;
    EditText truckNo;
    Button editTruckSubmit;
    FirebaseDatabase rootNode;
    DatabaseReference referenceTruck, referenceOwner;
    String truckTypeField;
    Button makeEditable;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TruckEditDocumentFragment() {
        // Required empty public constructor
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

        View v = inflater.inflate(R.layout.fragment_truck_edit_document, container, false);
        Bundle bundle = this.getArguments();
        String TruckType = bundle.getString("truckType");
        String TruckNo = bundle.getString("truckNo");
        editTruckSubmit = (Button) v.findViewById(R.id.submitEditTruck);
        truckType1 = v.findViewById(R.id.truckType1);
        truckType2 = v.findViewById(R.id.truckType2);
        truckType3 = v.findViewById(R.id.truckType3);
        makeEditable = v.findViewById(R.id.makeEditableTType);
        makeEditable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                truckNo.setFocusable(true);
//                truckNo.setCursorVisible(true);
                truckNo.setEnabled(true);
            }});
        truckType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                truckTypeField = "truckType1";
                truckType1.setBackgroundColor(Color.parseColor("#1C439D"));
                truckType1.setTextColor(Color.parseColor("#FFFFFF"));
                truckType2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                truckType3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                truckType2.setTextColor(Color.parseColor("#212121"));
                truckType3.setTextColor(Color.parseColor("#212121"));
            }});

        truckType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                truckTypeField = "truckType2";
                truckType2.setBackgroundColor(Color.parseColor("#1C439D"));
                truckType2.setTextColor(Color.parseColor("#FFFFFF"));
                truckType1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                truckType3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                truckType1.setTextColor(Color.parseColor("#212121"));
                truckType3.setTextColor(Color.parseColor("#212121"));

            }});

        truckType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                truckTypeField = "truckType3";
                truckType3.setBackgroundColor(Color.parseColor("#1C439D"));
                truckType3.setTextColor(Color.parseColor("#FFFFFF"));
                truckType2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                truckType1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                truckType1.setTextColor(Color.parseColor("#212121"));
                truckType2.setTextColor(Color.parseColor("#212121"));
            }});


//        truckType = (Spinner) v.findViewById(R.id.truckTypeDropdown);
        truckNo = (EditText) v.findViewById(R.id.truckNo);
        switch (TruckType){
            case "truckType1":
                truckType1.setBackgroundColor(Color.parseColor("#1C439D"));
                truckType1.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case "truckType2":
                truckType2.setBackgroundColor(Color.parseColor("#1C439D"));
                truckType2.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case "type3":
                truckType3.setBackgroundColor(Color.parseColor("#1C439D"));
                truckType3.setTextColor(Color.parseColor("#FFFFFF"));
                break;
        }


        rootNode = FirebaseDatabase.getInstance();
        referenceTruck = rootNode.getReference("Truck");

//        truckType.setText(TruckType);
        truckNo.setText(TruckNo);
        editTruckSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                    referenceTruck.get().addOnCompleteListener(task -> {
//                        if (!task.isSuccessful()) {
//                            Log.e("firebase", "Error getting data", task.getException());
//                        }
//                        else {
//                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                            JSONObject jsonObject = null;
//                            ArrayList<String> truckTypeList = new ArrayList(), regdNoList = new ArrayList();
//                            try {
//                                jsonObject = new JSONObject(String.valueOf(task.getResult().getValue()).trim());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            Iterator<String> keys = jsonObject.keys();
//
//                            while(keys.hasNext()) {
//                                String key = keys.next();
//                                try {
//                                    if (jsonObject.get(key) instanceof JSONObject) {
//                                        // do something with jsonObject here
//                                        Log.e("info", String.valueOf( jsonObject.get(key)));
//                                        Log.d("info",key);
//                                        Log.d("info", String.valueOf(jsonObject));
//                                    }
//                                }
//                                catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    });
//                    referenceTruck.child(TruckNo.toString()).child("regdNo").setValue(truckNo.getText().toString());
//                    referenceTruck.child(TruckNo.toString()).child("Trucktype").setValue(truckType.getText().toString())
                String truckNoTemp = truckNo.getText().toString();
                TruckDetails newTruckDetails = new TruckDetails(ownerId.toString(), truckTypeField, truckNo.getText().toString());
                referenceTruck.child(TruckNo.toString()).removeValue();
                referenceTruck.child(truckNo.getText().toString()).setValue(newTruckDetails);
            }
        });
        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        truckTypeField = (String) adapterView.getItemAtPosition(i);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}