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
//    @FXML
//    private TextField product?;
//    @FXML
//    private TextField customerType?;
    
    private ArrayList<Customer> customersList = new ArrayList();
    private ArrayList<Complaint> complaintsList = new ArrayList();
    private String customerID;
    private String customerFirstName;
    private String customerLastName;
    private String customerContactNumber;
    private String customerEmail;
    private String customerAddress;
    private String customerType;
    private String customerProduct;
    
    @FXML
    public void customerSearchButtonClick() {
        System.out.println("Search Button pressed.");
        tfFirstName.clear();
        tfLastName.clear();
        tfContactNumber.clear();
        tfEmail.clear();
        taAddress.clear();
        try (Connection connection = DatabaseHandler.getConnection()) {
            System.out.println("Connection successful.");
            customerID = tfCustomerID.getText();
            PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM customers WHERE custID = " + customerID + ";"
            );
            System.out.println("Query generation successful.");
            ResultSet results = query.executeQuery();
            System.out.println("Query execution successful.");
            if (results.next()) {
                tfFirstName.setText(results.getString("fName"));
                tfLastName.setText(results.getString("lName"));
                tfContactNumber.setText(results.getString("mobile"));
                tfEmail.setText(results.getString("email"));
                taAddress.setText(results.getString("addr"));
            }
        } catch (Exception e) {
            System.out.println("Connection failed.");
        }
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
        loadCustomersRecords();
    }    
    
}
