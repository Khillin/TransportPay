package com.transport.khata;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.transport.khata.adapter.BaseAdapterTrips;
import com.transport.khata.model.CreateTripHelperClass;

import java.util.ArrayList;


public class ViewJobFragment extends Fragment {
    //ownerId captured
    String ownerid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String ownerId = FirebaseDatabase.getInstance().getReference().child("owner").child(ownerid).getKey().toString();

    DatabaseReference tripListRef,partyNameRef,originRef,destinationRef,startDateRef,tripRef;

//        String partyNameList[] = {"Sharma Tech","LucinTech","DelhiVery","FreshtoHome","Larsen"};
//        String tripStatusList[] = {"LR Recieved","POD Submitted","Settled","LR Recieved","LR Recieved"};
//        String originList[] = {"Mumbai","Delhi","Bengaluru","Delhi","Mathura"};
//        String destinationList[] = {"Hyderabad","Gandhinagar","Mumbai","Ramgarh","Jodhpur"};
//        String startDateList[] = {"12-04-2022","15-04-2022","24-03-2022","26-03-2022","27-03-2022"};

        ListView tripListView;
        ArrayList<String> tripIdList = new ArrayList<>();

        ArrayList <CreateTripHelperClass> tripDataList= new ArrayList();

        ArrayList<String> partyNameList = new ArrayList<>();
//        ArrayList<String> tripStatusList = new ArrayList<>();
        ArrayList<String> originList = new ArrayList<>();
        ArrayList<String> destinationList = new ArrayList<>();
        ArrayList<String> startDateList = new ArrayList<>();
        ArrayList<String> tripStatusList = new ArrayList<>();
    public ViewJobFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_job, container, false);
        tripListView = (ListView) view.findViewById(R.id.List_trip_info);



        //Database fetch

        tripListRef = FirebaseDatabase.getInstance().getReference().child("owner").child(ownerId).child("Trips");
        tripListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    String tripId=data.getKey().toString();
//                        tripDataList.add((CreateTripHelperClass) data.child(tripId).getValue());
                    tripIdList.add(tripId);
                }
                if(tripIdList.size() > 0){
                    tripRef = FirebaseDatabase.getInstance().getReference().child("Trips");
                    for (String tripId : tripIdList) {
                        tripRef.child(tripId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String partyName = snapshot.child("partyName").getValue().toString();
                                String origin = snapshot.child("originAddress").getValue().toString();
                                String destination = snapshot.child("destinationAddress").getValue().toString();
                                String startDate = snapshot.child("startDate").getValue().toString();
                                String tripStatus = snapshot.child("tripStatus").getValue().toString();
                                partyNameList.add(partyName);
                                originList.add(origin);
                                destinationList.add(destination);
                                startDateList.add(startDate);
                                tripStatusList.add(tripStatus);
                                BaseAdapterTrips baseAdapterTrips = new BaseAdapterTrips(getActivity().getApplicationContext(),tripStatusList,partyNameList,originList,destinationList,startDateList);
                                tripListView.setAdapter(baseAdapterTrips);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
//
        tripListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ViewJobActivity.class);
                intent.putExtra("tripId",tripIdList.get(i).toString());
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;

    }
}