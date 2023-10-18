/*
 * 
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */


package main.java.com.dave.exceptions;

/**
 * Exception class for Illegal date exception
 * Throw when a date is set in the future
 */

public class IllegalDateException extends Throwable{

    /**
     * @param message
     */
    public IllegalDateException(String message){
        super(message);
    }
}
