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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Daniel Barros
 */
public class UserInterfaceController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadComboBoxOptions();
        loadCustomersRecords();
        viewAllCustomersButtonClick();
    }
    
/* =============================================================================
CUSTOMERS
============================================================================= */
    // Customer Section UI Controls
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
    
    // Customer Section Variables
    private final ArrayList<Customer> customersList = new ArrayList();
    private final ArrayList<Customer> tempCustomersList = new ArrayList();
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
    private String customerSet;
    private Customer iteratingCustomer;
    
// Customer Helper Methods
    private void loadComboBoxOptions() {
        cbProduct.getItems().addAll("Internet", "Phone", "Billing");
        cbCustomerType.getItems().addAll("Business", "Domestic");
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
    
    private void refreshPaginationNumbers() {
        tfCurrentCustomer.setText(currentCustomer + 1 + "");
        tfTotalCustomers.setText(numberOfCustomers + "");
    }
    
    // New Customer Button Handlers
    @FXML
    public void newCustomerButtonClick() {
        nextSaveAction = "New";
        clearAllCustomersFields();
        enableAllCustomerFields();
        tfCurrentCustomer.setText(numberOfCustomers + 1 + "");
        tfTotalCustomers.setText(numberOfCustomers + 1 + "");
    }
    
    private void clearAllCustomersFields() {
        tfCustomerID.clear();
        tfFirstName.clear();
        tfLastName.clear();
        tfContactNumber.clear();
        tfEmail.clear();
        taAddress.clear();
        cbProduct.getSelectionModel().clearSelection();
        cbCustomerType.getSelectionModel().clearSelection();
    }
    
    // Edit Customer Button Handlers
    @FXML
    public void editCustomerButtonClick() {
        if (customerSet.equals("FullSet")) {
            nextSaveAction = "Edit";
            enableAllCustomerFields();
            tfCustomerID.setEditable(false);
            tfCustomerID.setStyle("-fx-control-inner-background: #F1F1F1;");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cannot edit in Search");
            alert.setContentText("Customer edit is not allowed whilst in Search mode. " + 
                    "Please click the 'View All' button to return to View mode, then " +
                    "try again.");
            alert.showAndWait();
        }
    }
    
    // Save Customer Button Handlers
    @FXML
    public void saveCustomerButtonClick() {
        if (tfFirstName.isEditable()) {
            if (nextSaveAction.equals("New")) {
                saveNewCustomer();
            } else {
                saveEditedCustomer();
            }
        }
    }
    
    private void saveNewCustomer() {
        Customer newCustomer = makeNewCustomerObjectfromUI();
        boolean addedToDatabase = addCustomerToDatabase(newCustomer);
        if (addedToDatabase) {
            disableAllCustomerFields();
            customersList.clear();
            loadCustomersRecords();
            int indexOfNewCustomer = -1;
            for (int i = 0; i < customersList.size(); i++) {
                if (customersList.get(i).getCustomerID().equals(newCustomer.getCustomerID())) {
                    indexOfNewCustomer = i;
                    break;
                }
            }
            currentCustomer = indexOfNewCustomer;
            displayCustomerRecord(currentCustomer);
            numberOfCustomers = customersList.size();
            refreshPaginationNumbers();
            nextSaveAction = null;
            displaySavedCustomerAlert();
        }
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
            PreparedStatement insertNewCustomerStatement = connection.prepareStatement(
                String.format("INSERT INTO customers (custID, fName, lName, mobile, email, addr, product, custType) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');", 
                customerID, customerFirstName, customerLastName, customerContactNumber,
                customerEmail, customerAddress, customerProduct, customerType)
            );
            int rowsInserted = insertNewCustomerStatement.executeUpdate();
            if (rowsInserted > 0) {
                addedToDatabase = true;
            } else {
                System.out.println("ERROR: Customer record not added to the Customer database.");
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Connection to the Customers database failed. Unable to save Customer to database.");
            e.printStackTrace();
        }
        return addedToDatabase;
    }
    
    private void displayCustomerRecord(int index) {
        Customer customer;
        if (customerSet.equals("FullSet")) {
            customer = customersList.get(index);
        } else {
            customer = tempCustomersList.get(index);
        }
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
    
    private void displaySavedCustomerAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Customer saved");
        alert.setContentText("Customer record saved successfully.");
        alert.showAndWait();
    }
    
    private void saveEditedCustomer() {
        int indexOfEditedCustomer = Integer.parseInt(tfCurrentCustomer.getText()) - 1;
        Customer originalCustomer = customersList.get(indexOfEditedCustomer);
        Customer editedCustomer = makeNewCustomerObjectfromUI();
        if (editedCustomer.equals(originalCustomer)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No changes to Save");
            alert.setContentText("The customer record you are trying to save has no changes.");
            alert.showAndWait();
        } else {
            if (alterCustomerInDatabase(editedCustomer)) {
                customersList.set(indexOfEditedCustomer, editedCustomer);
                disableAllCustomerFields();
                nextSaveAction = null;
                displaySavedCustomerAlert();
            };
        }
    }
    
    private boolean alterCustomerInDatabase(Customer editedCustomer) {
        boolean editedInDatabase = false;
        customerID = editedCustomer.getCustomerID();
        customerFirstName = editedCustomer.getCustomerFirstName();
        customerLastName = editedCustomer.getCustomerLastName();
        customerContactNumber = editedCustomer.getCustomerContactNumber();
        customerEmail = editedCustomer.getCustomerEmail();
        customerAddress = editedCustomer.getCustomerAddress();
        customerProduct = editedCustomer.getCustomerProduct();
        customerType = editedCustomer.getCustomerType();
        try (Connection connection = DatabaseHandler.getConnection()) {
            PreparedStatement editCustomerStatement = connection.prepareStatement(
             String.format(
          "UPDATE customers SET fname = '%s', lName = '%s', mobile = '%s', email = '%s', addr = '%s', custType = '%s', product = '%s' WHERE custID = %s;",
            customerFirstName, customerLastName, customerContactNumber, customerEmail, customerAddress, customerProduct, customerType, customerID)
            );
            int rowsDeleted = editCustomerStatement.executeUpdate();
            if (rowsDeleted > 0) {
                editedInDatabase = true;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Customer not edited in database.");
                alert.setContentText("The customer record cannot be edited in the database.");
                alert.showAndWait();
            }
            connection.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer not edited in database.");
            alert.setContentText("Unable to connect to the database. Customer record not edited in database.");
            alert.showAndWait();
        }
        return editedInDatabase;
    }
    
    // Search Customer Button Handlers
    @FXML
    public void customerSearchButtonClick() {
        tfCustomerID.clear();
        tfCustomerID.setEditable(false);
        tfCustomerID.setStyle("-fx-control-inner-background: #F1F1F1;");
        tfFirstName.clear();
        tfFirstName.setEditable(false);
        tfFirstName.setStyle("-fx-control-inner-background: #F1F1F1;");
        tfLastName.clear();
        tfLastName.setEditable(true);
        tfLastName.setStyle("-fx-control-inner-background: #FFFFFF;");
        tfContactNumber.clear();
        tfContactNumber.setEditable(true);
        tfContactNumber.setStyle("-fx-control-inner-background: #FFFFFF;");
        tfEmail.clear();
        tfEmail.setEditable(false);
        tfEmail.setStyle("-fx-control-inner-background: #F1F1F1;");
        taAddress.clear();
        taAddress.setEditable(false);
        taAddress.setStyle("-fx-control-inner-background: #F1F1F1;");
        cbProduct.setValue(null);
        cbProduct.setDisable(true);
        cbProduct.setStyle("-fx-control-inner-background: #F1F1F1;");
        cbCustomerType.setValue(null);
        cbCustomerType.setDisable(true);
        cbCustomerType.setStyle("-fx-control-inner-background: #F1F1F1;");
        tfCurrentCustomer.clear();
        tfCurrentCustomer.setEditable(false);
        tfCurrentCustomer.setStyle("-fx-control-inner-background: #F1F1F1;");
        tfTotalCustomers.clear();
        tfTotalCustomers.setEditable(false);
        tfTotalCustomers.setStyle("-fx-control-inner-background: #F1F1F1;");
        customerSet = "SearchSet";
    }
    
    // View All Customer Button Handlers
    @FXML
    public void viewAllCustomersButtonClick() {
        customerSet = "FullSet";
        disableAllCustomerFields();
        currentCustomer = 0;
        numberOfCustomers = customersList.size();
        displayCustomerRecord(currentCustomer);
        refreshPaginationNumbers();
    }
    
    // Clear Customer Button Handlers
    @FXML
    public void clearAllCustomerFieldsButtonClick() {
        if (tfCustomerID.isEditable()) {
            clearAllCustomersFields();
        }
    }
    
    // Customer Last Name Search Button Handlers
    @FXML
    public void customerSearchLastNameButtonClick() {
        if (customerSet.equals("FullSet")) {
            displayIncorrectSearchAlert();
            return;
        }
        boolean matchFound = false;
        tempCustomersList.clear();
        String lastNameInput = tfLastName.getText();
        String iteratingCustomerLastName;
        for (int i = 0; i < customersList.size(); i++) {
            iteratingCustomer = customersList.get(i);
            iteratingCustomerLastName = iteratingCustomer.getCustomerLastName();
            if (iteratingCustomerLastName.contains(lastNameInput)) {
                tempCustomersList.add(iteratingCustomer);
                matchFound = true;
            }
        }
        if (matchFound) {
            disableAllCustomerFields();
            currentCustomer = 0;
            numberOfCustomers = tempCustomersList.size();
            displayCustomerRecord(currentCustomer);
            refreshPaginationNumbers();
        }
    }
    
    // Customer Contact Number Search Button Handlers
    @FXML
    public void customerSearchContactNumberButtonClick() {
        if (customerSet.equals("FullSet")) {
            displayIncorrectSearchAlert();
            return;
        }
        boolean matchFound = false;
        tempCustomersList.clear();
        String contactNumberInput = tfContactNumber.getText();
        String iteratingCustomerContactNumber;
        for (int i = 0; i < customersList.size(); i++) {
            iteratingCustomer = customersList.get(i);
            iteratingCustomerContactNumber = iteratingCustomer.getCustomerContactNumber();
            if (iteratingCustomerContactNumber.contains(contactNumberInput)) {
                tempCustomersList.add(iteratingCustomer);
                matchFound = true;
            }
        }
        if (matchFound) {
            disableAllCustomerFields();
            currentCustomer = 0;
            numberOfCustomers = tempCustomersList.size();
            displayCustomerRecord(currentCustomer);
            refreshPaginationNumbers();
        }
    }
    
    private void displayIncorrectSearchAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Search not allowed in View mode.");
        alert.setContentText("Searching for a customer is now allowed in View mode. " + 
            "Please click the 'Customer Search' button first, to start a search.");
        alert.showAndWait();
    }
    
    // Previous Customer Button Handlers
    @FXML
    public void previousCustomerButtonClick() {
        disableAllCustomerFields();
        if (currentCustomer == 0) {
            currentCustomer = numberOfCustomers - 1;
        } else {
            currentCustomer--;
        }
        displayCustomerRecord(currentCustomer);
        refreshPaginationNumbers();
    }
    
    // Next Customer Button Handlers
    @FXML
    public void nextCustomerButtonClick() {
        disableAllCustomerFields();
        if (currentCustomer + 1 == numberOfCustomers) {
            currentCustomer = 0;
        } else {
            currentCustomer++;
        }
        displayCustomerRecord(currentCustomer);
        refreshPaginationNumbers();
    }
    
/* =============================================================================
COMPLAINTS
============================================================================= */    
    // Customer Section UI Controls
    
    
    // Customer Section Variables
    private final ArrayList<Complaint> complaintsList = new ArrayList();
    private final ArrayList<Complaint> tempComplaintsList = new ArrayList();
}
