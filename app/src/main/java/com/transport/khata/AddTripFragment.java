package com.transport.khata;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;
import com.transport.khata.model.BrokerHelperClass;
import com.transport.khata.model.Constants;
import com.transport.khata.model.CreateTripHelperClass;
import com.transport.khata.model.DriverHelperClass;
import com.transport.khata.model.JobHelperClass;

import java.io.Console;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AddTripFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    int SELECT_IMAGE_CODE = 1;
    Uri imageUri;
    String tripStatus = Constants.status_jrny_start;


    ArrayList <String> truckList = new ArrayList<>();
    ArrayList <String> driverNameList = new ArrayList<>();
    ArrayList <String> driverPhoneList = new ArrayList<>();
    ArrayList <Integer> numbering = new ArrayList<>();
    Integer i=0;
//    String truckList[] = {"UP85BP0987","UP85nb9867"};
//    String driverNameList[] = {"pintoo","Ramesh"};
//    String driverPhoneList[] = {"8978903873","8797979797"};


    CardView cameraCard;
    EditText partyName, driverName, originAddress, destinationAddress, driverPhone, billAmount, billType;
    TextView startDate,noOfTruck;
    DatePickerDialog.OnDateSetListener setListener;
    Button createTrip, galleryButton;
    Spinner spinner;
    ImageButton addMoreTruck;
    String ownerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    LinearLayout truckInfoLayout, cameraDetail,cameraLayout ;
    CardView card;
    ImageView cameraImage;

    ListView addlistView;

    private AlertDialog.Builder dialogBuilderDriver;
    private AlertDialog dialog;

    FirebaseDatabase rootNode;
    DatabaseReference spinnerRef;
    ArrayList<String> spinnerList;
    ArrayAdapter<String> adapter;

    public AddTripFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);
        createTrip = view.findViewById(R.id.create_trip);

        addMoreTruck = (ImageButton) view.findViewById(R.id.add_more_truck);
        addlistView = (ListView) view.findViewById(R.id.List_truck_info);

        partyName = view.findViewById(R.id.party_name);
        driverName = view.findViewById(R.id.driver_name);
        driverPhone = view.findViewById(R.id.driver_mob_no);
        startDate = view.findViewById(R.id.start_date);
        originAddress = view.findViewById(R.id.origin);
        destinationAddress = view.findViewById(R.id.destination);
        billAmount = view.findViewById(R.id.bill_amount);
        billType = view.findViewById(R.id.bill_type);
        LinearLayout truckInfolayout = (LinearLayout) view.findViewById(R.id.truck_info_layout);
        truckInfoLayout = (LinearLayout) view.findViewById(R.id.truck_info_layout);
        noOfTruck = view.findViewById(R.id.no_of_truck);

        cameraCard = (CardView) view.findViewById(R.id.camera_card);
        cameraDetail = (LinearLayout) view. findViewById(R.id.camera_detail) ;
        cameraLayout = (LinearLayout) view. findViewById(R.id.camera_layout) ;

        //camera initialization
        cameraImage = (ImageView) view.findViewById(R.id.camera_image);
        galleryButton = (Button) view.findViewById(R.id.gallery_button);

        //Request for camera permission
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA
            },100);
        }

        cameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });

        //gallery option
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),SELECT_IMAGE_CODE);

            }
        });



        //load data in truck info spinner
        spinnerRef= FirebaseDatabase.getInstance().getReference().child("owner").child(ownerId).child("trucks");
        ArrayList<String> truckRegdNo = new ArrayList<>();
        truckRegdNo.add("Select Truck");
        spinnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    String name=data.child("regdNo").getValue().toString();
                    truckRegdNo.add(name);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinner = view.findViewById(R.id.truck_reg_no);
        adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_dropdown_item,truckRegdNo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //on click Of createTrip Button
        createTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick();
            }
        });

        //start date selection via Calender
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String date = dayOfMonth+"/"+(month+1)+"/"+year;
                startDate.setText(date);
            }
        };


        addMoreTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                Integer j = i+1;
                noOfTruck.setText(j.toString());
                numbering.add(i);
                truckList.add(spinner.getSelectedItem().toString());
                driverNameList.add(driverName.getText().toString());
                driverPhoneList.add(driverPhone.getText().toString());

                CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getActivity().getApplicationContext(),truckList,driverNameList,driverPhoneList,numbering);
                addlistView.setAdapter(customBaseAdapter);
                setListViewHeightBasedOnChildren(addlistView);
                addlistView.setVisibility(View.VISIBLE);
                driverName.getText().clear();
                driverPhone.getText().clear();
            }
        });

        cameraCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int v = (cameraDetail.getVisibility() == View.GONE)? View.VISIBLE:View.GONE;

                TransitionManager.beginDelayedTransition(cameraLayout, new AutoTransition());
                cameraDetail.setVisibility(v);

            }
        });

        return view;
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


    private void onButtonClick() {

//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        Date myDate = null;
//        try {
//            myDate = df.parse(startDate.getText().toString());
//            String myText = myDate.getDate() + "-" + (myDate.getMonth() + 1) + "-" + (1900 + myDate.getYear());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        truckList.add(spinner.getSelectedItem().toString());
        driverNameList.add(driverName.getText().toString());
        driverPhoneList.add(driverPhone.getText().toString());

        String tripId = generateUniqueTripId();

        final String[] selectedTruck = new String[1];
        selectedTruck[0] = spinner.getSelectedItem().toString();

        try{
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Trips");
//        CreateTripHelperClass createTripHelperClass = new CreateTripHelperClass(partyName.getText().toString(),driverName.getText().toString(),selectedTruck[0],originAddress.getText().toString(),destinationAddress.getText().toString(),billAmount.getText().toString(),billType.getText().toString(),driverPhone.getText().toString(), startDate.getText().toString(),tripId);
            CreateTripHelperClass createTripHelperClass = new CreateTripHelperClass(partyName.getText().toString(),originAddress.getText().toString(),destinationAddress.getText().toString(),billAmount.getText().toString(),billType.getText().toString(),tripId,truckList,driverNameList,driverPhoneList,startDate.getText().toString(),tripStatus);
            databaseReference.child(tripId).setValue(createTripHelperClass);

            //storing tripId in Owners
            DatabaseReference ownerRef = FirebaseDatabase.getInstance().getReference().child("owner").child(ownerId).child("Trips");
            ownerRef.child(tripId).setValue(createTripHelperClass.getTripId());

            Toast.makeText(getActivity().getApplicationContext(), "Trip created successfully", Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            Intent intent = new Intent(getActivity().getApplicationContext(), Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }


    }

    private String generateUniqueTripId() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String timeStamp = simpleDateFormat.format(new Date());
         return ownerId+"_"+timeStamp;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100){
            //get capture image
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            //set capture image to image view
            cameraImage.setImageBitmap(captureImage);
        } else if (requestCode == 1){
            try{
                imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),imageUri);
                cameraImage.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}