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
    @FXML
    private TextField tfCurrentCustomer;
    @FXML
    private TextField tfTotalCustomers;
    
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
    private String customerProduct;
    private String customerType;
    private int currentCustomer;
    private int numberOfCustomers;
    private String nextSaveAction;
    
    private void loadComboBoxOptions() {
        cbProduct.getItems().addAll("Internet", "Phone", "Billing");
        cbCustomerType.getItems().addAll("Business", "Domestic");
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
    
    @FXML
    public void newCustomerButtonClick() {
        nextSaveAction = "New";
        enableAllCustomerFields();
    }
    
    @FXML
    public void saveCustomerButtonClick() {
        Customer newCustomer = makeNewCustomerObjectfromUI();
        boolean addedToDatabase = addCustomerToDatabase(newCustomer);
        if (addedToDatabase) {
            customersList.add(newCustomer);
        }
        clearAllCustomerFieldsButtonClick();
        disableAllCustomerFields();
        nextSaveAction = null;
    }
    
    private Customer makeNewCustomerObjectfromUI() {
        customerID = tfCustomerID.getText();
        customerFirstName = tfFirstName.getText();
        customerLastName = tfLastName.getText();
        customerContactNumber = tfContactNumber.getText();
        customerContactNumber = removeBlankSpaces(customerContactNumber);
        customerEmail = tfEmail.getText();
        customerAddress = taAddress.getText();
        customerProduct = cbProduct.getValue().toString();
        customerType = cbCustomerType.getValue().toString();
        Customer newCustomer = getNewCustomerObject();
        return newCustomer;
    }
    
    private String removeBlankSpaces (String input) {
        if (input == null) {
            return "";
        } else {
            String trimmedInput = input.trim();
            trimmedInput = trimmedInput.replace(" ", "");
            return trimmedInput;
        }
    }
    
    private Customer getNewCustomerObject() {
        return new Customer(
            customerID,
            customerFirstName,
            customerLastName,
            customerContactNumber,
            customerEmail,
            customerAddress,
            customerType,
            customerProduct);
    }
    
    private void enableAllCustomerFields() {
        tfCustomerID.setEditable(true);
        tfCustomerID.setStyle("-fx-control-inner-background: #FFFFFF;");
        tfFirstName.setEditable(true);
        tfFirstName.setStyle("-fx-control-inner-background: #FFFFFF;");
        tfLastName.setEditable(true);
        tfLastName.setStyle("-fx-control-inner-background: #FFFFFF;");
        tfContactNumber.setEditable(true);
        tfContactNumber.setStyle("-fx-control-inner-background: #FFFFFF;");
        tfEmail.setEditable(true);
        tfEmail.setStyle("-fx-control-inner-background: #FFFFFF;");
        taAddress.setEditable(true);
        taAddress.setStyle("-fx-control-inner-background: #FFFFFF;");
        cbProduct.setDisable(false);
        cbProduct.setStyle("-fx-control-inner-background: #FFFFFF;");
        cbCustomerType.setDisable(false);
        cbCustomerType.setStyle("-fx-control-inner-background: #FFFFFF;");
    }
    
    private void disableAllCustomerFields() {
        tfCustomerID.setEditable(false);
        tfCustomerID.setStyle("-fx-control-inner-background: #F1F1F1;");
        tfFirstName.setEditable(false);
        tfFirstName.setStyle("-fx-control-inner-background: #F1F1F1;");
        tfLastName.setEditable(false);
        tfLastName.setStyle("-fx-control-inner-background: #F1F1F1;");
        tfContactNumber.setEditable(false);
        tfContactNumber.setStyle("-fx-control-inner-background: #F1F1F1;");
        tfEmail.setEditable(false);
        tfEmail.setStyle("-fx-control-inner-background: #F1F1F1;");
        taAddress.setEditable(false);
        taAddress.setStyle("-fx-control-inner-background: #F1F1F1;");
        cbProduct.setDisable(true);
        cbProduct.setStyle("-fx-control-inner-background: #F1F1F1;");
        cbCustomerType.setDisable(true);
        cbCustomerType.setStyle("-fx-control-inner-background: #F1F1F1;");
        tfCurrentCustomer.setEditable(false);
        tfCurrentCustomer.setStyle("-fx-control-inner-background: #F1F1F1;");
        tfTotalCustomers.setEditable(false);
        tfTotalCustomers.setStyle("-fx-control-inner-background: #F1F1F1;");
    }
    
    private boolean addCustomerToDatabase(Customer newCustomer) {
        boolean addedToDatabase = false;
        customerID = newCustomer.getCustomerID();
        customerFirstName = newCustomer.getCustomerFirstName();
        customerLastName = newCustomer.getCustomerLastName();
        customerContactNumber = newCustomer.getCustomerContactNumber();
        customerEmail = newCustomer.getCustomerEmail();
        customerAddress = newCustomer.getCustomerAddress();
        customerProduct = newCustomer.getCustomerProduct();
        customerType = newCustomer.getCustomerType();
        try (Connection connection = DatabaseHandler.getConnection()) {
            System.out.println("Connection to Customer database successfull.");
            PreparedStatement insertNewCustomerStatement = connection.prepareStatement(
                String.format("INSERT INTO customers (custID, fName, lName, mobile, email, addr, product, custType) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');", 
                customerID, customerFirstName, customerLastName, customerContactNumber,
                customerEmail, customerAddress, customerProduct, customerType)
            );
            System.out.println("Query generated successfully.");
            int rowsInserted = insertNewCustomerStatement.executeUpdate();
            System.out.println("Query executed successfully.");
            if (rowsInserted > 0) {
                System.out.println("Customer added to the database.");
                System.out.println(newCustomer);
                addedToDatabase = true;
            } else {
                System.out.println("ERROR: Customer record not added to the Customer database.");
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Connection to the Customers database failed. Unable to save records to database.");
            e.printStackTrace();
        }
        return addedToDatabase;
    }
    
    private void loadCustomersRecords() {
        try (Connection connection = DatabaseHandler.getConnection()) {
            PreparedStatement getAllCustomersStatement = connection.prepareStatement(
                "SELECT * FROM customers ORDER BY custID;"
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
                Customer newCustomer = getNewCustomerObject();
                customersList.add(newCustomer);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Connection to the Customers database failed. Unable to load customers from Customers database.");
        }
    }

    private void refreshPaginationNumbers(String set) {
        if (set.equals("FullSet")); {
            tfCurrentCustomer.setText(currentCustomer + 1 + "");
            tfTotalCustomers.setText(numberOfCustomers + "");
        }
    }
    
    private void displayCustomerRecord(int index) {
        Customer customer = customersList.get(index);
        tfCustomerID.setText(customer.getCustomerID());
        tfFirstName.setText(customer.getCustomerFirstName());
        tfLastName.setText(customer.getCustomerLastName());
        tfContactNumber.setText(customer.getCustomerContactNumber());
        tfEmail.setText(customer.getCustomerEmail());
        taAddress.setText(customer.getCustomerAddress());
        cbProduct.setValue(customer.getCustomerProduct());
        cbProduct.setStyle("-fx-opacity: 1.0");
        cbCustomerType.setValue(customer.getCustomerType());
        cbCustomerType.setStyle("-fx-opacity: 1.0");
    }
    
    @FXML
    public void nextCustomerButtonClick() {
        if (currentCustomer + 1 == numberOfCustomers) {
            currentCustomer = 0;
        } else {
            currentCustomer++;
        }
        displayCustomerRecord(currentCustomer);
        refreshPaginationNumbers("FullSet");
    }
    
    @FXML
    public void previousCustomerButtonClick() {
        if (currentCustomer == 0) {
            currentCustomer = numberOfCustomers - 1;
        } else {
            currentCustomer--;
        }
        displayCustomerRecord(currentCustomer);
        refreshPaginationNumbers("FullSet");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadComboBoxOptions();
        disableAllCustomerFields();
        loadCustomersRecords();
        currentCustomer = 0;
        numberOfCustomers = customersList.size();
        displayCustomerRecord(currentCustomer);
        refreshPaginationNumbers("FullSet");
    }

}
