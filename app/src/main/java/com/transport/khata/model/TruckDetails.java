package com.transport.khata.model;

import com.google.firebase.database.PropertyName;

public class TruckDetails {

    String owner, TruckType, regdNo;

    public TruckDetails() {
    }

    public TruckDetails(String owner, String TruckType, String regdNo) {
        this.owner = owner;
        this.TruckType = TruckType;
        this.regdNo = regdNo;
    };

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRegdNo() {
        return regdNo;
    }

    public void setRegdNo(String regdNo) {
        this.regdNo = regdNo;
    }

    @PropertyName("TruckType")
    public String getTruckType() {
        return TruckType;
    }

    @PropertyName("TruckType")
    public void setTruckType(String truckType) {
        this.TruckType = truckType;
    }


}
