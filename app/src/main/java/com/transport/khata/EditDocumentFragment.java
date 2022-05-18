package com.transport.khata;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;


public class EditDocumentFragment extends Fragment {

    LinearLayout camera_open_id,progressbar;
    Button editTruckSubmit;
    Button changeNumberBtn;
    Button changeTypeBtn;
    ImageView click_image_id;
    Spinner truckType;
    EditText truckNo;
    private static final int pic_id = 123;
    LinearLayout camera_open_id_back;
    ImageView click_image_id_back;
    private static final int pic_id_back = 321;
    FirebaseDatabase rootNode;
    DatabaseReference referenceTruck, referenceOwner;
    StorageReference storageReference;
    Boolean valuesChanged = false;
    File photoFile;
    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_BACK = 1045;
    String ownerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public final static int PICK_PHOTO_CODE_FRONT = 1046;
    public final static int PICK_PHOTO_CODE_BACK = 1096;
    Button uploadFromDeviceFront;
    Button uploadFromDeviceBack;
    String documentFor;
    String key;
    String DriverNo;
    long timeStampFront = 0;
    long timeStampBack = 0;
    Boolean frontImagechanged = false;
    Boolean backImagechanged = false;


    public EditDocumentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_document, container, false);
        Bundle bundle = getParentFragment().getArguments();
        documentFor = bundle.getString("documentFor");
//        String TruckType = bundle.getString("truckType");
        key = bundle.getString("key");
//        DriverNo = bundle.getString("driverNo");
//        String ownerId = bundle.getString(ownerid);
//        String TruckType = "truck980";
//        String TruckNo = "MP-08-YB-7689";
//        String ownerId = "ownerId1";

//        truckType = (Spinner) view.findViewById(R.id.truckTypeDropdown);
        truckNo = (EditText) view.findViewById(R.id.truckNo);

//        truckType.setText(TruckType);
        storageReference = FirebaseStorage.getInstance().getReference();
        rootNode = FirebaseDatabase.getInstance();
        referenceTruck = rootNode.getReference("Truck");

        camera_open_id = (LinearLayout) view.findViewById(R.id.camera_button);
        progressbar = (LinearLayout) view.findViewById(R.id.progressbar);
        click_image_id = (ImageView) view.findViewById(R.id.click_image);
        camera_open_id_back = (LinearLayout) view.findViewById(R.id.camera_button_back);
        click_image_id_back = (ImageView) view.findViewById(R.id.click_image_back);
        editTruckSubmit = (Button) view.findViewById(R.id.submitEditTruck);
        uploadFromDeviceFront = (Button) view.findViewById(R.id.uploadFromDeviceFront);
        uploadFromDeviceBack = (Button) view.findViewById(R.id.uploadFromDeviceBack);

        changeNumberBtn = (Button) view.findViewById(R.id.makeEditableTType);
        //Request for camera permission
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA
            },100);
        }
//        changeTypeBtn = (Button) view.findViewById(R.id.changeTruckType);
        // Camera_open button is for open the camera
        // and add the setOnClickListener in this button
        camera_open_id.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent camera_intent
                        = new Intent(MediaStore
                        .ACTION_IMAGE_CAPTURE);

                photoFile = getPhotoFileUri("front", key.toString());

                Uri fileProvider = FileProvider.getUriForFile(requireActivity().getApplicationContext(),  BuildConfig.APPLICATION_ID + ".provider", photoFile);
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

//                if (camera_intent.resolveActivity(getActivity().getPackageManager()) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(camera_intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//                }
            }
        });

        camera_open_id_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                String pathName =  Environment.getExternalStorageDirectory().getPath() +"/FrontRC_" + new Date().toString() +"_"+ TruckNo.toString();

                Intent camera_intent
                        = new Intent(MediaStore
                        .ACTION_IMAGE_CAPTURE);

                photoFile = getPhotoFileUri("back", key.toString());

                Uri fileProvider = FileProvider.getUriForFile(requireActivity().getApplicationContext(),  BuildConfig.APPLICATION_ID + ".provider", photoFile);
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

//                if (camera_intent.resolveActivity(getActivity().getPackageManager()) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(camera_intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_BACK);
//                }


//                Uri uriSavedImage=Uri.fromFile(new File("/sdcard/flashCropped.png"));
//                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

                // Create the camera_intent ACTION_IMAGE_CAPTURE
                // it will open the camera for capture the image

                // Start the activity with camera_intent,
                // and request pic id
