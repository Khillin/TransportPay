package com.transport.khata.model;

public class DriverHelperClass {

    String driverName, vehicleNumber, deliveryAddress, driverPhone;
    Number jobId;

    public DriverHelperClass() {
    }

    public DriverHelperClass(String driverName, String vehicleNumber, String deliveryAddress, String driverPhone, Number jobId) {
        this.driverName = driverName;
        this.vehicleNumber = vehicleNumber;
        this.deliveryAddress = deliveryAddress;
        this.driverPhone = driverPhone;
        this.jobId = jobId;
    }


    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Number getJobId() {
        return jobId;
    }

    public void setJobId(Number jobId) {
        this.jobId = jobId;
    }
}
