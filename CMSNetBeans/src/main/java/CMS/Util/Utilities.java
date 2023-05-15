/*
Central Queensland University
COIT12200 - Software Design & Development (2023 Term 1)
Campus: External
Assignment 2 - Complaints Management System
Student ID: 12184305
Student Name: Daniel Barros
*/
package CMS.Util;

import CMS.Model.Customer;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class contains methods that assist the UI Controller implementing the
 * application logic. The methods are static. The methods are used to
 * validate and format user input before it is passed to the Model classes.
 */
public class Utilities {
    /**
     * Removes blank spaces from the beginning, middle and the end of the input
     * string.
     * @param input
     * @return version of the input without blank spaces
     */
    public static String removeBlankSpaces (String input) {
        if (input == null) {
            return "";
        } else {
            String trimmedInput = input.trim();
            trimmedInput = trimmedInput.replace(" ", "");
            return trimmedInput;
        }
    }

    /**
     * Checks if the input string is empty, blank or null.
     * @param inputString
     * @return true if the input string is empty, blank or null
     */
    public static boolean isEmptyInput(String inputString) {
        if (inputString.isEmpty() || inputString.isBlank() ||
                        inputString == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the input string representing a date null.
     * @param inputDate
     * @return true if the input string representing a date is null.
     */
    public static boolean isEmptyInput(LocalDate inputDate) {
        if (inputDate == null) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the input string is not an integer number. It also checks if
     * the input string contains blank spaces or a dot.
     * @param inputString
     * @return true if the input string is not an integer number or if it
     * contains blank spaces or a dot.
     */
    public static boolean isNotIntegerInput(String inputString) {
        if (inputString.contains(" ") || inputString.contains(".")) {
            return true;
        }
        try {
            int parsedInputString = Integer.parseInt(inputString);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the input string is a number greater than the Java
     * Maximum Integer positive value.
     * @param inputString
     * @return true if the input string is a number greater than the Java
     * Maximum Integer positive value or if the input is not an integer number.
     */
    public static boolean isTooLarge(String inputString) {
        try {
            int input = Integer.parseInt(inputString);
            if (input > Integer.MAX_VALUE) {
                return true;
            }
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the input string entered as the Customer ID has already
     * been allocated to an existing Customer record. This method is used
     * when the user is creating a new Customer record. It is not used when
     * the user is updating an existing Customer record.
     * @param inputString
     * @param customersList
     * @return true if the input string entered as the Customer ID has already
     * been allocated to an existing Customer record.
     */
    public static boolean customerIDAlreadyExists(String inputString,
                                                  ArrayList<Customer> customersList) {
        for (Customer customer : customersList) {
            if (customer.getCustomerID().equals(inputString)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the input string is longer than the maximum length that the
     * corresponding MySQL database field allows.
     * @param inputString
     * @param maxFieldSize
     * @return true if the input string is longer than the maximum length that
     * the corresponding MySQL database field allows.
     */
    public static boolean isTooLong(String inputString, int maxFieldSize) {
        if (inputString.length() > maxFieldSize) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the input string contains the @ symbol. Used to validate the
     * email address field.
     * @param inputString
     * @return true if the input string does not contain the @ symbol.
     */
    public static boolean hasNoAt(String inputString) {
        if (!inputString.contains("@")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the input string, the value selected in a UI ComboBox, exists.
     * @param inputString
     * @return true if the input string does not exist, the corresponding UI
     * field is not selected.
     */
    public static boolean isNotSelected(String inputString) {
        return isEmptyInput(inputString);
    }

    /**
     * Checks if the input string, the value selected the UI Complaint field
     * 'Customer ID', exists. This method is used when the user is creating a
     * new Complaint record.
     * @param inputString
     * @param customersList
     * @return true if the input string does not exist.
     */
    public static boolean inexistentCustomerID(String inputString,
                                               ArrayList<Customer> customersList) {
        return !customerIDAlreadyExists(inputString, customersList);
    }
}
