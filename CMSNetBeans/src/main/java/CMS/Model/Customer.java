package CMS.Model;

/**
 *
 * @author Daniel Barros
 */
public class Customer {
    private String customerID;
    private String customerFirstName;
    private String customerLastName;
    private String customerContactNumber;
    private String customerEmail;
    private String customerAddress;
    private String customerType;
    private String customerProduct;

    public Customer(String customerID, String customerFirstName, String customerLastName, String customerContactNumber, String customerEmail, String customerAddress, String customerType, String customerProduct) {
        this.customerID = customerID;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerContactNumber = customerContactNumber;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.customerType = customerType;
        this.customerProduct = customerProduct;
    }
    
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

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerProduct() {
        return customerProduct;
    }

    public void setCustomerProduct(String customerProduct) {
        this.customerProduct = customerProduct;
    }

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
