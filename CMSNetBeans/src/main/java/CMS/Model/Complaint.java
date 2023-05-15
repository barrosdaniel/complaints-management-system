/*
Central Queensland University
COIT12200 - Software Design & Development (2023 Term 1)
Campus: External
Assignment 2 - Complaints Management System
Student ID: 12184305
Student Name: Daniel Barros
*/
package CMS.Model;

import java.time.LocalDate;

/**
 * Represents the Complaint entity in the Complaints Management System.
 */
public class Complaint {
    private String complaintID;
    private String complaintsCustomerID;
    private LocalDate complaintDate;
    private String complaintServiceType;
    private String complaintStatus;
    private String problemDescription;
    private String serviceNotes;

    /**
     * Constructor for the Complaint class.
     * @param complaintID
     * @param complaintsCustomerID
     * @param complaintDate
     * @param complaintServiceType
     * @param complaintStatus
     * @param problemDescription
     * @param serviceNotes
     */
    public Complaint(String complaintID, String complaintsCustomerID,
                     LocalDate complaintDate, String complaintServiceType,
                     String complaintStatus, String problemDescription,
                     String serviceNotes) {
        this.complaintID = complaintID;
        this.complaintsCustomerID = complaintsCustomerID;
        this.complaintDate = complaintDate;
        this.complaintServiceType = complaintServiceType;
        this.complaintStatus = complaintStatus;
        this.problemDescription = problemDescription;
        this.serviceNotes = serviceNotes;
    }

    // Getters and Setters
    public String getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(String complaintID) {
        this.complaintID = complaintID;
    }

    public String getComplaintsCustomerID() {
        return complaintsCustomerID;
    }

    public void setComplaintsCustomerID(String complaintsCustomerID) {
        this.complaintsCustomerID = complaintsCustomerID;
    }

    public LocalDate getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(LocalDate complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getComplaintServiceType() {
        return complaintServiceType;
    }

    public void setComplaintServiceType(String complaintServiceType) {
        this.complaintServiceType = complaintServiceType;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getServiceNotes() {
        return serviceNotes;
    }

    public void setServiceNotes(String serviceNotes) {
        this.serviceNotes = serviceNotes;
    }

    /**
     * Overridden toString method for the Complaint class. Represents the
     * Complaint object fields as a String. Used for testing purposes.
     * @return a String representation of the Complaint object.
     */
    @Override
    public String toString() {
        return "Complaint{" + "complaintID=" + complaintID +
                ", complaintsCustomerID=" + complaintsCustomerID +
                ", complaintDate=" + complaintDate +
                ", complaintServiceType=" + complaintServiceType +
                ", complaintStatus=" + complaintStatus +
                ", problemDescription=" + problemDescription +
                ", serviceNotes=" + serviceNotes + '}';
    }
}
