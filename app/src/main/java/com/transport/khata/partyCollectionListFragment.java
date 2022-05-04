package com.transport.khata;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.transport.khata.model.tripDetails;
import com.transport.khata.model.tripDetailsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class partyCollectionListFragment extends Fragment {

    FirebaseDatabase rootNode;
    DatabaseReference referenceTrip;
//    String tripId = "ownerid1_09-04-2022-01-03-54";
    String tripId = "ownerid1_23-03-2022-03-00-54";
    ArrayList<tripDetails> tripDetails = new ArrayList();
    ListView partyCollectionCard;

    public partyCollectionListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_party_collection_list, container, false);
        rootNode = FirebaseDatabase.getInstance();
        partyCollectionCard = view.findViewById(R.id.trip_listView);



        referenceTrip = rootNode.getReference("Trips").child(tripId);
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
////                driverListClass drivers = dataSnapshot.child("ownerid1").child("drivers").getValue(driverListClass.class);
////                driverDetails.clear();
////                listview.setAdapter(null);
//                for (DataSnapshot snapshot : dataSnapshot.child(tripId).getChildren()) {
//                    Log.e("data", (String) snapshot.getValue(Boolean.parseBoolean("startDate")));
//                    tripDetails trip = snapshot.getValue(tripDetails.class);
//                    tripDetails.add(trip);
//                }
//                setData();
//                // ..
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.e("ERROR", "loadPost:onCancelled", databaseError.toException());
//            }
//        };
//        referenceTrip.addValueEventListener(postListener);


        referenceTrip.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                truck.setText(String.valueOf(task.getResult().getValue()));
                JSONObject jsonObj = null;

                try {
                    jsonObj = new JSONObject(String.valueOf(task.getResult().getValue()).trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Iterator<String> keys = jsonObj.keys();

                Log.d("firebase", String.valueOf(jsonObj));

                while(keys.hasNext()) {
                    String key = keys.next();
                    try {
                        if (jsonObj.get(key) instanceof JSONObject) {
                            int advance = (int) jsonObj.get("advance");
                            int billAmount = (int) jsonObj.get("billAmount");
                            String billType = jsonObj.getString("billType");
                            String driverName = jsonObj.getString("driverName");
                            Long driverPhone = (Long) jsonObj.get("driverPhone");
                            String originAddress = jsonObj.getString("originAddress");
                            String ownerid = jsonObj.getString("ownerid");
                            JSONObject startDate = (JSONObject) jsonObj.get("startDate");
                            String partyName = jsonObj.getString("partyName");
                            String status = jsonObj.getString("status");
                            String tripId = jsonObj.getString("tripId");
                            String truckNum = jsonObj.getString("truckNum");
                            String destinationAddress = jsonObj.getString("destinationAddress");

//                             Log.d("firebase", driverName);
//                             Log.d("firebase", String.valueOf(driverPhone));
//                             Log.d("firebase", originAddress);
//                             Log.d("firebase", ownerid);
//                             Log.d("firebase", String.valueOf(startDate));
//                             Log.d("firebase", partyName);
//                             Log.d("firebase", status);
//                             Log.d("firebase", tripId);
//                             Log.d("firebase", truckNum);
//                             Log.d("firebase", destinationAddress);
                            tripDetails trip = new tripDetails(advance, billAmount, billType, destinationAddress, driverName, driverPhone, originAddress, ownerid, partyName, startDate ,status, tripId, truckNum);
                            tripDetails.add(trip);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                tripDetailsAdapter adapter = new tripDetailsAdapter(getContext(), tripDetails);
                partyCollectionCard.setAdapter(adapter);

            }
        });


        return view;
    }
}