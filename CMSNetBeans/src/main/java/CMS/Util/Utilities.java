package CMS.Util;

import CMS.Model.Customer;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Daniel Barros
 */
public class Utilities {
    
    public static String removeBlankSpaces (String input) {
        if (input == null) {
            return "";
        } else {
            String trimmedInput = input.trim();
            trimmedInput = trimmedInput.replace(" ", "");
            return trimmedInput;
        }
    }
    
    public static boolean isEmptyInput(String inputString) {
        if (
                inputString.isEmpty() || 
                inputString.isBlank() ||
                inputString == null) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isEmptyInput(LocalDate inputDate) {
        if (inputDate == null) {
            return true;
        } 
        return false;
    }
    
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

    public static boolean customerIDAlreadyExists(String inputString, 
            ArrayList<Customer> customersList) {
        for (Customer customer : customersList) {
            if (customer.getCustomerID().equals(inputString)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isTooLong(String inputString, int maxFieldSize) {
        if (inputString.length() > maxFieldSize) {
            return true;
        }
        return false;
    }
    
    public static boolean hasNoAt(String inputString) {
        if (!inputString.contains("@")) {
            return true;
        }
        return false;
    }
    
    public static boolean isNotSelected(String inputString) {
        return isEmptyInput(inputString);
    }
    
    public static boolean inexistentCustomerID(String inputString, 
            ArrayList<Customer> customersList) {
        return !customerIDAlreadyExists(inputString, customersList);
    }
    
}
