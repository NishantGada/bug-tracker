package com.project.bugtracker.pojo;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bugID;
    private String bugTitle;
    private String bugDescription;
    private String bugPriority;
    private Date bugDueDate;

    @ManyToOne
    @JoinColumn(name = "empId")
    private Employee assignedTo;

    public Bug() {
    }
    public Bug(String bugTitle, String bugDescription, String bugPriority, Date bugDueDate, Employee assignedTo) {
        this.bugTitle = bugTitle;
        this.bugDescription = bugDescription;
        this.bugPriority = bugPriority;
        this.bugDueDate = bugDueDate;
        this.assignedTo = assignedTo;
    }

    public Employee getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Employee assignedTo) {
        this.assignedTo = assignedTo;
    }

    public int getBugID() {
        return bugID;
    }

    public void setBugID(int bugID) {
        this.bugID = bugID;
    }

    public String getBugTitle() {
        return bugTitle;
    }

    public void setBugTitle(String bugTitle) {
        this.bugTitle = bugTitle;
    }

    public String getBugDescription() {
        return bugDescription;
    }

    public void setBugDescription(String bugDescription) {
        this.bugDescription = bugDescription;
    }

    public String getBugPriority() {
        return bugPriority;
    }

    public void setBugPriority(String bugPriority) {
        this.bugPriority = bugPriority;
    }

    public Date getBugDueDate() {
        return bugDueDate;
    }

    public void setBugDueDate(Date bugDueDate) {
        this.bugDueDate = bugDueDate;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "bugID='" + bugID + '\'' +
                ", bugTitle='" + bugTitle + '\'' +
                ", bugDescription='" + bugDescription + '\'' +
                ", bugDueDate=" + bugDueDate +
//                ", assignedTo=" + assignedTo +
                '}';
    }
}