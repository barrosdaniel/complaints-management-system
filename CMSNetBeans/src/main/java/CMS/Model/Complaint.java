package CMS.Model;

import java.time.LocalDate;

/**
 *
 * @author Daniel Barros
 */
public class Complaint {
    private String complaintID;
    private String complaintsCustomerID;
    private LocalDate complaintDate;
    private String complaintServiceType;
    private String complaintStatus;
    private String problemDescription;
    private String serviceNotes;

    public Complaint(String complaintID, String complaintsCustomerID, LocalDate complaintDate, String complaintServiceType, String complaintStatus, String problemDescription, String serviceNotes) {
        this.complaintID = complaintID;
        this.complaintsCustomerID = complaintsCustomerID;
        this.complaintDate = complaintDate;
        this.complaintServiceType = complaintServiceType;
        this.complaintStatus = complaintStatus;
        this.problemDescription = problemDescription;
        this.serviceNotes = serviceNotes;
    }

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
    
    @Override
    public String toString() {
        return "Complaint{" + "complaintID=" + complaintID + ", complaintsCustomerID=" + complaintsCustomerID + ", complaintDate=" + complaintDate + ", complaintServiceType=" + complaintServiceType + ", complaintStatus=" + complaintStatus + ", problemDescription=" + problemDescription + ", serviceNotes=" + serviceNotes + '}';
    }
}
