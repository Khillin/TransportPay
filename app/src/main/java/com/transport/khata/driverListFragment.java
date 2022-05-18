package com.transport.khata;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.transport.khata.model.driverListAdapter;
import com.transport.khata.model.driverListClass;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link driverListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class driverListFragment extends Fragment {
    private static final int PICK_CONTACT = 4098;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 2099;
    ArrayList<driverListClass> driverDetails = new ArrayList();
    ListView listview;
    ExtendedFloatingActionButton addNewDriver;
    BottomSheetDialog dialogBuilderDriver;
    SearchView searchView;


    FirebaseDatabase rootNode;
    DatabaseReference referenceOwner;
    String ownerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Context context;
    Button searchContacts;
    TextInputEditText driverName;
    TextInputEditText driverPhoneNo;
    ImageButton back;
    driverListAdapter adapter;
    private static final int CONTACT_PERMISSION_CODE = 100;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public driverListFragment() {
        // Required empty public constructor
    }
    public static driverListFragment newInstance(String param1, String param2) {
        driverListFragment fragment = new driverListFragment();
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
        View view =  inflater.inflate(R.layout.fragment_driver_list, container, false);
        View row_view = getLayoutInflater().inflate(R.layout.row_item, null, false);
        context = getActivity();
        searchView = view.findViewById(R.id.search_view);



        addNewDriver = view.findViewById(R.id.addNewDriver);
//        Spinner driverOptions = (Spinner) row_view.findViewById(R.id.more_options);

        addNewDriver.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        createNewDriverDialogue();
                    }
                });

        back = view.findViewById(R.id.back_button1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });



        rootNode = FirebaseDatabase.getInstance();
        referenceOwner = rootNode.getReference("owner");

        listview = view.findViewById(R.id.driverList);




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


    public void setData(){
        adapter = new driverListAdapter(context, driverDetails);
        listview.setAdapter(adapter);
    }

    public void createNewDriverDialogue(){
        dialogBuilderDriver = new BottomSheetDialog(getContext());
        final View DriverPopupView = getLayoutInflater().inflate(R.layout.add_new_driver,null);


        rootNode = FirebaseDatabase.getInstance();
        referenceOwner = rootNode.getReference("owner");

        driverName = DriverPopupView.findViewById(R.id.driver_name_input);
        driverPhoneNo = DriverPopupView.findViewById(R.id.driver_number_input);

        Button submit = (Button) DriverPopupView.findViewById(R.id.submitNewDriver);
        searchContacts = (Button) DriverPopupView.findViewById(R.id.addFromContacts);
        searchContacts.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {
                        // Requesting the permission
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS},CONTACT_PERMISSION_CODE);
                    } else {
                            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);


                            startActivityForResult(intent, PICK_CONTACT);
                        }
                    }
                });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean error = false;
                if (driverPhoneNo.getText().length() == 0 || !driverPhoneNo.getText().toString().matches("[a-zA-Z0-9.? ]*")) {
                    driverPhoneNo.setError("Field cannot be empty");
                    error = true;
                }
                if(driverName.getText().length() == 0 || !driverName.getText().toString().matches("[a-zA-Z0-9.? ]*")){
                    driverName.setError("Field cannot be empty");
                    error = true;
                }
                if(error){
                    return;
                }
                referenceOwner = rootNode.getReference("owner");
                driverListClass driver = new driverListClass(0L, 0L, driverPhoneNo.getText().toString(),  driverName.getText().toString());

                referenceOwner.child(ownerId).child("drivers").child(driverPhoneNo.getText().toString()).setValue(driver).addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error pushing data", task.getException());
                    } else {
                        dialogBuilderDriver.dismiss();
                    }
                });

            }
        });
        dialogBuilderDriver.setContentView(DriverPopupView);
        dialogBuilderDriver.show();
    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();

                    Cursor cur =  getActivity().getContentResolver().query(contactData, null, null, null, null);
                    if (cur.getCount() > 0) {// thats mean some resutl has been found
                        if(cur.moveToNext()) {
                            @SuppressLint("Range") String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                            @SuppressLint("Range") String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            Log.e("Names", name);
                            driverName.setText(name);

                            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                            {

                                Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                                while (phones.moveToNext()) {
                                    @SuppressLint("Range") String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    Log.e("Number", phoneNumber);
                                    driverPhoneNo.setText(phoneNumber);
                                }
                                phones.close();
                            }

                        }
                    }
                    cur.close();
                }
                break;
        }
    }

    // This function is called when user accept or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when user is prompt for permission.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CONTACT_PERMISSION_CODE) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(getActivity(), "Contacts Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(), "Contacts Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}