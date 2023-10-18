/*
 * 
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package main.java.com.dave.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrinterByLabel implements ExpensePrinter {

    /**
     * Print method
     * 
     * @param expenses
     */
    @Override
    public void print(List<Expense> expenses) {

        Map<String, List<Expense>> categories = createCategories(expenses);

        // Loop through map key entries
        for (Map.Entry<String, List<Expense>> map : categories.entrySet()) {
            System.out.println(map.getKey()); // Print out Key as a title
            for (Expense e : map.getValue()) { // Print out values per key
                System.out.println(e);
            }
            System.out.println(""); // new line
        }

    }

    /**
     * 
     * @param expenses
     * @return mapped categories
     */
    private Map<String, List<Expense>> createCategories(List<Expense> expenses) {
        // Create hashmap for category type in String, and a list of expenses
        Map<String, List<Expense>> categories = new HashMap<>();

        // Loop through all the expenses and add to category map
        // Add category as key if absent
        // Add expense to the category list
        for (Expense e : expenses) {
            categories.computeIfAbsent(e.getExpenseType(), k -> new ArrayList<>()).add(e);
        }
        return categories;
    }

}
