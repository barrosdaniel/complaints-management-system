/*
Central Queensland University
COIT12200 - Software Design & Development (2023 Term 1)
Campus: External
Assignment 2 - Complaints Management System
Student ID: 12184305
Student Name: Daniel Barros
*/
package CMS;

/**
 * Enumeration for the save action to be used in the application, when saving
 * a new or edited Customer or Complaint. Depending on this value, the controller
 * will action the appropriate method to save to the database, either adding
 * a new value or altering an existing database value.
 */
public enum SaveAction {
    NEW,
    EDIT
}
