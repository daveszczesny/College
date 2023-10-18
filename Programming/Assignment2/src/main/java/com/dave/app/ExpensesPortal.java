/*
 * 
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package main.java.com.dave.app;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class ExpensesPortal {

    // List for expenses
    private List<Expense> expenses;

    private static final float EXCHANGE_RATE_USD_EUR = 0.95f;

    /**
     * 
     * @param expenses
     * @return total
     */
    public static Money sumExpenses(List<Expense> expenses) {
        Money total = Money.zero(CurrencyUnit.EUR); // Creates a Money variable set to 0 in euro

        /*
         * Loops through all expenses and performs a tenary operation
         * If the currency unit of the current expense is in euro we simply add
         * it to our total
         * If the currency unit of the current expense is in USD we convert it using the
         * current exchange between EUR and USD which is set to EXCHANGE_RATE_USD_EUR
         */
        for (Expense e : expenses) {
            total = e.getAmount().getCurrencyUnit() == CurrencyUnit.EUR ? total.plus(e.getAmount())
                    : total.plus(e.getAmount().convertedTo(CurrencyUnit.EUR, new BigDecimal(EXCHANGE_RATE_USD_EUR),
                            RoundingMode.CEILING));
        }

        // Returns total amount
        return total;
    }

    // Constructor initializing expenses list
    public ExpensesPortal() {
        expenses = new ArrayList<>();
    }

    /**
     * Submit expenses to portal
     * 
     * @param expense
     */
    public void submitExpense(Expense... expense) {
        for (Expense e : expense) {
            expenses.add(e);
        }
    }

    /**
     * Method to approve a certain expense
     * 
     * @param expense
     */
    public void approveExpense(Expense expense) {
        expense.approveExpense();
    }

    /**
     * Prints expenses
     * 
     * @param printer
     */
    public void printExpenses(ExpensePrinter printer) {
        printer.print(expenses);
    }

    // Returns list of expenses
    /**
     * @return expenses
     */
    public List<Expense> getExpenses() {
        return this.expenses;
    }

}
