package org.example.jobtrackerspring.model;

public class ServiceDisplay {
    private String jobId;
    private String jobName;
    private ServiceEntry service;

    public ServiceDisplay(String jobId, String jobName, ServiceEntry service) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.service = service;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public ServiceEntry getService() {
        return service;
    }

    public void setService(ServiceEntry service) {
        this.service = service;
    }
}

