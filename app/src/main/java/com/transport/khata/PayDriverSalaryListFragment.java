package com.transport.khata;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.transport.khata.model.driverListClass;
import com.transport.khata.model.payDriverSalaryAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayDriverSalaryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayDriverSalaryListFragment extends Fragment {

    ArrayList<driverListClass> driverDetails = new ArrayList();
    ListView listview;

    FirebaseDatabase rootNode;
    DatabaseReference referenceOwner;
    String ownerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Context context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PayDriverSalaryListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayDriverSalaryListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayDriverSalaryListFragment newInstance(String param1, String param2) {
        PayDriverSalaryListFragment fragment = new PayDriverSalaryListFragment();
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
        View view =  inflater.inflate(R.layout.fragment_pay_driver_salary_list, container, false);
        View row_view = getLayoutInflater().inflate(R.layout.row_item_pay_driver, null, false);
        context = getActivity();



        rootNode = FirebaseDatabase.getInstance();
        referenceOwner = rootNode.getReference("owner");

        listview = view.findViewById(R.id.driverList);

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.moreOptions, android.R.layout.simple_spinner_item);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//
//        driverOptions.setAdapter(adapter);




        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
//                driverListClass drivers = dataSnapshot.child("ownerid1").child("drivers").getValue(driverListClass.class);
                driverDetails.clear();
                listview.setAdapter(null);
                for (DataSnapshot snapshot : dataSnapshot.child(ownerId).child("drivers").getChildren()) {
                    driverListClass driver = snapshot.getValue(driverListClass.class);
                    driverDetails.add(driver);
                }
                setData();
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        };
        referenceOwner.addValueEventListener(postListener);

        return view;
    }


    public void setData(){
        payDriverSalaryAdapter adapter = new payDriverSalaryAdapter(context, driverDetails);
        listview.setAdapter(adapter);
    }
}