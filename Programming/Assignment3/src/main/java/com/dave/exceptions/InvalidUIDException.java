/*
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package src.main.java.com.dave.exceptions;


/*
 * InvalidUIDException is thrown when an achievement is given an invalid player UID
 *  This would result in an achivement being unable to be retrieved after serialization
 */
public class InvalidUIDException extends Throwable {
    public InvalidUIDException(String message)
    {
        super(message);
    }
}
