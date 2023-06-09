/*
Central Queensland University
COIT12200 - Software Design & Development (2023 Term 1)
Campus: External
Assignment 2 - Complaints Management System
Student ID: 12184305
Student Name: Daniel Barros
*/
package CMS;

import CMS.Model.Complaint;
import CMS.Model.Customer;
import CMS.Util.DatabaseHandler;
import CMS.Util.Utilities;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * User Interface FXML file Controller class. Implements the Initializable
 * Interface. This class is responsible for handling all the user interface
 * events and actions.
 */
public class UserInterfaceController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCustomerComboBoxOptions();
        loadComplaintComboBoxOptions();
        loadCustomersRecords();
        loadComplaintsRecords();
        viewAllCustomersButtonClick();
        viewAllComplaintsButtonClick();
        disableReportField();
    }

    private void displayUnsuccessfulRecordSaveAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Unsuccessful record save");
        alert.setContentText("The attempt to save a record to the database failed. " +
                "Review the information provided and try again.");
        alert.showAndWait();
    }

    private void displayDatabaseConnectionErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database connection failed");
        alert.setContentText("Connection to the database failed. Unable to load " +
                "records from database.");
        alert.showAndWait();
    }

    private void displayInvalidFieldsAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid information entered");
        alert.setContentText("Unable to save the data to the database. Please " +
                "review the information entered and try again.");
        alert.showAndWait();
    }

    /**
     * Method to exit the application. Handles the Exit button click event.
     */
    @FXML
    public void exitButtonClick() {
        Platform.exit();
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
    private SaveAction nextCustomerSaveAction;
    private DataSet customerSet;
    private Customer iteratingCustomer;
    private final int FIRST_NAME_FIELD_MAX_SIZE = 30;
    private final int LAST_NAME_FIELD_MAX_SIZE = 30;
    private final int CONTACT_NUMBER_FIELD_MAX_SIZE = 10;
    private final int EMAIL_FIELD_MAX_SIZE = 30;
    private final int ADDRESS_FIELD_MAX_SIZE = 50;
    private final int PRODUCT_FIELD_MAX_SIZE = 10;
    private final int CUSTOMER_TYPE_FIELD_MAX_SIZE = 10;

    // Customer Helper Methods
    private void loadCustomerComboBoxOptions() {
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
            getAllCustomersStatement.close();
            getAllCustomersQueryResults.close();
            connection.close();
        } catch (Exception e) {
            displayDatabaseConnectionErrorAlert();
        }
    }

    private void refreshCustomerPaginationNumbers() {
        tfCurrentCustomer.setText(currentCustomer + 1 + "");
        tfTotalCustomers.setText(numberOfCustomers + "");
    }

    // New Customer Button Handlers

    /**
     * This method is called when the New Customer button is clicked. It clears
     * all the fields and enables them for the user to enter new customer
     * details. It sets the nextCustomerSaveAction to NEW. This method also
     * sets the current customer number and total number of customers to the
     * current number of customers + 1. This information is used in the
     * customer records pagination.
     */
    @FXML
    public void newCustomerButtonClick() {
        nextCustomerSaveAction = SaveAction.NEW;
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
    /**
     * This method is called when the Edit Customer button is clicked. It
     * enables all the fields for the user to edit the customer details. It
     * sets the nextCustomerSaveAction to EDIT. This method also disables the
     * Customer ID field so that the user cannot edit it. This is because the
     * Customer ID is the primary key and must not be changed. It enables all
     * other editable customer fields. An alert is displayed if the user clicks
     * the Edit Customer button whilst in Search mode.
     */
    @FXML
    public void editCustomerButtonClick() {
        if (customerSet.equals(DataSet.FULL_SET)) {
            nextCustomerSaveAction = SaveAction.EDIT;
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
    /**
     * This method is called when the Save Customer button is clicked. It
     * validates all the customer fields and saves the new customer details to
     * the database if the fields are valid. It also saves the edited customer
     * details to the database if the fields are valid. It sets the next
     * customer save action to NEW if the user is saving a new customer. It sets
     * the next customer save action to EDIT if the user is saving an edited
     * customer. An alert is displayed if the user clicks the Save Customer
     * button with invalid field values.
     */
    @FXML
    public void saveCustomerButtonClick() {
        if (tfFirstName.isEditable()) {
            if (validateAllCustomerFieldsInput()) {
                if (nextCustomerSaveAction.equals(SaveAction.NEW)) {
                    saveNewCustomer();
                } else {
                    saveEditedCustomer();
                }
            } else {
                displayInvalidFieldsAlert();
            }
        }
    }

    private boolean validateAllCustomerFieldsInput() {
        boolean allCustomerFieldsAreValid = false;
        if (
                validateCustomerID() &&
                        validateCustomerFirstName() &&
                        validateCustomerLastName() &&
                        validateCustomerContactNumber() &&
                        validateCustomerEmail() &&
                        validateCustomerAddress() &&
                        validateCustomerProduct() &&
                        validateCustomerType()
        ) {
            allCustomerFieldsAreValid = true;
        }
        return allCustomerFieldsAreValid;
    }

    private boolean validateCustomerID() {
        String customerIDInput = tfCustomerID.getText();
        if (Utilities.isEmptyInput(customerIDInput) ||
                Utilities.isNotIntegerInput(customerIDInput) ||
                Utilities.isTooLarge(customerIDInput)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Customer ID");
            alert.setContentText("The field 'Customer ID' must be filled in, " +
                    "must be numeric, and must not be greater than than " +
                    Integer.MAX_VALUE + ".");
            alert.showAndWait();
            return false;
        }
        if (nextCustomerSaveAction.equals(SaveAction.NEW) &&
                Utilities.customerIDAlreadyExists(customerIDInput, customersList)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Existing Customer ID");
            alert.setContentText("The field 'Customer ID' must not already exist.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateCustomerFirstName() {
        String customerFirstNameInput = tfFirstName.getText();
        if (Utilities.isTooLong(customerFirstNameInput,
                FIRST_NAME_FIELD_MAX_SIZE) ||
                Utilities.isEmptyInput(customerFirstNameInput)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Customer First Name");
            alert.setContentText("The field 'First Name' must be filled in " +
                    "and must not be longer than 30 characters.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateCustomerLastName() {
        String customerLastNameInput = tfLastName.getText();
        if (Utilities.isTooLong(customerLastNameInput,
                LAST_NAME_FIELD_MAX_SIZE) ||
                Utilities.isEmptyInput(customerLastNameInput)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Customer Last Name");
            alert.setContentText("The field 'Last Name' must be filled in " +
                    "and must not be longer than 30 characters.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateCustomerContactNumber() {
        String contactNumberInput = tfContactNumber.getText();
        if (Utilities.isEmptyInput(contactNumberInput) ||
                Utilities.isNotIntegerInput(contactNumberInput) ||
                Utilities.isTooLong(contactNumberInput, CONTACT_NUMBER_FIELD_MAX_SIZE)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Customer Contact Number");
            alert.setContentText("The field 'Contact Number' must be filled in, " +
                    "must be numeric, and must not be longer than 10 characters.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateCustomerEmail() {
        String customerEmailInput = tfEmail.getText();
        if (Utilities.isTooLong(customerEmailInput,
                EMAIL_FIELD_MAX_SIZE) ||
                Utilities.isEmptyInput(customerEmailInput) ||
                Utilities.hasNoAt(customerEmailInput)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Customer Email");
            alert.setContentText("The field 'Email' must be filled in, " +
                    "must have a '@', and must not be longer than 30 characters.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateCustomerAddress() {
        String customerAddressInput = taAddress.getText();
        if (Utilities.isTooLong(customerAddressInput,
                ADDRESS_FIELD_MAX_SIZE) ||
                Utilities.isEmptyInput(customerAddressInput)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Customer Address");
            alert.setContentText("The field 'Address' must be filled in " +
                    "and must not be longer than 50 characters.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateCustomerProduct() {
        try {
            String product = cbProduct.getValue().toString();
            if (Utilities.isNotSelected(product) ||
                    Utilities.isTooLong(product, PRODUCT_FIELD_MAX_SIZE)) {
                throw new Exception();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Customer Product");
            alert.setContentText("The field 'Product' must be selected " +
                    "and must not be longer than 10 characters.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateCustomerType() {
        try {
            String customerType = cbCustomerType.getValue().toString();
            if (Utilities.isNotSelected(customerType) ||
                    Utilities.isTooLong(customerType, CUSTOMER_TYPE_FIELD_MAX_SIZE)) {
                throw new Exception();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Customer Type");
            alert.setContentText("The field 'Customer Type' must be selected " +
                    "and must not be longer than 10 characters.");
            alert.showAndWait();
            return false;
        }
        return true;
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
            refreshCustomerPaginationNumbers();
            nextCustomerSaveAction = null;
            displaySavedCustomerAlert();
        }
    }

    private Customer makeNewCustomerObjectfromUI() {
        customerID = tfCustomerID.getText();
        customerFirstName = tfFirstName.getText();
        customerLastName = tfLastName.getText();
        customerContactNumber = tfContactNumber.getText();
        customerContactNumber = Utilities.removeBlankSpaces(customerContactNumber);
        customerEmail = tfEmail.getText();
        customerAddress = taAddress.getText();
        customerProduct = cbProduct.getValue().toString();
        customerType = cbCustomerType.getValue().toString();
        Customer newCustomer = getNewCustomerObject();
        return newCustomer;
    }

    private Customer getNewCustomerObject() {
        return new Customer(
                customerID,
                customerFirstName,
                customerLastName,
                customerContactNumber,
                customerEmail,
                customerAddress,
                customerProduct,
                customerType);
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
                    String.format("INSERT INTO customers (custID, fName, lName, mobile, email, addr, custType, product) " +
                                    "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                            customerID, customerFirstName, customerLastName, customerContactNumber,
                            customerEmail, customerAddress, customerType, customerProduct)
            );
            int rowsInserted = insertNewCustomerStatement.executeUpdate();
            if (rowsInserted > 0) {
                addedToDatabase = true;
            } else {
                displayUnsuccessfulRecordSaveAlert();
            }
            insertNewCustomerStatement.close();
            connection.close();
        } catch (Exception e) {
            displayDatabaseConnectionErrorAlert();
        }
        return addedToDatabase;
    }

    private void displayCustomerRecord(int index) {
        Customer customer;
        if (customerSet.equals(DataSet.FULL_SET)) {
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
            alert.setContentText("The customer record you are trying to save " +
                    "has no changes.");
            alert.showAndWait();
        } else {
            if (alterCustomerInDatabase(editedCustomer)) {
                customersList.set(indexOfEditedCustomer, editedCustomer);
                disableAllCustomerFields();
                nextCustomerSaveAction = null;
                displaySavedCustomerAlert();
            }
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
                            "UPDATE customers SET fname = '%s', lName = '%s', mobile = '%s', " +
                                    "email = '%s', addr = '%s', custType = '%s', product = '%s' " +
                                    "WHERE custID = %s;",
                            customerFirstName, customerLastName, customerContactNumber,
                            customerEmail, customerAddress, customerType,
                            customerProduct, customerID));
            int rowsDeleted = editCustomerStatement.executeUpdate();
            if (rowsDeleted > 0) {
                editedInDatabase = true;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Customer not edited in database.");
                alert.setContentText("The customer record cannot be edited in the database.");
                alert.showAndWait();
            }
            editCustomerStatement.close();
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
    /**
     * Handles the search button click event for the customer search. The search
     * is can then be performed based on the customer's last name and contact
     * number. The search enables the fields for the customer's last name and
     * contact number to be filled in. The customer's first name, email, address,
     * product and type are not editable. The customer set is set to the
     * search set.
     */
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
        customerSet = DataSet.SEARCH_SET;
    }

    // View All Customer Button Handlers
    /**
     * Handles the view all button click event for the customer section. The
     * customer set is set to the full set. The customer's first name, last name,
     * contact number, email, address, product and type are disabled. The
     * method then displays the first customer record in the customer list.
     * The pagination fields are reset.
     */
    @FXML
    public void viewAllCustomersButtonClick() {
        customerSet = DataSet.FULL_SET;
        disableAllCustomerFields();
        if (customersList.size() > 0) {
            currentCustomer = 0;
            numberOfCustomers = customersList.size();
            displayCustomerRecord(currentCustomer);
            refreshCustomerPaginationNumbers();
        }
    }

    // Clear Customer Button Handlers
    /**
     * Handles the clear button click event for the customer section. The
     * method clears all the editable customer fields. This function is only
     * used when customer records are being added or edited.
     */
    @FXML
    public void clearAllCustomerFieldsButtonClick() {
        if (tfCustomerID.isEditable()) {
            clearAllCustomersFields();
        }
    }

    // Customer Last Name Search Button Handlers
    /**
     * Method handles the customer last name search button click event. The
     * method searches the customer list for the customer's last name. The
     * customer's first name, email, address, product and type are not
     * editable. The method checks if the Search button was clicked before
     * searching the customer list. If the Search button was not clicked, the
     * method displays an alert to the user. If the customer's last name is
     * found in the customer list, the method displays the matching customer
     * records.
     */
    @FXML
    public void customerSearchLastNameButtonClick() {
        if (customerSet.equals(DataSet.FULL_SET)) {
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
            refreshCustomerPaginationNumbers();
        }
    }

    // Customer Contact Number Search Button Handlers

    /**
     * Method handles the customer contact number search button click event. The
     * method searches the customer list for the customer's contact number. The
     * customer's first name, email, address, product and type are not
     * editable. The method checks if the Search button was clicked before
     * searching the customer list. If the Search button was not clicked, the
     * method displays an alert to the user. If the customer's contact number is
     * found in the customer list, the method displays the matching customer
     * records.
     */
    @FXML
    public void customerSearchContactNumberButtonClick() {
        if (customerSet.equals(DataSet.FULL_SET)) {
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
            refreshCustomerPaginationNumbers();
        }
    }

    private void displayIncorrectSearchAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Search not allowed in View mode.");
        alert.setContentText("Searching for a customer or complaint is now allowed " +
                "in View mode. Please click the 'Customer Search' or 'Complaint " +
                "Search' button first, to start a search.");
        alert.showAndWait();
    }

    // Add Customer Complaint Button Handlers

    /**
     * Method handles the add customer complaint button click event. The
     * method clears all the editable complaint fields. The complaint customer
     * ID field is set to the current customer's ID. The complaint ID field is
     * set to the next available ID.
     */
    @FXML
    public void addCustomerComplaintButtonClick() {
        newComplaintButtonClick();
        tfComplaintsCustomerID.setText(tfCustomerID.getText());
    }

    // Previous Customer Button Handlers
    /**
     * Handles the previous button click event for the customer section. The
     * customer's first name, last name, contact number, email, address,
     * product and type are disabled. The method then displays the previous
     * customer record in the customer list. The pagination fields are reset.
     */
    @FXML
    public void previousCustomerButtonClick() {
        disableAllCustomerFields();
        if (currentCustomer == 0) {
            currentCustomer = numberOfCustomers - 1;
        } else {
            currentCustomer--;
        }
        displayCustomerRecord(currentCustomer);
        refreshCustomerPaginationNumbers();
    }

    // Next Customer Button Handlers
    /**
     * Handles the next button click event for the customer section. The
     * customer's first name, last name, contact number, email, address,
     * product and type are disabled. The method then displays the next
     * customer record in the customer list. The pagination fields are reset.
     */
    @FXML
    public void nextCustomerButtonClick() {
        disableAllCustomerFields();
        if (currentCustomer + 1 == numberOfCustomers) {
            currentCustomer = 0;
        } else {
            currentCustomer++;
        }
        displayCustomerRecord(currentCustomer);
        refreshCustomerPaginationNumbers();
    }

    /* =============================================================================
    COMPLAINTS
    ============================================================================= */
    // Complaints Section UI Controls
    @FXML
    private TextField tfComplaintID;
    @FXML
    private TextField tfComplaintsCustomerID;
    @FXML
    private DatePicker dpComplaintDate;
    @FXML
    private ComboBox cbServiceType;
    @FXML
    private ComboBox cbComplaintStatus;
    @FXML
    private TextArea taProblemDescription;
    @FXML
    private TextArea taServiceNotes;
    @FXML
    private TextField tfCurrentComplaint;
    @FXML
    private TextField tfTotalComplaint;

    // Complaints Section Variables
    private final ArrayList<Complaint> complaintsList = new ArrayList();
    private final ArrayList<Complaint> tempComplaintsList = new ArrayList();
    private String complaintID;
    private String complaintsCustomerID;
    private LocalDate complaintDate;
    private String complaintServiceType;
    private String complaintStatus;
    private String problemDescription;
    private String serviceNotes;
    private int currentComplaint;
    private int numberOfComplaints;
    private SaveAction nextComplaintSaveAction;
    private int nextComplaintID;
    private DataSet complaintSet;
    private Complaint iteratingComplaint;
    private final int SERVICE_TYPE_FIELD_MAX_SIZE = 10;
    private final int COMPLAINT_STATUS_FIELD_MAX_SIZE = 10;
    private final int PROBLEM_DESCRIPTION_FIELD_MAX_SIZE = 150;
    private final int SERVICE_NOTES_FIELD_MAX_SIZE = 150;

    // Complaint Helper Methods
    private void loadComplaintComboBoxOptions() {
        cbServiceType.getItems().addAll("Internet", "Phone", "Billing");
        cbComplaintStatus.getItems().addAll("Received", "Allocated", "Started", "Resolved", "Pending", "Cancelled");
    }

    private void enableAllComplaintsFields() {
        tfComplaintsCustomerID.setEditable(true);
        tfComplaintsCustomerID.setStyle("-fx-control-inner-background: #FFFFFF;");
        cbServiceType.setDisable(false);
        cbServiceType.setStyle("-fx-control-inner-background: #FFFFFF;");
        cbComplaintStatus.setDisable(false);
        cbComplaintStatus.setStyle("-fx-control-inner-background: #FFFFFF;");
        taProblemDescription.setEditable(true);
        taProblemDescription.setStyle("-fx-control-inner-background: #FFFFFF;");
        taServiceNotes.setEditable(true);
        taServiceNotes.setStyle("-fx-control-inner-background: #FFFFFF;");
    }

    private void disableAllComplaintsFields() {
        tfComplaintID.setEditable(false);
        tfComplaintID.setStyle("-fx-control-inner-background: #F1F1F1;");
        tfComplaintsCustomerID.setEditable(false);
        tfComplaintsCustomerID.setStyle("-fx-control-inner-background: #F1F1F1;");
        dpComplaintDate.setEditable(false);
        dpComplaintDate.setStyle("-fx-control-inner-background: #F1F1F1;");
        cbServiceType.setDisable(true);
        cbServiceType.setStyle("-fx-control-inner-background: #F1F1F1;");
        cbComplaintStatus.setDisable(true);
        cbComplaintStatus.setStyle("-fx-control-inner-background: #F1F1F1;");
        taProblemDescription.setEditable(false);
        taProblemDescription.setStyle("-fx-control-inner-background: #F1F1F1;");
        taServiceNotes.setEditable(false);
        taServiceNotes.setStyle("-fx-control-inner-background: #F1F1F1;");
        tfCurrentComplaint.setEditable(false);
        tfCurrentComplaint.setStyle("-fx-control-inner-background: #F1F1F1;");
        tfTotalComplaint.setEditable(false);
        tfTotalComplaint.setStyle("-fx-control-inner-background: #F1F1F1;");
    }

    private void loadComplaintsRecords() {
        try (Connection connection = DatabaseHandler.getConnection()) {
            PreparedStatement getAllComplaintsStatement = connection.prepareStatement(
                    "SELECT * FROM complaints ORDER BY compID;"
            );
            ResultSet getAllComplaintsQueryResults = getAllComplaintsStatement.executeQuery();
            while (getAllComplaintsQueryResults.next()) {
                complaintID = getAllComplaintsQueryResults.getString("compID");
                complaintsCustomerID = getAllComplaintsQueryResults.getString("custID");
                String date = getAllComplaintsQueryResults.getString("compDate");
                complaintDate = LocalDate.parse(date);
                complaintServiceType = getAllComplaintsQueryResults.getString("serviceType");
                complaintStatus = getAllComplaintsQueryResults.getString("compStatus");
                problemDescription = getAllComplaintsQueryResults.getString("problemDesc");
                serviceNotes = getAllComplaintsQueryResults.getString("serviceNotes");
                Complaint newComplaint = getNewComplaintObject();
                complaintsList.add(newComplaint);
            }
            getAllComplaintsStatement.close();
            getAllComplaintsQueryResults.close();
            connection.close();
        } catch (Exception e) {
            displayDatabaseConnectionErrorAlert();
        }
    }

    private void refreshComplaintPaginationNumbers() {
        tfCurrentComplaint.setText(currentComplaint + 1 + "");
        tfTotalComplaint.setText(numberOfComplaints + "");
    }

    // New Complaint Button Handlers
    /**
     * This method handles the new complaint button click event. It clears all
     * the fields and enables them for the user to enter new complaint details.
     * It also sets the next complaint save action to NEW.
     */
    @FXML
    public void newComplaintButtonClick() {
        nextComplaintSaveAction = SaveAction.NEW;
        clearAllComplaintFields();
        enableAllComplaintsFields();
        tfCurrentComplaint.setText(numberOfComplaints + 1 + "");
        tfTotalComplaint.setText(numberOfComplaints + 1 + "");
    }

    private void clearAllComplaintFields() {
        tfComplaintID.setText(getNextComplaintID() + "");
        tfComplaintsCustomerID.clear();
        dpComplaintDate.getEditor().setText("");
        cbServiceType.getSelectionModel().clearSelection();
        cbComplaintStatus.getSelectionModel().clearSelection();
        taProblemDescription.clear();
        taServiceNotes.clear();
    }

    private int getNextComplaintID() {
        nextComplaintID = 0;
        for (Complaint complaint : complaintsList) {
            if (Integer.parseInt(complaint.getComplaintID()) > nextComplaintID) {
                nextComplaintID = Integer.parseInt(complaint.getComplaintID());
            }
        }
        return (nextComplaintID + 1);
    }

    // Edit Complaint Button Handlers
    /**
     * This method handles the edit complaint button click event. It enables
     * all the fields for the user to edit the complaint details. It also sets
     * the next complaint save action to EDIT. An alert is displayed if the
     * complaint set is in search mode.
     */
    @FXML
    public void editComplaintButtonClick() {
        if (complaintSet.equals(DataSet.FULL_SET)) {
            nextComplaintSaveAction = SaveAction.EDIT;
            enableAllComplaintsFields();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cannot edit in Search");
            alert.setContentText("Complaint edit is not allowed whilst in Search mode. " +
                    "Please click the 'View All' button to return to View mode, then " +
                    "try again.");
            alert.showAndWait();
        }
    }

    // Save Complaint Button Handlers
    /**
     * This method handles the save complaint button click event. It validates
     * all the fields and saves the new complaint details to the database if
     * all the fields are valid. It also saves the edited complaint details to
     * the database if all the fields are valid. An alert is displayed if the
     * complaint set is in search mode.
     */
    @FXML
    public void saveComplaintButtonClick() {
        if (tfComplaintsCustomerID.isEditable()) {
            if (validateAllComplaintFieldsInput()) {
                if (nextComplaintSaveAction.equals(SaveAction.NEW)) {
                    saveNewComplaint();
                } else {
                    saveEditedComplaint();
                }
            } else {
                displayInvalidFieldsAlert();
            }
        }
    }

    private boolean validateAllComplaintFieldsInput() {
        boolean allComplaintFieldsAreValid = false;
        if (
                validateComplaintCustomerID() &&
                        validateComplaintDate() &&
                        validateServiceType() &&
                        validateComplaintStatus() &&
                        validateProblemDescription() &&
                        validateServiceNotes()
        ) {
            allComplaintFieldsAreValid = true;
        }
        return allComplaintFieldsAreValid;
    }

    private boolean validateComplaintCustomerID() {
        String complaintCustomerID = tfComplaintsCustomerID.getText();
        if (Utilities.inexistentCustomerID(complaintCustomerID, customersList)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Customer ID");
            alert.setContentText("The 'Customer ID' provided does not exist.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateComplaintDate() {
        LocalDate dateInput = dpComplaintDate.getValue();
        if (Utilities.isEmptyInput(dateInput)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Complaint Date");
            alert.setContentText("The 'Complaint Date' must not be empty.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateServiceType() {
        try {
            String serviceType = cbServiceType.getValue().toString();
            if (Utilities.isNotSelected(serviceType) ||
                    Utilities.isTooLong(serviceType, SERVICE_TYPE_FIELD_MAX_SIZE)) {
                throw new Exception();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Service Type");
            alert.setContentText("The field 'Service Type' must be selected " +
                    "and must not be longer than 10 characters.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateComplaintStatus() {
        try {
            String complaintStatus = cbComplaintStatus.getValue().toString();
            if (Utilities.isNotSelected(complaintStatus) ||
                    Utilities.isTooLong(complaintStatus, COMPLAINT_STATUS_FIELD_MAX_SIZE)) {
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Complaint Status");
            alert.setContentText("The field 'Complaint Status' must be selected " +
                    "and must not be longer than 10 characters.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateProblemDescription() {
        String problemDescription = taProblemDescription.getText();
        if (Utilities.isTooLong(problemDescription,
                PROBLEM_DESCRIPTION_FIELD_MAX_SIZE) ||
                Utilities.isEmptyInput(problemDescription)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Problem Description");
            alert.setContentText("The field 'Problem Description' must be filled in " +
                    "and must not be longer than 150 characters.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateServiceNotes(){
        String serviceNotes = taServiceNotes.getText();
        if (Utilities.isTooLong(serviceNotes,
                SERVICE_NOTES_FIELD_MAX_SIZE) ||
                Utilities.isEmptyInput(serviceNotes)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Service Notes");
            alert.setContentText("The field 'Service Notes' must be filled in " +
                    "and must not be longer than 150 characters.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void saveNewComplaint() {
        Complaint newComplaint = makeNewComplaintObjectfromUI();
        boolean addedToDatabase = addComplaintToDatabase(newComplaint);
        if (addedToDatabase) {
            disableAllComplaintsFields();
            complaintsList.clear();
            loadComplaintsRecords();
            int indexOfNewComplaint = -1;
            for (int i = 0; i < complaintsList.size(); i++) {
                if (complaintsList.get(i).getComplaintID().equals(newComplaint.getComplaintID())) {
                    indexOfNewComplaint = i;
                    break;
                }
            }
            currentComplaint = indexOfNewComplaint;
            displayComplaintRecord(currentComplaint);
            numberOfComplaints = complaintsList.size();
            refreshComplaintPaginationNumbers();
            nextComplaintSaveAction = null;
            displaySavedComplaintAlert();
        }
    }

    private Complaint makeNewComplaintObjectfromUI() {
        complaintID = tfComplaintID.getText();
        complaintsCustomerID = tfComplaintsCustomerID.getText();
        complaintDate = dpComplaintDate.getValue();
        complaintServiceType = cbServiceType.getValue().toString();
        complaintStatus = cbComplaintStatus.getValue().toString();
        problemDescription = taProblemDescription.getText();
        serviceNotes = taServiceNotes.getText();
        Complaint newComplaint = getNewComplaintObject();
        return newComplaint;
    }

    private Complaint getNewComplaintObject() {
        return new Complaint(
                complaintID,
                complaintsCustomerID,
                complaintDate,
                complaintServiceType,
                complaintStatus,
                problemDescription,
                serviceNotes
        );
    }

    private boolean addComplaintToDatabase(Complaint newComplaint) {
        boolean addedToDatabase = false;
        complaintID = newComplaint.getComplaintID();
        complaintsCustomerID = newComplaint.getComplaintsCustomerID();
        complaintDate = newComplaint.getComplaintDate();
        complaintServiceType = newComplaint.getComplaintServiceType();
        complaintStatus = newComplaint.getComplaintStatus();
        problemDescription = newComplaint.getProblemDescription();
        serviceNotes = newComplaint.getServiceNotes();
        try (Connection connection = DatabaseHandler.getConnection()) {
            PreparedStatement insertNewComplaintStatement = connection.prepareStatement(
                    String.format("INSERT INTO complaints (compID, custID, serviceType, problemDesc, compDate, serviceNotes, compStatus) " +
                                    "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                            complaintID, complaintsCustomerID, complaintServiceType, problemDescription,
                            complaintDate, serviceNotes, complaintStatus)
            );
            int rowsInserted = insertNewComplaintStatement.executeUpdate();
            if (rowsInserted > 0) {
                addedToDatabase = true;
            } else {
                displayUnsuccessfulRecordSaveAlert();
            }
            insertNewComplaintStatement.close();
            connection.close();
        } catch (Exception e) {
            displayUnsuccessfulRecordSaveAlert();
        }
        return addedToDatabase;
    }

    private void displayComplaintRecord(int index) {
        Complaint complaint;
        if (complaintSet.equals(DataSet.FULL_SET)) {
            complaint = complaintsList.get(index);
        } else {
            complaint = tempComplaintsList.get(index);
        }
        tfComplaintID.setText(complaint.getComplaintID());
        tfComplaintsCustomerID.setText(complaint.getComplaintsCustomerID());
        dpComplaintDate.setValue(complaint.getComplaintDate());
        cbServiceType.setValue(complaint.getComplaintServiceType());
        cbServiceType.setStyle("-fx-opacity: 1.0");
        cbComplaintStatus.setValue(complaint.getComplaintStatus());
        cbComplaintStatus.setStyle("-fx-opacity: 1.0");
        taProblemDescription.setText(complaint.getProblemDescription());
        taServiceNotes.setText(complaint.getServiceNotes());
    }

    private void displaySavedComplaintAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Complaint saved");
        alert.setContentText("Complaint record saved successfully.");
        alert.showAndWait();
    }

    private void saveEditedComplaint() {
        int indexOfEditedComplaint = Integer.parseInt(tfCurrentComplaint.getText()) - 1;
        Complaint originalComplaint = complaintsList.get(indexOfEditedComplaint);
        Complaint editedComplaint = makeNewComplaintObjectfromUI();
        if (editedComplaint.equals(originalComplaint)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No changes to Save");
            alert.setContentText("The complaint record you are trying to save has no changes.");
            alert.showAndWait();
        } else {
            if (alterComplaintInDatabase(editedComplaint)) {
                complaintsList.set(indexOfEditedComplaint, editedComplaint);
                disableAllComplaintsFields();
                nextComplaintSaveAction = null;
                displaySavedComplaintAlert();
            }
        }
    }

    private boolean alterComplaintInDatabase(Complaint editedComplaint) {
        boolean editedInDatabase = false;
        complaintID = editedComplaint.getComplaintID();
        complaintsCustomerID = editedComplaint.getComplaintsCustomerID();
        complaintDate = editedComplaint.getComplaintDate();
        complaintServiceType = editedComplaint.getComplaintServiceType();
        complaintStatus = editedComplaint.getComplaintStatus();
        problemDescription = editedComplaint.getProblemDescription();
        serviceNotes = editedComplaint.getServiceNotes();
        try (Connection connection = DatabaseHandler.getConnection()) {
            PreparedStatement editComplaintStatement = connection.prepareStatement(
                    String.format(
                            "UPDATE complaints SET compID = '%s', custID = '%s', serviceType = '%s', problemDesc = '%s', compDate = '%s', serviceNotes = '%s', compStatus = '%s' WHERE compID = %s;",
                            complaintID, complaintsCustomerID, complaintServiceType, problemDescription, complaintDate, serviceNotes, complaintStatus, complaintID)
            );
            int rowsDeleted = editComplaintStatement.executeUpdate();
            if (rowsDeleted > 0) {
                editedInDatabase = true;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Complaint not edited in database.");
                alert.setContentText("The complaint record cannot be edited in the database.");
                alert.showAndWait();
            }
            editComplaintStatement.close();
            connection.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Complaint not edited in database.");
            alert.setContentText("Unable to connect to the database. Complaint record not edited in database.");
            alert.showAndWait();
        }
        return editedInDatabase;
    }

    // Search Complaint Button Handlers
    /**
     * Handles the Search Complaint button click event. Sets the complaintID,
     * and CustomerID fields to editable and clears the fields. Sets these
     * field to editable and clears them. Sets the complaint set to SEARCH_SET.
     */
    @FXML
    public void complaintSearchButtonClick() {
        tfComplaintID.clear();
        tfComplaintID.setEditable(true);
        tfComplaintID.setStyle("-fx-control-inner-background: #FFFFFF;");
        tfComplaintsCustomerID.clear();
        tfComplaintsCustomerID.setEditable(true);
        tfComplaintsCustomerID.setStyle("-fx-control-inner-background: #FFFFFF;");
        dpComplaintDate.getEditor().setText("");
        dpComplaintDate.setEditable(false);
        dpComplaintDate.setStyle("-fx-control-inner-background: #F1F1F1;");
        cbServiceType.setValue(null);
        cbServiceType.setDisable(true);
        cbServiceType.setStyle("-fx-control-inner-background: #F1F1F1;");
        cbComplaintStatus.setValue(null);
        cbComplaintStatus.setDisable(true);
        cbComplaintStatus.setStyle("-fx-control-inner-background: #F1F1F1;");
        taProblemDescription.clear();
        taProblemDescription.setEditable(false);
        taProblemDescription.setStyle("-fx-control-inner-background: #F1F1F1;");
        taServiceNotes.clear();
        taServiceNotes.setEditable(false);
        taServiceNotes.setStyle("-fx-control-inner-background: #F1F1F1;");
        complaintSet = DataSet.SEARCH_SET;
    }

    // Complaint ID Search Button Handlers
    /**
     * Handles the Complaint ID Search button click event. Searches the
     * complaints list for a complaint with a matching complaint ID. All
     * matching complaints are then displayed.
     */
    @FXML
    public void complaintSearchIDButtonClick() {
        if (complaintSet.equals(DataSet.FULL_SET)) {
            displayIncorrectSearchAlert();
            return;
        }
        boolean matchFound = false;
        tempComplaintsList.clear();
        String complaintIDInput = tfComplaintID.getText();
        String iteratingComplaintID;
        for (int i = 0; i < complaintsList.size(); i++) {
            iteratingComplaint = complaintsList.get(i);
            iteratingComplaintID = iteratingComplaint.getComplaintID();
            if (iteratingComplaintID.contains(complaintIDInput)) {
                tempComplaintsList.add(iteratingComplaint);
                matchFound = true;
            }
        }
        if (matchFound) {
            disableAllComplaintsFields();
            currentComplaint = 0;
            numberOfComplaints = tempComplaintsList.size();
            displayComplaintRecord(currentComplaint);
            refreshComplaintPaginationNumbers();
        }
    }

    // Complaint ID Search Button Handlers
    /**
     * Handles the Complaint Customer ID Search button click event. Searches the
     * complaints list for complaints with matching customer IDs. All matching
     * complaints are then displayed.
     */
    @FXML
    public void complaintSearchCustomerIDButtonClick() {
        if (complaintSet.equals(DataSet.FULL_SET)) {
            displayIncorrectSearchAlert();
            return;
        }
        boolean matchFound = false;
        tempComplaintsList.clear();
        String complaintCustomerIDInput = tfComplaintsCustomerID.getText();
        String iteratingComplaintCustomerID;
        for (int i = 0; i < complaintsList.size(); i++) {
            iteratingComplaint = complaintsList.get(i);
            iteratingComplaintCustomerID = iteratingComplaint.getComplaintsCustomerID();
            if (iteratingComplaintCustomerID.contains(complaintCustomerIDInput)) {
                tempComplaintsList.add(iteratingComplaint);
                matchFound = true;
            }
        }
        if (matchFound) {
            disableAllComplaintsFields();
            currentComplaint = 0;
            numberOfComplaints = tempComplaintsList.size();
            displayComplaintRecord(currentComplaint);
            refreshComplaintPaginationNumbers();
        }
    }

    // View All Complaint Button Handlers
    /**
     * Handles the View All Complaints button click event. Sets the complaint
     * set to FULL_SET and displays all complaints. The method then disables all
     * complaint fields and displays the first complaint record in the
     * complaints list. The pagination numbers are then refreshed.
     */
    @FXML
    public void viewAllComplaintsButtonClick() {
        complaintSet = DataSet.FULL_SET;
        disableAllComplaintsFields();
        if (complaintsList.size() > 0) {
            currentComplaint = 0;
            numberOfComplaints = complaintsList.size();
            displayComplaintRecord(currentComplaint);
            refreshComplaintPaginationNumbers();
        }
    }

    // Clear Complaint Button Handlers
    /**
     * Handles the Clear Complaint button click event. Clears all complaint
     * fields and disables all editable complaint fields except the complaint ID
     * and customer ID fields. This function is only used when complaint
     * records are being added or edited.
     */
    @FXML
    public void clearAllComplaintFieldsButtonClick() {
        if (tfComplaintsCustomerID.isEditable()) {
            clearAllComplaintFields();
        }
    }

    // Previous Complaint Button Handlers
    /**
     * Handles the Previous Complaint button click event. Disables all complaint
     * fields and displays the previous complaint record in the complaints list.
     * The pagination numbers are then refreshed.
     */
    @FXML
    public void previousComplaintButtonClick() {
        disableAllComplaintsFields();
        if (currentComplaint == 0) {
            currentComplaint = numberOfComplaints - 1;
        } else {
            currentComplaint--;
        }
        displayComplaintRecord(currentComplaint);
        refreshComplaintPaginationNumbers();
    }

    // Next Complaint Button Handlers
    /**
     * Handles the Next Complaint button click event. Disables all complaint
     * fields and displays the next complaint record in the complaints list.
     * The pagination numbers are then refreshed.
     */
    @FXML
    public void nextComplaintButtonClick() {
        disableAllComplaintsFields();
        if (currentComplaint + 1 == numberOfComplaints) {
            currentComplaint = 0;
        } else {
            currentComplaint++;
        }
        displayComplaintRecord(currentComplaint);
        refreshComplaintPaginationNumbers();
    }

    /* =============================================================================
    REPORT
    ============================================================================= */
    // Report Section UI Controls
    @FXML
    private TextArea taReportContent;

    // Report Section Variables
    private String year;
    private String week;
    private String weekStart;
    private String complaintCount;

    // Report Section Helper Methods
    private void disableReportField() {
        taReportContent.setEditable(false);
        taReportContent.setStyle("-fx-control-inner-background: #F1F1F1;");
    }

    /**
     * Displays the report string. The report data is built from a database
     * query extracting the data from the complaints table and consolidating
     * into weekly complaint totals. The report string is built from the query
     * results and displayed in the report text area. The report text area is
     * disabled. An alert is displayed if an error occurs.
     */
    @FXML
    public void generateReportButtonClick() {
        String reportPrint = "";
        try (Connection connection = DatabaseHandler.getConnection()) {
            PreparedStatement getReportDataStatement = connection.prepareStatement(
                    "SELECT YEAR(compDate) AS year, " +
                            "WEEK(compDate) AS week, " +
                            "DATE_FORMAT(STR_TO_DATE(CONCAT(YEARWEEK(compDate), ' Monday'), '%X%V %W'), '%W %d/%m/%Y') AS weekStart,\n" +
                            "COUNT(*) AS complaintCount " +
                            "FROM complaints " +
                            "GROUP BY weekStart " +
                            "ORDER BY year DESC, week DESC;"
            );
            ResultSet getReportDataQueryResults = getReportDataStatement.executeQuery();
            reportPrint += String.format("%-10s %-10s %-30s %-15s\n", "Year",
                    "Week", "Week Start", "Complaints");
            reportPrint += String.format("%-10s %-10s %-30s %-15s\n", "==========",
                    "==========", "==============================", "===============");
            while (getReportDataQueryResults.next()) {
                year = getReportDataQueryResults.getString("year");
                week = getReportDataQueryResults.getString("week");
                weekStart = getReportDataQueryResults.getString("weekStart");
                complaintCount = getReportDataQueryResults.getString("complaintCount");
                reportPrint += String.format("%-10s %-10s %-30s %-15s\n", year,
                        week, weekStart, complaintCount);
            }
            reportPrint += String.format("%-10s %-10s %-30s %-15s\n", "==========",
                    "==========", "==============================", "===============");
            reportPrint += String.format("%-10s %-10s %-30s %-15s\n", "",
                    "", "TOTAL", complaintsList.size());
            getReportDataStatement.close();
            getReportDataQueryResults.close();
            connection.close();
        } catch (Exception e) {
            displayReportUnsuccessfulAlert();
        }
        taReportContent.setText(reportPrint);
    }

    private void displayReportUnsuccessfulAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Report failed");
        alert.setContentText("Connection to the Complaints database failed. Unable to load " +
                "Complaint statistics from database.");
        alert.showAndWait();
    }
}
