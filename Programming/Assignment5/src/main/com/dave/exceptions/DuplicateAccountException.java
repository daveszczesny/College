
/*
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

 /* Exception thrown when duplicate account is attempted to be added to Bank */
public class DuplicateAccountException extends Throwable {
    public DuplicateAccountException(String msg) {
        super(msg);
    }
}
