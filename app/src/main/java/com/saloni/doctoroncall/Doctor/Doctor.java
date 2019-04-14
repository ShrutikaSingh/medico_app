package com.saloni.doctoroncall.Doctor;

import java.util.ArrayList;
import java.util.Arrays;

public class Doctor {

    private String name;
    private String mobile;
    private String qualification;
    private String area;
    private String email;
    private String password;
    private ArrayList<Integer> days;
    private int hrs_from;
    private int hrs_to;

    private String latitude;
    private String longitude;

    public Doctor() {

    }

    public Doctor(String[] docDetails, ArrayList<Integer> days, int hrs_from, int hrs_to) {
        this.name = docDetails[0];
        this.mobile = docDetails[1];
        this.qualification = docDetails[2];
        this.area = docDetails[3];
        this.email = docDetails[4];
        this.password = docDetails[5];

        this.latitude = docDetails[6];
        this.longitude = docDetails[7];

        this.days = days;
        this.hrs_from = hrs_from;
        this.hrs_to = hrs_to;

    }

    public Doctor(String name, String mobile, String qualification, String area, String email, String password, String latitude, String longitude) {
        this.name = name;
        this.mobile = mobile;
        this.qualification = qualification;
        this.area = area;
        this.email = email;
        this.password = password;
        this.days = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 1, 1));
        this.hrs_from = 1800;
        this.hrs_to = 2100;

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHrs_from() {
        return hrs_from;
    }

    public void setHrs_from(int hrs_from) {
        this.hrs_from = hrs_from;
    }

    public int getHrs_to() {
        return hrs_to;
    }

    public void setHrs_to(int hrs_to) {
        this.hrs_to = hrs_to;
    }

    public ArrayList<Integer> getDays() {
        return days;
    }

    public void setDays(ArrayList<Integer> days) {
        this.days = days;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
