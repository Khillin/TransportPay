package com.transport.khata.model;

import java.util.ArrayList;
import java.util.Date;

public class CreateTripHelperClass {

    private String partyName, driverName, truckNum, originAddress,
            destinationAddress, billAmount,
            billType, tripId, tripStatus;

    private ArrayList<String> truckList = new ArrayList<>();
    private ArrayList <String> driverNameList = new ArrayList<>();
    private ArrayList <String> driverPhoneList = new ArrayList<>();

    private String driverPhone;

    public ArrayList<String> getTruckList() {
        return truckList;
    }

    public void setTruckList(ArrayList<String> truckList) {
        this.truckList = truckList;
    }

    public ArrayList<String> getDriverNameList() {
        return driverNameList;
    }

    public void setDriverNameList(ArrayList<String> driverNameList) {
        this.driverNameList = driverNameList;
    }

    public ArrayList<String> getDriverPhoneList() {
        return driverPhoneList;
    }

    public void setDriverPhoneList(ArrayList<String> driverPhoneList) {
        this.driverPhoneList = driverPhoneList;
    }

    String startDate;

    public CreateTripHelperClass(){};

    public CreateTripHelperClass(String partyName, String originAddress, String destinationAddress, String billAmount, String billType, String tripId, ArrayList<String> truckList, ArrayList<String> driverNameList, ArrayList<String> driverPhoneList, String startDate,String tripStatus) {
        this.partyName = partyName;
        this.originAddress = originAddress;
        this.destinationAddress = destinationAddress;
        this.billAmount = billAmount;
        this.billType = billType;
        this.tripId = tripId;
        this.truckList = truckList;
        this.driverNameList = driverNameList;
        this.driverPhoneList = driverPhoneList;
        this.startDate = startDate;
        this.tripStatus = tripStatus;
    }

    public CreateTripHelperClass(String partyName, String originAddress, String destinationAddress, String billAmount, String billType, String tripId, ArrayList<String> truckList, ArrayList<String> driverNameList, ArrayList<String> driverPhoneList, String startDate) {
        this.partyName = partyName;
        this.originAddress = originAddress;
        this.destinationAddress = destinationAddress;
        this.billAmount = billAmount;
        this.billType = billType;
        this.tripId = tripId;
        this.truckList = truckList;
        this.driverNameList = driverNameList;
        this.driverPhoneList = driverPhoneList;
        this.startDate = startDate;
    }

//    public CreateTripHelperClass(String partyName, String driverName, String truckNum, String originAddress, String destinationAddress, String billAmount, String billType, String tripId, ArrayList<String> truckList, ArrayList<String> driverNameList, ArrayList<String> driverPhoneList, String driverPhone, String startDate) {
//        this.partyName = partyName;
//        this.driverName = driverName;
//        this.truckNum = truckNum;
//        this.originAddress = originAddress;
//        this.destinationAddress = destinationAddress;
//        this.billAmount = billAmount;
//        this.billType = billType;
//        this.tripId = tripId;
//        this.truckList = truckList;
//        this.driverNameList = driverNameList;
//        this.driverPhoneList = driverPhoneList;
//        this.driverPhone = driverPhone;
//        this.startDate = startDate;
//    }
//
//    public CreateTripHelperClass(String partyName, String driverName, String truckNum, String originAddress, String destinationAddress, String billAmount, String billType, String driverPhone, String startDate, String tripId) {
//        this.partyName = partyName;
//        this.driverName = driverName;
//        this.truckNum = truckNum;
//        this.originAddress = originAddress;
//        this.destinationAddress = destinationAddress;
//        this.billAmount = billAmount;
//        this.billType = billType;
//        this.driverPhone = driverPhone;
//        this.startDate = startDate;
//        this.tripId= tripId;
//    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getTruckNum() {
        return truckNum;
    }

    public void setTruckNum(String truckNum) {
        this.truckNum = truckNum;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }
}
