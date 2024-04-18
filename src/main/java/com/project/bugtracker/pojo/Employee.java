package com.project.bugtracker.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee {
    @Id
    private int empId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String employeeRole;
    private String supervisorName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "assignedTo")
    private List<Bug> bugsList;

    public List<Bug> getBugs() {
        return bugsList;
    }

    public Employee() {
    }

    public Employee(int count, String firstName, String lastName, String email, String password, String employeeRole, String supervisorName) {
        this.empId = count + 1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.employeeRole = employeeRole;
        this.supervisorName = supervisorName;
        this.bugsList = new ArrayList<>();
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public List<Bug> getBugsList() {
        return bugsList;
    }

    public void setBugsList(List<Bug> bugsList) {
        this.bugsList = bugsList;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "empId=" + empId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + employeeRole + '\'' +
                ", supervisorName='" + supervisorName + '\'' +
                ", bugsList='" + bugsList + '\'' +
                '}';
    }
}