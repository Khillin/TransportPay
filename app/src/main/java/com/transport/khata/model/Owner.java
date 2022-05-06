package com.transport.khata.model;

import java.util.ArrayList;

public class Owner {
    private String phoneNo;
    private String ownerId;
    private String name;
    private ArrayList<String> trucks = new ArrayList<>();
    private ArrayList <String> Trips = new ArrayList<>();
    private ArrayList <String> drivers = new ArrayList<>();

    public Owner(String ownerId, String phoneNo) {
        name=null;
        trucks=null;
        Trips=null;
        drivers=null;
        this.ownerId = ownerId;
        this.phoneNo=phoneNo;
    }

    public Owner(String name, ArrayList<String> trucks, ArrayList<String> trips, ArrayList<String> drivers) {
        this.name = name;
        this.trucks = trucks;
        Trips = trips;
        this.drivers = drivers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTrucks() {
        return trucks;
    }

    public void setTrucks(ArrayList<String> trucks) {
        this.trucks = trucks;
    }

    public ArrayList<String> getTrips() {
        return Trips;
    }

    public void setTrips(ArrayList<String> trips) {
        Trips = trips;
    }

    public ArrayList<String> getDrivers() {
        return drivers;
    }

    public void setDrivers(ArrayList<String> drivers) {
        this.drivers = drivers;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
