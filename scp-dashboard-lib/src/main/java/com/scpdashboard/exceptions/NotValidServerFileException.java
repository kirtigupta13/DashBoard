package com.scpdashboard.exceptions;

/**
 * Custom exception which is thrown when the Parser realizes that the given file does not have valid information to parse
 * 
 * @author SD049814
 *
 */

public class NotValidServerFileException extends Exception {

    public NotValidServerFileException() {
        super("The given file does not contain proper valid server Information");
    }

}