//                startActivityForResult(camera_intent, pic_id_back);
            }
        });

        ConstraintLayout accordianBtn1 = (ConstraintLayout) view.findViewById(R.id.accordion_btn1);
        accordianBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Clicked", "Btn1");
                ConstraintLayout findMagicLl = (ConstraintLayout) view.findViewById(R.id.accordian1);
                if (findMagicLl.getVisibility() == View.VISIBLE) {
                    findMagicLl.setVisibility(View.GONE);
                } else {
                    findMagicLl.setVisibility(View.VISIBLE);
                }
            }
        });

        ConstraintLayout accordianBtn2 = (ConstraintLayout) view.findViewById(R.id.accordion_btn2);
        accordianBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Clicked", "Btn2");
                ConstraintLayout findMagicLl = (ConstraintLayout) view.findViewById(R.id.accordian2);
                if (findMagicLl.getVisibility() == View.VISIBLE) {
                    findMagicLl.setVisibility(View.GONE);
                } else {
                    findMagicLl.setVisibility(View.VISIBLE);
                }
            }
        });

        uploadFromDeviceFront.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onPickPhoto(v, "front");
            }
        });

        uploadFromDeviceBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onPickPhoto(v, "back");
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {

        // Match the request 'pic id with requestCode
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//
//            // BitMap is data structure of image file
//            // which stor the image in memory
//            Bitmap photo = (Bitmap) data.getExtras()
//                    .get("data");
//
//            // Set the image in imageview for display
//            click_image_id.setImageBitmap(photo);
//        } else if (requestCode == pic_id_back) {
//
//            // BitMap is data structure of image file
//            // which stor the image in memory
//            Bitmap photo = (Bitmap) data.getExtras()
//                    .get("data");
//
//            // Set the image in imageview for display
//            click_image_id_back.setImageBitmap(photo);
//        }

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            // by this point we have the camera photo on disk
            Uri contentUri = FileProvider.getUriForFile(requireActivity().getApplicationContext(),  BuildConfig.APPLICATION_ID + ".provider", photoFile);
            String  fileName = photoFile.getName();
            Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            frontImagechanged = true;

            uploadImageToFirebase(fileName,contentUri);

            // RESIZE BITMAP, see section below
            // Load the taken image into a preview
            click_image_id.setImageBitmap(takenImage);
            click_image_id.setVisibility(View.VISIBLE);
            uploadFromDeviceFront.setVisibility(View.GONE);
            camera_open_id.setVisibility(View.GONE);
            getView().findViewById(R.id.textView4).setVisibility(View.GONE);

        } else  if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_BACK) {
            Uri contentUri = FileProvider.getUriForFile(requireActivity().getApplicationContext(),  BuildConfig.APPLICATION_ID + ".provider", photoFile);
            String  fileName = photoFile.getName();
            // by this point we have the camera photo on disk
            Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            backImagechanged = true;

            uploadImageToFirebase(fileName,contentUri);

            // RESIZE BITMAP, see section below
            // Load the taken image into a preview
            click_image_id_back.setImageBitmap(takenImage);
            click_image_id_back.setVisibility(View.VISIBLE);
            uploadFromDeviceBack.setVisibility(View.GONE);
            camera_open_id_back.setVisibility(View.GONE);
            getView().findViewById(R.id.textView5).setVisibility(View.GONE);

        } else  if (requestCode == PICK_PHOTO_CODE_FRONT) {
            Uri photoUri = data.getData();

            // Load the image located at photoUri into selectedImage
            Bitmap selectedImage = loadFromUri(photoUri);

            // Load the selected image into a preview
            click_image_id.setImageBitmap(selectedImage);
            click_image_id.setVisibility(View.VISIBLE);
            uploadFromDeviceFront.setVisibility(View.GONE);
            camera_open_id.setVisibility(View.GONE);
            getView().findViewById(R.id.textView4).setVisibility(View.GONE);

        } else  if (requestCode == PICK_PHOTO_CODE_BACK) {
            Uri photoUri = data.getData();

            // Load the image located at photoUri into selectedImage
            Bitmap selectedImage = loadFromUri(photoUri);

            // Load the selected image into a preview
            click_image_id_back.setImageBitmap(selectedImage);
            click_image_id_back.setVisibility(View.VISIBLE);
            uploadFromDeviceBack.setVisibility(View.GONE);
            camera_open_id_back.setVisibility(View.GONE);
            getView().findViewById(R.id.textView5).setVisibility(View.GONE);

        }
    }

    private void uploadImageToFirebase(String fileName, Uri contentUri) {
        StorageReference image = storageReference.child("images/"+ownerId +"/"+fileName);
        uploadFromDeviceFront.setVisibility(View.GONE);
        camera_open_id.setVisibility(View.GONE);
        getView().findViewById(R.id.textView4).setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getView().findViewById(R.id.textView4).setVisibility(View.GONE);
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("TAG", "onSuccess: Uploaded Image URI is "+ uri.toString());
                        image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(click_image_id);
                                click_image_id.setVisibility(View.VISIBLE);
                                progressbar.setVisibility(View.GONE);
                            }
                        });

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

    public File getPhotoFileUri(String front_back, String truckNo) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory");
        }

        String fileName = "";

        if(documentFor.equals("truck")){
            if (front_back.equals("front")) {
                timeStampFront = System.nanoTime();
                fileName = "FrontRC_" + key.toString()+"_"+timeStampFront+".jpg";
            } else {
                timeStampBack = System.nanoTime();
                fileName = "BackRC_" + key.toString()+"_"+timeStampBack+".jpg";
            }
        } else if (documentFor.equals("driver")){
            if (front_back.equals("front")) {
                timeStampFront = System.nanoTime();
                fileName = "FrontDL_" + key.toString()+"_"+timeStampFront+".jpg";
            } else {
                timeStampBack = System.nanoTime();
                fileName = "BackDL_" + key.toString()+"_"+timeStampBack+".jpg";
            }
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    // Trigger gallery selection for a photo
    public void onPickPhoto(View view, String type) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        // Bring up gallery to select a photo
        if(type.equals("front")) {
            startActivityForResult(intent, PICK_PHOTO_CODE_FRONT);
        } else {
            startActivityForResult(intent, PICK_PHOTO_CODE_BACK);
        }

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

    public long getTimeStampFront() {
        if(frontImagechanged) {
            return timeStampFront;
        }
        return 0;
    }

    public long getTimeStampBack() {
        if(backImagechanged) {
            return timeStampBack;
        }
        return 0;
    }
}