/*
 * 
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package test.java.com.dave.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.dave.app.Expense;
import main.java.com.dave.app.ExpensePrinter;

public class PrinterByLabelTest implements ExpensePrinter {
    

    Map<String, List<Expense>> categories = new HashMap<>();

    /**
     * 
     * Groups categories together
     * 
     * @param expenses
     */
    @Override
    public void print(List<Expense> expenses){
        

        for(Expense e : expenses){
            categories.computeIfAbsent(e.getExpenseType(), k -> new ArrayList<>());
        }

    }


    /**
     * 
     * @return categories
     */
    public Map<String, List<Expense>> getCategories(){
        return this.categories;
    }

}
