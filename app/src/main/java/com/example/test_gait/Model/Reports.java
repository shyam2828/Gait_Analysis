package com.example.test_gait.Model;

public class Reports {
    String cadence,steptime,stridetime,date;

    public Reports(String cadence, String steptime, String stridetime, String date) {
        this.cadence = cadence;
        this.steptime = steptime;
        this.stridetime = stridetime;
        this.date = date;
    }

    public String getCadence() {
        return cadence;
    }

    public void setCadence(String cadence) {
        this.cadence = cadence;
    }

    public String getSteptime() {
        return steptime;
    }

    public void setSteptime(String steptime) {
        this.steptime = steptime;
    }

    public String getStridetime() {
        return stridetime;
    }

    public void setStridetime(String stridetime) {
        this.stridetime = stridetime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

