package com.transport.khata.model;

public class JobHelperClass {

    String clientName, truckType, routeInfo;
    Number weight, jobId;

    public JobHelperClass() {
    }

    public JobHelperClass(String clientName, String truckType, String routeInfo, Number weight, Number jobId) {
        this.clientName = clientName;
        this.truckType = truckType;
        this.routeInfo = routeInfo;
        this.weight = weight;
        this.jobId = jobId;
    }

    public Number getJobId() {
        return jobId;
    }

    public void setJobId(Number jobId) {
        this.jobId = jobId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

    public String getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(String routeInfo) {
        this.routeInfo = routeInfo;
    }

    public Number getWeight() {
        return weight;
    }

    public void setWeight(Number weight) {
        this.weight = weight;
    }
}
