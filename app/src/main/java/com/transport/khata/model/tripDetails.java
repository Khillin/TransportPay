package com.transport.khata.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class tripDetails implements Serializable {
    public String billType, destinationAddress,driverName,originAddress,ownerid,partyName,status, tripId,truckNum ;
    public int advance, billAmount;
    public JSONObject startDate, endDate;
    public Long driverPhone;

    public tripDetails() {
    }

    public tripDetails( int advance, int billAmount, String billType, String destinationAddress, String driverName, Long driverPhone, String originAddress, String ownerid, String partyName, JSONObject startDate, String status, String tripId, String truckNum) {
        this.advance = advance;
        this.billAmount = billAmount;
        this.billType = billType;
        this.destinationAddress = destinationAddress;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.originAddress = originAddress;
        this.ownerid = ownerid;
        this.partyName = partyName;
        this.startDate = startDate;
        this.status = status;
        this.tripId = tripId;
        this.truckNum = truckNum;
    };


    public int getAdvance() {
        return advance;
    }

    public void setAdvance(int advance) {
        this.advance = advance;
    }

    public int getbillAmount() {
        return billAmount;
    }

    public void setbillAmount(int billAmount) {
        this.billAmount = billAmount;
    }

    public String getdestinationAddress() {
        return destinationAddress;
    }

    public void setdestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getbillType() {
        return billType;
    }

    public void setbillType(String billType) {
        this.billType = billType;
    }

    public String getdriverName() {
        return driverName;
    }

    public void setdriverName(String driverName) {
        this.driverName = driverName;
    }

    public Long getdriverPhone() {
        return driverPhone;
    }

    public void setdriverPhone(Long driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getoriginAddress() {
        return originAddress;
    }

    public void setoriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getownerid() {
        return ownerid;
    }

    public void setownerid(String ownerid) {
        this.ownerid = ownerid;
    }

    public String getpartyName() {
        return partyName;
    }

    public void setpartyName(String partyName) {
        this.partyName = partyName;
    }

    public JSONObject getstartDate() {
        return startDate;
    }

    public void setendDate(JSONObject endDate) {
        this.endDate = endDate;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String gettripId() {
        return tripId;
    }

    public void settripId(String tripId) {
        this.tripId = tripId;
    }

    public String gettruckNum() {
        return truckNum;
    }

    public void settruckNum(String truckNum) {
        this.truckNum = truckNum;
    }
}
