package com.transport.khata;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.transport.khata.adapter.BaseAdapterInterface;
import com.transport.khata.adapter.BaseAdapterTrips;
import com.transport.khata.model.Constants;
import com.transport.khata.model.CreateTripHelperClass;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;


public class ViewJobFragment extends Fragment implements BaseAdapterInterface {
    //ownerId captured
    int SELECT_IMAGE_CODE = 1;
    Boolean status=false;
    public final String APP_TAG = "TransportKhata";
    String ownerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//    String ownerId = FirebaseDatabase.getInstance().getReference().child("owner").child(ownerid).getKey().toString();
    SearchView searchView;
    BaseAdapterTrips baseAdapterTrips;
    File photoFile;
    long timeStamp = 0;
    StorageReference storageReference;
    View referenceView;
    String referenceTripId;
    String referenceTripStatus;
    Boolean updateFlag =false;

    DatabaseReference tripListRef,tripRef;

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

    public ViewJobFragment ViewJobFragment() {
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_view_job, container, false);
        tripListView = (ListView) view.findViewById(R.id.List_trip_info);
        searchView = view.findViewById(R.id.search_view);
        storageReference = FirebaseStorage.getInstance().getReference();



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
                                int position=0;
                                String partyName = snapshot.child("partyName").getValue().toString();
                                String origin = snapshot.child("originAddress").getValue().toString();
                                String destination = snapshot.child("destinationAddress").getValue().toString();
                                String startDate = snapshot.child("startDate").getValue().toString();
                                String tripStatus = snapshot.child("tripStatus").getValue().toString();
                                if(updateFlag){
                                    position= tripIdList.indexOf(snapshot.getKey());
                                    tripStatusList.set(position,tripStatus);
                                    updateFlag=false;
                                } else{
                                    partyNameList.add(partyName);
                                    originList.add(origin);
                                    destinationList.add(destination);
                                    startDateList.add(startDate);
                                    tripStatusList.add(tripStatus);
                                }
                                baseAdapterTrips = new BaseAdapterTrips(getActivity(),tripIdList,tripStatusList,partyNameList,originList,destinationList,startDateList, ViewJobFragment());
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                baseAdapterTrips.getFilter().filter(newText);
                return false;
            }
        });

        //Request for camera permission
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA
            },100);
        }


        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1){
            try{
                Uri imageUri = data.getData();
                String  fileName = photoFile.getName();
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),imageUri);
//                Bitmap selectedImage = loadFromUri(imageUri);
                uploadImageToFirebase(fileName,imageUri);
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private void uploadImageToFirebase(String fileName, Uri contentUri) {
        Button upload = referenceView.findViewById(R.id.status_update);
        upload.setText("Uploading...");
        StorageReference image = storageReference.child("images/"+ownerId +"/"+fileName);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                if(referenceTripStatus.equalsIgnoreCase(Constants.status_jrny_start)){
                    tripRef.child(referenceTripId).child("tripStatus").setValue(Constants.status_lr_recvd).addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error pushing data", task.getException());
                        } else {
                            Toast.makeText(getActivity(),"Trip Status updated Successfully",Toast.LENGTH_SHORT).show();
                        }

                    });
                } else if (referenceTripStatus.equalsIgnoreCase(Constants.status_lr_recvd)){
                    tripRef.child(referenceTripId).child("tripStatus").setValue(Constants.status_pod_recvd).addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error pushing data", task.getException());
                        } else {
                            Toast.makeText(getActivity(),"Trip Status updated Successfully",Toast.LENGTH_SHORT).show();
                        }

                    });
                }

                updateFlag=true;
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("TAG", "onSuccess: Uploaded Image URI is "+ uri.toString());
                    }
                });
                Toast.makeText(getActivity(),"Image id Uploaded Successfully",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Upload Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public File getPhotoFileUri(String tripStatus,String tripId) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory");
        }

        String fileName = "";
        timeStamp = System.nanoTime();
        if(tripStatus.equalsIgnoreCase(Constants.status_jrny_start)){
            fileName = "LR" + "_" + ownerId+"_"+tripId+".jpg";
        } else if (tripStatus.equalsIgnoreCase(Constants.status_lr_recvd)){
            fileName = "POD" + "_" + ownerId+"_"+tripId+".jpg";
        }


        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void onClickBtn(View view,String tripId,String tripStatus,Boolean completeBtnFlag) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        referenceView = view;
        referenceTripId = tripId;
        referenceTripStatus = tripStatus;

        if(!completeBtnFlag){
            if(tripStatus.equalsIgnoreCase(Constants.status_jrny_start) || tripStatus.equalsIgnoreCase(Constants.status_lr_recvd)){
                photoFile = getPhotoFileUri(tripStatus,tripId);
//                Intent intent = new Intent(context, ViewJobFragment.class);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),SELECT_IMAGE_CODE);
            } else if (tripStatus.equalsIgnoreCase(Constants.status_pod_recvd)){
                updateFlag=true;
                tripRef.child(referenceTripId).child("tripStatus").setValue(Constants.status_settlement).addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error pushing data", task.getException());
                    } else {
                        Toast.makeText(getActivity(),"Trip Status updated Successfully",Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } else {
            updateFlag=true;
            tripRef.child(referenceTripId).child("tripStatus").setValue(Constants.status_settlement).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error pushing data", task.getException());
                } else {
                    Toast.makeText(getActivity(),"Trip marked as complete",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}