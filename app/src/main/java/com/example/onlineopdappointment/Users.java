package com.example.onlineopdappointment;

public class Users {
    public String Hospital_Name,Hospital_Speciality;

    public Users() {

    }

    public Users(String hospital_Name, String hospital_Speciality) {
        Hospital_Name = hospital_Name;
        Hospital_Speciality = hospital_Speciality;
    }

    public String getHospital_Name() {
        return Hospital_Name;
    }

    public void setHospital_Name(String hospital_Name) {
        Hospital_Name = hospital_Name;
    }

    public String getHospital_Speciality() {
        return Hospital_Speciality;
    }

    public void setHospital_Speciality(String hospital_Speciality) {
        Hospital_Speciality = hospital_Speciality;
    }

}
