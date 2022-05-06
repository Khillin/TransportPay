package com.transport.khata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.transport.khata.model.TruckDetails;
import com.transport.khata.model.driverListAdapter;
import com.transport.khata.model.driverListClass;
import com.transport.khata.model.truckDetailsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class driverListActivity extends AppCompatActivity {

    ArrayList<driverListClass> driverDetails = new ArrayList();
    ListView listview;
    ExtendedFloatingActionButton addNewDriver;
    BottomSheetDialog dialogBuilderDriver;


    FirebaseDatabase rootNode;
    DatabaseReference referenceOwner;
    String ownerId = FirebaseAuth.getInstance().getCurrentUser().getUid();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_list);
//        View row_view = getLayoutInflater().inflate(R.layout.row_item, null, false);
//
//
//        addNewDriver = findViewById(R.id.addNewDriver);
//        Spinner driverOptions = (Spinner) row_view.findViewById(R.id.more_options);
//
//        addNewDriver.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        createNewDriverDialogue();
//                    }
//                });
//
//
//
//        rootNode = FirebaseDatabase.getInstance();
//        referenceOwner = rootNode.getReference("owner");
//
//        listview = findViewById(R.id.driverList);
//
//        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.moreOptions, android.R.layout.simple_spinner_item);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//
//        driverOptions.setAdapter(adapter);
//
//
//
//
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
////                driverListClass drivers = dataSnapshot.child("ownerid1").child("drivers").getValue(driverListClass.class);
//                driverDetails.clear();
//                listview.setAdapter(null);
//                for (DataSnapshot snapshot : dataSnapshot.child(ownerid).child("drivers").getChildren()) {
//                    driverListClass driver = snapshot.getValue(driverListClass.class);
//                    driverDetails.add(driver);
//                }
//                setData();
//                // ..
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
//            }
//        };
//        referenceOwner.addValueEventListener(postListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.driverList_layout, new driverListFragment());
        fragmentTransaction.commit();
    }
//
//    public void setData(){
//        driverListAdapter adapter = new driverListAdapter(this, driverDetails);
//        listview.setAdapter(adapter);
//    }
//
//    public void createNewDriverDialogue(){
//        dialogBuilderDriver = new BottomSheetDialog(this);
//        final View DriverPopupView = getLayoutInflater().inflate(R.layout.add_new_driver,null);
//
//
//        rootNode = FirebaseDatabase.getInstance();
//        referenceOwner = rootNode.getReference("owner");
//
//        TextInputEditText driverName = DriverPopupView.findViewById(R.id.driver_name_input);
//        TextInputEditText driverPhoneNo = DriverPopupView.findViewById(R.id.driver_number_input);
//
//        Button submit = (Button) DriverPopupView.findViewById(R.id.submitNewDriver);
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                referenceOwner = rootNode.getReference("owner");
//                driverListClass driver = new driverListClass(0L, 0L, driverPhoneNo.getText().toString(),  driverName.getText().toString());
//                referenceOwner.child(ownerid).child("drivers").child(driverPhoneNo.getText().toString()).setValue(driver).addOnCompleteListener(task -> {
//                    if (!task.isSuccessful()) {
//                        Log.e("firebase", "Error pushing data", task.getException());
//                    } else {
//                        dialogBuilderDriver.dismiss();
//                    }
//                });
//
//            }
//        });
//        dialogBuilderDriver.setContentView(DriverPopupView);
//        dialogBuilderDriver.show();
//    }
}