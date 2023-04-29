package CMS;

import CMS.Model.Complaint;
import CMS.Model.Customer;
import CMS.Util.DatabaseHandler;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Daniel Barros
 */
public class UserInterfaceController implements Initializable {
    
    @FXML
    private TextField tfCustomerID;
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfContactNumber;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextArea taAddress;
    @FXML
    private ComboBox cbProduct;
    @FXML
    private ComboBox cbCustomerType;
    
    private ArrayList<Customer> customersList = new ArrayList();
    private ArrayList<Customer> tempCustomersList = new ArrayList();
    private ArrayList<Complaint> complaintsList = new ArrayList();
    private ArrayList<Complaint> tempComplaintsList = new ArrayList();
    private String customerID;
    private String customerFirstName;
    private String customerLastName;
    private String customerContactNumber;
    private String customerEmail;
    private String customerAddress;
    private String customerType;
    private String customerProduct;
    
    private void loadComboBoxOptions() {
        cbProduct.getItems().addAll("Internet", "Phone", "Billing");
        cbCustomerType.getItems().addAll("Business", "Residential");
    }
    
    @FXML
    private void clearAllCustomerFieldsButtonClick() {
        tfCustomerID.clear();
        tfFirstName.clear();
        tfLastName.clear();
        tfContactNumber.clear();
        tfEmail.clear();
        taAddress.clear();
        cbProduct.getSelectionModel().clearSelection();
        cbCustomerType.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void customerSearchLastNameButtonClick() {
        System.out.println("Search by Last Name button clicked.");
        String lastNameInput = tfLastName.getText();
        System.out.println("User entered: " + lastNameInput);
        clearAllCustomerFieldsButtonClick();
        tempCustomersList.clear();
        for (Customer customer : customersList) {
            if (customer.getCustomerLastName().contains(lastNameInput)) {
                System.out.println("Match found for " + lastNameInput);
                tempCustomersList.add(customer);
            }
        }
        if (tempCustomersList.size() == 0) {
            System.out.println("No match foudn for " + lastNameInput);
        } else {
            for (Customer customer : tempCustomersList) {
                System.out.println(customer);
            }
        }
    }
    
    @FXML
    public void customerSearchContactNumberButtonClick() {
        System.out.println("Search by Contact Number button clicked.");
    }
    
    
    private void loadCustomersRecords() {
        try (Connection connection = DatabaseHandler.getConnection()) {
            PreparedStatement getAllCustomersStatement = connection.prepareStatement(
                "SELECT * FROM customers;"
            );
            ResultSet getAllCustomersQueryResults = getAllCustomersStatement.executeQuery();
            while (getAllCustomersQueryResults.next()) {
                customerID = getAllCustomersQueryResults.getString("custID");
                customerFirstName = getAllCustomersQueryResults.getString("fName");
                customerLastName = getAllCustomersQueryResults.getString("lName");
                customerContactNumber = getAllCustomersQueryResults.getString("mobile");
                customerEmail = getAllCustomersQueryResults.getString("email");
                customerAddress = getAllCustomersQueryResults.getString("addr");
                customerType = getAllCustomersQueryResults.getString("custType");
                customerProduct = getAllCustomersQueryResults.getString("product");
                Customer newCustomer = new Customer(
                        customerID,
                        customerFirstName, 
                        customerLastName,
                        customerContactNumber, 
                        customerEmail, 
                        customerAddress, 
                        customerType,
                        customerProduct);
                customersList.add(newCustomer);
            }
        } catch (Exception e) {
            System.out.println("Connection to the Customers database failed.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadComboBoxOptions();
        loadCustomersRecords();
    }
}
