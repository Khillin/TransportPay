package com.transport.khata.model;

public class BrokerHelperClass {

    String brokerName, brokerPhone;
    Number jobId;

    public BrokerHelperClass() {
    }

    public BrokerHelperClass(String brokerName, String brokerPhone, Number jobId) {
        this.brokerName = brokerName;
        this.brokerPhone = brokerPhone;
        this.jobId = jobId;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBrokerPhone() {
        return brokerPhone;
    }

    public void setBrokerPhone(String brokerPhone) {
        this.brokerPhone = brokerPhone;
    }

    public Number getJobId() {
        return jobId;
    }

    public void setJobId(Number jobId) {
        this.jobId = jobId;
    }
}
