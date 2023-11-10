/*
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

 /* Exception thrown when trying to retrieve an account that doesn't exist in the banks collection */

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String msg){
        super(msg);
    }
}
