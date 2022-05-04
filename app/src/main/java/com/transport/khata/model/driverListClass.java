package com.transport.khata.model;

public class driverListClass {


    String driverPhone, drivername;
    Long frontDLimgname, backDLimgname;
    public driverListClass() {
    }

    public driverListClass(Long frontDLimgname, Long backDLimgname, String driverPhone, String drivername) {
        this.frontDLimgname = frontDLimgname;
        this.backDLimgname = backDLimgname;
        this.driverPhone = driverPhone;
        this.drivername = drivername;
    };

    public String getdrivername() {
        return drivername;
    }

    public void setdrivername(String drivername) {
        this.drivername = drivername;
    }

    public Long getfrontDLimgname() {
        return frontDLimgname;
    }

    public void setfrontDLimgname(Long frontDLimgname) {
        this.frontDLimgname = frontDLimgname;
    }

    public Long getbackDLimgname() {
        return backDLimgname;
    }

    public void setbackDLimgname(Long backDLimgname) {
        this.backDLimgname = backDLimgname;
    }

    public String getdriverPhone() {
        return driverPhone;
    }

    public void setdriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }
}
