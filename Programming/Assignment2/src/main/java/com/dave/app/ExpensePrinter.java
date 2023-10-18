/*
 * 
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package main.java.com.dave.app;
import java.util.List;

// Interface for printing expenses
public interface ExpensePrinter {

    /**
     * 
     * @param expenses
     */
    public void print(List<Expense> expenses);
}
