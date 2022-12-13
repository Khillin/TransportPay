package com.transport.khata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.transport.khata.adapter.BaseAdapterInterface;
import com.transport.khata.adapter.DriverTripInfoAdapter;
import com.transport.khata.model.CreateTripHelperClass;
import com.transport.khata.model.driverListAdapter;
import com.transport.khata.model.driverListClass;
import com.transport.khata.model.tripDetails;
import com.transport.khata.model.tripDetailsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ViewJobActivity extends AppCompatActivity {


    String ownerId = FirebaseAuth.getInstance().getCurrentUser().getUid();

//    String ownerId = FirebaseDatabase.getInstance().getReference().child("owner").child(ownerid).getKey().toString();

    ImageButton Back;
    TextView editBtn, saveBtn, advAmt, advAmtEdit,
    editBtn2, saveBtn2, billAmt, billAmtEdit;

    DatabaseReference tripRef;
    StorageReference storageReference;
    String tripId = null;

    ArrayList<String> truckList = new ArrayList<>();
    ArrayList <String> driverNameList = new ArrayList<>();
    ArrayList <String> driverPhoneList = new ArrayList<>();
    ListView listview;
    ImageView viewLR,viewPOD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);
        tripId = getIntent().getStringExtra("tripId");
        tripRef = FirebaseDatabase.getInstance().getReference("Trips").child(tripId);
        storageReference = FirebaseStorage.getInstance().getReference();

        advAmt = findViewById(R.id.advance_amount);
        advAmtEdit = findViewById(R.id.advance_amount_edit);
        editBtn = findViewById(R.id.edit_btn);
        saveBtn = findViewById(R.id.save_btn);
        billAmt = findViewById(R.id.bill_amount);
        billAmtEdit = findViewById(R.id.bill_amount_edit);
        editBtn2 = findViewById(R.id.ba_edit_btn);
        saveBtn2 = findViewById(R.id.ba_save_btn);
        viewLR = findViewById(R.id.view_LR);
        viewPOD = findViewById(R.id.view_POD);

        listview = findViewById(R.id.list_driver_info);


//        on Back button click
        Back = (ImageButton) findViewById(R.id.back_button);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // ************** on edit button click ***********************
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advAmtEdit.setText(advAmt.getText().toString());
                advAmtEdit.setVisibility(View.VISIBLE);
                advAmt.setVisibility(View.GONE);
                editBtn.setVisibility(View.GONE);
                saveBtn.setVisibility(View.VISIBLE);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advAmt.setText(advAmtEdit.getText().toString());
                advAmt.setVisibility(View.VISIBLE);
                advAmtEdit.setVisibility(View.GONE);
                editBtn.setVisibility(View.VISIBLE);
                saveBtn.setVisibility(View.GONE);
                tripRef.child("advAmount").setValue(advAmt.getText().toString());
            }
        });

        editBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billAmtEdit.setText(billAmt.getText().toString());
                billAmtEdit.setVisibility(View.VISIBLE);
                billAmt.setVisibility(View.GONE);
                editBtn2.setVisibility(View.GONE);
                saveBtn2.setVisibility(View.VISIBLE);
            }
        });

        saveBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billAmt.setText(billAmtEdit.getText().toString());
                billAmt.setVisibility(View.VISIBLE);
                billAmtEdit.setVisibility(View.GONE);
                editBtn2.setVisibility(View.VISIBLE);
                saveBtn2.setVisibility(View.GONE);
                tripRef.child("billAmount").setValue(advAmt.getText().toString());
            }
        });

        uploadDocIfExist();
        //**********************//
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
//                driverListClass drivers = dataSnapshot.child("ownerid1").child("drivers").getValue(driverListClass.class);
                listview.setAdapter(null);
                for (DataSnapshot snapshot : dataSnapshot.child("driverNameList").getChildren()) {
                    driverNameList.add(snapshot.getValue().toString());
                }
                for (DataSnapshot snapshot : dataSnapshot.child("driverPhoneList").getChildren()) {
                    driverPhoneList.add(snapshot.getValue().toString());
                }
                for (DataSnapshot snapshot : dataSnapshot.child("truckList").getChildren()) {
                    truckList.add(snapshot.getValue().toString());
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

        tripRef.addValueEventListener(postListener);


    }

    private void uploadDocIfExist() {
        String fileName = "LR" +"_"+ownerId+"_"+tripId;
        StorageReference image = storageReference.child("images/"+ownerId +"/"+fileName +".jpg");
        image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(viewLR);
                viewLR.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "LR not Found, please upload first to view", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setData(){
        DriverTripInfoAdapter adapter = new DriverTripInfoAdapter(getApplicationContext(), truckList,driverNameList,driverPhoneList);
        listview.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listview);
    }

    public static void setListViewHeightBasedOnChildren
            (ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}