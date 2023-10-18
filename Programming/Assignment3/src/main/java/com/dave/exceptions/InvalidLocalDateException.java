/*
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package src.main.java.com.dave.exceptions;


/*
 * InvalidLocalDateExceiption thrown when an invalid date is provided 
 */
public class InvalidLocalDateException extends Throwable{
    public InvalidLocalDateException(String message)
    {
        super(message);
    }
}
