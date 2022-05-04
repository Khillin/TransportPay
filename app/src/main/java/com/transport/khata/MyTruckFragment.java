package com.transport.khata;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.transport.khata.model.DriverHelperClass;
import com.transport.khata.model.JobHelperClass;
import com.transport.khata.model.TruckDetails;
import com.transport.khata.model.truckDetailsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyTruckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTruckFragment extends Fragment {

    FirebaseDatabase rootNode;
    DatabaseReference referenceJob, referenceOwner, referenceTruck;
    TextView truck;
    EditText regdNo;
    LinearLayout layout;
    ListView listview;
    BottomSheetDialog dialogBuilderTruck;
    Button submit;
    String truckTypeFin;
    String ownerId = "ownerid1";



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyTruckFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyTruckFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyTruckFragment newInstance(String param1, String param2) {
        MyTruckFragment fragment = new MyTruckFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_truck, container, false);
        View rowView = inflater.inflate(R.layout.row_item, null, false);

        truck = rowView.findViewById(R.id.textView);
        regdNo = rowView.findViewById(R.id.regdNo);
        layout = rowView.findViewById(R.id.LinearLayout);
        listview = view.findViewById(R.id.listview);
        rootNode = FirebaseDatabase.getInstance();
        referenceJob = rootNode.getReference("Truck");
        ArrayList<View> viewsArray = new ArrayList();
        ArrayList<TruckDetails> truckDArray = new ArrayList();
        Button OpenBottomSheet = view.findViewById(R.id.open_bottom_sheet);

        OpenBottomSheet.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        createNewTruckDialogue();
                    }
                });


        referenceJob.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                truck.setText(String.valueOf(task.getResult().getValue()));
                JSONObject jsonObject = null;
                ArrayList<String> truckTypeList = new ArrayList(), regdNoList = new ArrayList();
                try {
                    jsonObject = new JSONObject(String.valueOf(task.getResult().getValue()).trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Iterator<String> keys = jsonObject.keys();

                while(keys.hasNext()) {
                    String key = keys.next();
                    try {
                        if (jsonObject.get(key) instanceof JSONObject) {
                            String owner = ((JSONObject) jsonObject.get(key)).getString("owner");
                            String Trucktype = ((JSONObject) jsonObject.get(key)).getString("Trucktype");
                            String regdNoStr = ((JSONObject) jsonObject.get(key)).getString("regdNo");
                            TruckDetails truckDetails  = new TruckDetails(owner, Trucktype, regdNoStr);
                            truckDetails.setOwner(((JSONObject) jsonObject.get(key)).getString("owner"));
                            truckDetails.setTruckType(((JSONObject) jsonObject.get(key)).getString("Trucktype"));
                            truckDetails.setRegdNo(((JSONObject) jsonObject.get(key)).getString("regdNo"));
                            truckTypeList.add(truckDetails.getTruckType());
                            regdNoList.add(truckDetails.getRegdNo());
                            truck.setText("Trucktype");
                            regdNo.setText("regdNoStr");
                            truckDArray.add(truckDetails);
                            viewsArray.add(rowView);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                truckDetailsAdapter adapter = new truckDetailsAdapter(getActivity(), truckDArray);
                listview.setAdapter(adapter);
            }
        });
        return view;
    }

    public void createNewTruckDialogue(){
        dialogBuilderTruck = new BottomSheetDialog(getActivity());
        final View TruckPopupView = getLayoutInflater().inflate(R.layout.add_new_truck,null);

        referenceTruck = rootNode.getReference("Truck");

        EditText truckNo = TruckPopupView.findViewById(R.id.truck_no);
        MaterialButton truckType1 = TruckPopupView.findViewById(R.id.truckType1);
        MaterialButton truckType2 = TruckPopupView.findViewById(R.id.truckType2);
        MaterialButton truckType3 = TruckPopupView.findViewById(R.id.truckType3);

        truckType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                truckTypeFin = "truckType1";
                truckType1.setBackgroundColor(Color.parseColor("#717F8C"));
                truckType1.setTextColor(Color.parseColor("#FFFFFF"));
                truckType2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                truckType3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                truckType2.setTextColor(Color.parseColor("#212121"));
                truckType3.setTextColor(Color.parseColor("#212121"));
            }});

        truckType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                truckTypeFin = "truckType2";
                truckType2.setBackgroundColor(Color.parseColor("#717F8C"));
                truckType2.setTextColor(Color.parseColor("#FFFFFF"));
                truckType1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                truckType3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                truckType1.setTextColor(Color.parseColor("#212121"));
                truckType3.setTextColor(Color.parseColor("#212121"));

            }});

        truckType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                truckTypeFin = "truckType3";
                truckType3.setBackgroundColor(Color.parseColor("#717F8C"));
                truckType3.setTextColor(Color.parseColor("#FFFFFF"));
                truckType2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                truckType1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                truckType1.setTextColor(Color.parseColor("#212121"));
                truckType2.setTextColor(Color.parseColor("#212121"));
            }});

        submit = (Button) TruckPopupView.findViewById(R.id.submitAddnewTruck);

        dialogBuilderTruck.setContentView(TruckPopupView);
        dialogBuilderTruck.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                referenceOwner = rootNode.getReference("owner");
                referenceOwner.child("ownerid1").child("trucks").get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                truck.setText(String.valueOf(task.getResult().getValue()));
                        JSONObject jsonObj = null;
                        String truckNumber = truckNo.getText().toString();

                        try {
                            jsonObj = new JSONObject(String.valueOf(task.getResult().getValue()).trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Iterator<String> keys = jsonObj.keys();
                        TruckDetails truckDetailsClass = new TruckDetails(ownerId, truckTypeFin, truckNumber);
                        referenceTruck.child(truckNumber).setValue(truckDetailsClass);
                        dialogBuilderTruck.dismiss();
                    }
                });
            }
        });

    }
}
