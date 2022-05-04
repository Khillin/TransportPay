package com.transport.khata;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.transport.khata.model.BrokerHelperClass;
import com.transport.khata.model.DriverHelperClass;
import com.transport.khata.model.JobHelperClass;

import java.util.Random;

public class createjob extends AppCompatActivity {

    EditText clientName, truckType, routeInfo, loadWeight, driverName, vehicleNum, deliveryAddress, driverPhone, brokerName, brokerPhoneNo;
    Button assignDriver, assignBroker , assignDriverPopup, assignBrokerPopup, cancelDriverPopup, cancelBrokerPopup;

    private AlertDialog.Builder dialogBuilderDriver;
    private AlertDialog dialog;

    FirebaseDatabase rootNode;
    DatabaseReference referenceJob, referenceDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createjob);

        clientName = findViewById(R.id.client_name);
        truckType = findViewById(R.id.truck_type);
        routeInfo = findViewById(R.id.route);
        loadWeight = findViewById(R.id.weight_amount);
        assignDriver = findViewById(R.id.assign_driver);
        assignBroker = findViewById(R.id.assign_broker);

        assignDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewDriverDialogue();
            }
        });

        assignBroker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewBrokerDialogue();
            }
        });


    }

    public void createNewDriverDialogue(){
        dialogBuilderDriver = new AlertDialog.Builder(this);
        final View driverPopupView = getLayoutInflater().inflate(R.layout.assign_driver_popup,null);

        driverName = (EditText) driverPopupView.findViewById(R.id.driver_name);
        vehicleNum = (EditText) driverPopupView.findViewById(R.id.truck_num);
        deliveryAddress = (EditText) driverPopupView.findViewById(R.id.delivery_address);
        driverPhone = (EditText) driverPopupView.findViewById(R.id.driver_phone);

        assignDriverPopup = (Button) driverPopupView.findViewById(R.id.assign_driver_popup);
        cancelDriverPopup = (Button) driverPopupView.findViewById(R.id.cancel_driver_popup);

        dialogBuilderDriver.setView(driverPopupView);
        dialog = dialogBuilderDriver.create();
        dialog.show();

        assignDriverPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //define assign to driver popup button
                rootNode = FirebaseDatabase.getInstance();
                referenceJob = rootNode.getReference("Job");
                referenceDriver = rootNode.getReference("Driver");

                //store job information
                Number jobId = generateUniqueJobId();
                String driverPhoneNo = driverPhone.getText().toString();
                JobHelperClass jobHelperClass = new JobHelperClass(clientName.getText().toString(), routeInfo.getText().toString(), truckType.getText().toString(),Integer.parseInt(loadWeight.getText().toString()), jobId);
                referenceJob.child(jobId.toString()).setValue(jobHelperClass);

                //store driver information
                DriverHelperClass driverHelperClass = new DriverHelperClass(driverName.getText().toString(),vehicleNum.getText().toString(),deliveryAddress.getText().toString(),driverPhoneNo, jobId);
                referenceDriver.child(driverPhoneNo).setValue(driverHelperClass);

                Toast.makeText(createjob.this,"Job created and assigned to " + driverName.getText().toString(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), createjob.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        cancelDriverPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void createNewBrokerDialogue(){

        dialogBuilderDriver = new AlertDialog.Builder(this);
        final View brokerPopupView = getLayoutInflater().inflate(R.layout.assign_broker_popup,null);

        brokerName = (EditText) brokerPopupView.findViewById(R.id.broker_name);
        brokerPhoneNo = (EditText) brokerPopupView.findViewById(R.id.broker_phone);

        assignBrokerPopup = (Button) brokerPopupView.findViewById(R.id.assign_broker_popup);
        cancelBrokerPopup = (Button) brokerPopupView.findViewById(R.id.cancel_broker_popup);

        dialogBuilderDriver.setView(brokerPopupView);
        dialog = dialogBuilderDriver.create();
        dialog.show();

        assignBrokerPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Number jobId = generateUniqueJobId();
                String brokerPhone = brokerPhoneNo.getText().toString();

                //define assign to driver popup button
                rootNode = FirebaseDatabase.getInstance();
                referenceJob = rootNode.getReference("Job");
                referenceDriver = rootNode.getReference("Broker");

                JobHelperClass jobHelperClass = new JobHelperClass(clientName.getText().toString(), routeInfo.getText().toString(), truckType.getText().toString(),Integer.parseInt(loadWeight.getText().toString()), jobId);
                referenceJob.child(jobId.toString()).setValue(jobHelperClass);

                //store broker information
                BrokerHelperClass brokerHelperClass = new BrokerHelperClass(brokerName.getText().toString(),brokerPhone, jobId);
                referenceDriver.child(brokerPhone).setValue(brokerHelperClass);

                Toast.makeText(createjob.this,"Job created and assigned to " + brokerName.getText().toString(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), createjob.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        cancelBrokerPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public Integer generateUniqueJobId() {
        Random rand = new Random();
        int rand_int1 = rand.nextInt(100000);


        return rand_int1;
    }
}