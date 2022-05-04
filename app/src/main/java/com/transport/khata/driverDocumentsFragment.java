package com.transport.khata;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.transport.khata.model.driverListClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link driverDocumentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class driverDocumentsFragment extends Fragment {

    private static final int CONTACT_PERMISSION_CODE = 5600;
    TextInputEditText driverNameEdit;
    TextInputEditText driverPhoneNoEdit;
    FirebaseDatabase rootNode;
    DatabaseReference referenceDriver;
    String ownerId = "ownerid1";
    String front_rc = "";
    String back_rc = "";
    Button saveDriver;
    Long timeStamp_front = 0L;
    Long timeStamp_back = 0L;
    String driverNo;
    Button searchContacts;
    ImageButton back;
    private static final int PICK_CONTACT = 4099;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public driverDocumentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment driverDocumentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static driverDocumentsFragment newInstance(String param1, String param2) {
        driverDocumentsFragment fragment = new driverDocumentsFragment();
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

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        insertNestedFragment();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_driver_documents, container, false);
        Bundle bundle = this.getArguments();
        String driverName = bundle.getString("driverName");
        driverNo = bundle.getString("driverNo");
        driverNameEdit = v.findViewById(R.id.driverNameEdit);
        driverPhoneNoEdit = v.findViewById(R.id.driverPhoneNoEdit);
        driverNameEdit.setText(driverName);
        driverPhoneNoEdit.setText(driverNo);
        saveDriver = v.findViewById(R.id.saveDriver);
        rootNode = FirebaseDatabase.getInstance();
        referenceDriver = rootNode.getReference("owner");
        referenceDriver.child(ownerId).child("drivers").child(driverNo).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(String.valueOf(Objects.requireNonNull(task.getResult()).getValue()).trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonObject != null) {
                    try {
                        if (!jsonObject.get("frontDLimgname").toString().equals("0")) {
                            front_rc = "MyCustomApp/" + "FrontRC_" + jsonObject.get("frontDLimgname").toString() + "_" + driverNo + ".jpg";
                            File file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), front_rc);
                            if (file.exists()) {
                                timeStamp_front = (Long) jsonObject.get("frontDLimgname");
                                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                ImageView myImage = (ImageView) v.findViewById(R.id.click_image);
                                myImage.setVisibility(View.VISIBLE);
                                v.findViewById(R.id.uploadFromDeviceFront).setVisibility(View.GONE);
                                v.findViewById(R.id.textView4).setVisibility(View.GONE);
                                v.findViewById(R.id.camera_button).setVisibility(View.GONE);
                                myImage.setImageBitmap(myBitmap);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.get("backDLimgname").toString().equals("0")) {
                            back_rc = "MyCustomApp/" + "BackRC_" + jsonObject.get("backDLimgname").toString() + "_" + driverNo + ".jpg";
                            File file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), back_rc);
                            if (file.exists()) {
                                timeStamp_back = (Long) jsonObject.get("backDLimgname");
                                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                ImageView myImage = (ImageView) v.findViewById(R.id.click_image_back);
                                myImage.setVisibility(View.VISIBLE);
                                v.findViewById(R.id.uploadFromDeviceBack).setVisibility(View.GONE);
                                v.findViewById(R.id.textView5).setVisibility(View.GONE);
                                v.findViewById(R.id.camera_button_back).setVisibility(View.GONE);
                                myImage.setImageBitmap(myBitmap);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        searchContacts = (Button) v.findViewById(R.id.searchContacts);
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

        saveDriver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditDocumentFragment docfragment = (EditDocumentFragment) getChildFragmentManager().findFragmentById(R.id.fooFragment);
                if(docfragment.getTimeStampFront() != 0){
                    timeStamp_front = docfragment.getTimeStampFront();
                };
                if(docfragment.getTimeStampBack() != 0){
                    timeStamp_back = docfragment.getTimeStampBack();
                };
                driverListClass driver = new driverListClass(timeStamp_front, timeStamp_back, driverPhoneNoEdit.getText().toString(), driverNameEdit.getText().toString());
                referenceDriver.child(driverNo.toString()).removeValue();
                referenceDriver.child(ownerId).child("drivers").child(driverPhoneNoEdit.getText().toString()).setValue(driver).addOnCompleteListener((task)->{
                    if(task.isSuccessful()){
                        getActivity().onBackPressed();
                    }
                });

            }
        });

        back = v.findViewById(R.id.back_button2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                driverListFragment editFragment = new driverListFragment();
//                Log.e("TESTING","testing");
//                Bundle args = new Bundle();
//                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.doc_driv, editFragment);
//                fragmentTransaction.commit();
                getActivity().onBackPressed();
            }
        });



        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                            driverNameEdit.setText(name);

                            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                            {

                                Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                                while (phones.moveToNext()) {
                                    @SuppressLint("Range") String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    Log.e("Number", phoneNumber);
                                    driverPhoneNoEdit.setText(phoneNumber);
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