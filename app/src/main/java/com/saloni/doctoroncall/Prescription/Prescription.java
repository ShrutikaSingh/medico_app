package com.saloni.doctoroncall.Prescription;

public class Prescription {
    private String pID;
    private String docKey;
    private String patientName;
    private String doctorName;
    private String doctorPhone;
    private String prescription;
    private String fees;

    public Prescription() {

    }

    public Prescription(String pID, String docKey, String patientName, String doctorName, String doctorPhone, String prescription, String fees) {
        this.pID = pID;
        this.docKey = docKey;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.doctorPhone = doctorPhone;
        this.prescription = prescription;
        this.fees = fees;
    }

    public String getpID() {
        return pID;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }
}
