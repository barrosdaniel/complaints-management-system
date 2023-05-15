/*
Central Queensland University
COIT12200 - Software Design & Development (2023 Term 1)
Campus: External
Assignment 2 - Complaints Management System
Student ID: 12184305
Student Name: Daniel Barros
*/
package CMS.Model;

/**
 * Represents the Customer entity in the Complaints Management System.
 */
public class Customer {
    private String customerID;
    private String customerFirstName;
    private String customerLastName;
    private String customerContactNumber;
    private String customerEmail;
    private String customerAddress;
    private String customerProduct;
    private String customerType;

    /**
     * Constructor for the Customer class.
     * @param customerID
     * @param customerFirstName
     * @param customerLastName
     * @param customerContactNumber
     * @param customerEmail
     * @param customerAddress
     * @param customerProduct
     * @param customerType
     */
    public Customer(String customerID, String customerFirstName, String customerLastName,
                    String customerContactNumber, String customerEmail, String customerAddress,
                    String customerProduct, String customerType) {
        this.customerID = customerID;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerContactNumber = customerContactNumber;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.customerProduct = customerProduct;
        this.customerType = customerType;
    }

    // Getters and Setters
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerContactNumber() {
        return customerContactNumber;
    }

    public void setCustomerContactNumber(String customerContactNumber) {
        this.customerContactNumber = customerContactNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerProduct() {
        return customerProduct;
    }

    public void setCustomerProduct(String customerProduct) {
        this.customerProduct = customerProduct;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    /**
     * Overridden equals method for the Customer class. Checks if the customerID,
     * customerFirstName, customerLastName, customerContactNumber, customerEmail,
     * customerAddress, customerProduct and customerType are equal to the
     * same fields in the supplied Customer object.
     * @param otherCustomerObject
     * @return true if all Customer object fields are equal to the supplied
     * Customer object fields, false otherwise.
     */
    @Override
    public boolean equals(Object otherCustomerObject) {
        boolean areEqual = false;
        if (!(otherCustomerObject instanceof Customer)) {
            areEqual = false;
        }
        if (otherCustomerObject == this) {
            areEqual = true;
        }
        Customer otherCustomer = (Customer) otherCustomerObject;
        if (
                this.customerID.equals(otherCustomer.getCustomerID()) &&
                        this.customerFirstName.equals(otherCustomer.getCustomerFirstName()) &&
                        this.customerLastName.equals(otherCustomer.getCustomerLastName()) &&
                        this.customerContactNumber.equals(otherCustomer.getCustomerContactNumber()) &&
                        this.customerEmail.equals(otherCustomer.getCustomerEmail()) &&
                        this.customerAddress.equals(otherCustomer.getCustomerAddress()) &&
                        this.customerProduct.equals(otherCustomer.getCustomerProduct()) &&
                        this.customerType.equals(otherCustomer.getCustomerType())
        ) {
            areEqual = true;
        }
        return areEqual;
    }

    /**
     * Overridden toString method for the Customer class. Represents the Customer
     * object fields as a String. Used for testing purposes.
     * @return a String representation of the Customer object.
     */
    @Override
    public String toString() {
        return  "Customer{" + "customerID=" + customerID +
                ", customerFirstName=" + customerFirstName +
                ", customerLastName=" + customerLastName +
                ", customerContactNumber=" + customerContactNumber +
                ", customerEmail=" + customerEmail +
                ", customerAddress=" + customerAddress +
                ", customerType=" + customerType +
                ", customerProduct=" + customerProduct + '}';
    }
}
