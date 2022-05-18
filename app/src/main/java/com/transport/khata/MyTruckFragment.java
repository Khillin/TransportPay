package com.transport.khata;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.transport.khata.model.DriverHelperClass;
import com.transport.khata.model.JobHelperClass;
import com.transport.khata.model.TruckDetails;
import com.transport.khata.model.driverListAdapter;
import com.transport.khata.model.driverListClass;
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
    String ownerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    ArrayList<TruckDetails> truckDetails = new ArrayList();
    Context context;
    SearchView searchView;
    truckDetailsAdapter adapter;



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
        context = getActivity();
        searchView = view.findViewById(R.id.search_view);

        truck = rowView.findViewById(R.id.textView);
        regdNo = rowView.findViewById(R.id.regdNo);
        layout = rowView.findViewById(R.id.LinearLayout);
        listview = view.findViewById(R.id.listview);
        rootNode = FirebaseDatabase.getInstance();
        referenceOwner = rootNode.getReference("owner");
//        referenceJob = rootNode.getReference("Truck");
        referenceJob = rootNode.getReference("owner").child(ownerId).child("trucks");
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

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                truckDetails.clear();
                listview.setAdapter(null);
                for (DataSnapshot snapshot : dataSnapshot.child(ownerId).child("trucks").getChildren()) {
                    TruckDetails truck = snapshot.getValue(TruckDetails.class);
                    truckDetails.add(truck);
                }
                setTruckData();
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        referenceOwner.addValueEventListener(postListener);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }

    public void setTruckData(){
        adapter = new truckDetailsAdapter(context, truckDetails);
        listview.setAdapter(adapter);
    }

    public void createNewTruckDialogue(){
        dialogBuilderTruck = new BottomSheetDialog(getActivity());
        final View TruckPopupView = getLayoutInflater().inflate(R.layout.add_new_truck,null);

        referenceTruck = rootNode.getReference("owner").child(ownerId).child("trucks");

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
                referenceOwner.child(ownerId).child("trucks").get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        String truckNumber = truckNo.getText().toString();
                        TruckDetails truckDetailsClass = new TruckDetails(ownerId, truckTypeFin, truckNumber);
                        referenceTruck.child(truckNumber).setValue(truckDetailsClass);
                        dialogBuilderTruck.dismiss();
                    }
                });
            }
        });

    }
}
