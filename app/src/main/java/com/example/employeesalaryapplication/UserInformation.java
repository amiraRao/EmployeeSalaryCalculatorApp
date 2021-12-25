package com.example.employeesalaryapplication;

import com.google.firebase.database.Exclude;

public class UserInformation {
    public String empID;
    public String empName;
    public String empPost;
    public String empContact;
    public String empSalary;
    public String mKey;

    public UserInformation() {
    }

    public UserInformation(String empID, String empName, String empPost, String empContact, String empSalary) {
        this.empID = empID;
        this.empName = empName;
        this.empPost = empPost;
        this.empContact = empContact;
        this.empSalary = empSalary;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPost() {
        return empPost;
    }

    public void setEmpPost(String empPost) {
        this.empPost = empPost;
    }

    public String getEmpContact() {
        return empContact;
    }

    public void setEmpContact(String empContact) {
        this.empContact = empContact;
    }

    public String getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(String empSalary) {
        this.empSalary = empSalary;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String Key) {
        mKey = Key;
    }
}
