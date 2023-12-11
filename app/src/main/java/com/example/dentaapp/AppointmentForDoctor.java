package com.example.dentaapp;

public class AppointmentForDoctor {
    private String patientUsername;
    private String date;
    private String startTime;
    private String endTime;

    public AppointmentForDoctor(String patientUsername, String date, String startTime, String endTime) {
        this.patientUsername = patientUsername;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}

