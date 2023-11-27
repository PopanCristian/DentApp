package com.example.dentaapp;

public class Appointment {
    private String doctorUsername;
    private String date;
    private String startTime;
    private String endTime;

    // Constructor actualizat
    public Appointment(String doctorUsername, String date, String startTime, String endTime) {
        this.doctorUsername = doctorUsername;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getteri pentru fiecare câmp
    public String getDoctorUsername() {
        return doctorUsername;
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
