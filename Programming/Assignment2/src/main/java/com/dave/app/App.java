/*
 * 
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */


package main.java.com.dave.app;

import java.time.LocalDate;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import main.java.com.dave.exceptions.IllegalDateException;

public class App {


    public static void main(String[] args) throws IllegalDateException{

        // Instance of ExpensesPortal
        ExpensesPortal portal = new ExpensesPortal();
        
        // Sample expenses
        Expense expense1 = new Expense(LocalDate.now(), "Monitor 16\"", ExpenseType.EQUIPMENT, Money.parse("USD 200"));
        Expense expense2 = new Expense(LocalDate.of(2023, 9, 30), "Cables", ExpenseType.EQUIPMENT,
                Money.parse("EUR 20"));

        Expense expense3 = new Expense(LocalDate.now(), "Taxi to work", ExpenseType.TRAVEL_AND_SUBSISTENCE,
                Money.parse("EUR 30"));
        Expense expense4 = new Expense(LocalDate.now(), "Coffee w/ clients", ExpenseType.ENTERTAINMENT,
                Money.parse("USD 15"));

        Expense expense5 = new Expense(LocalDate.now(), "Machine gun", ExpenseType.OTHER, Money.parse("USD 120"));

        Expense expense6 = new Expense(LocalDate.now(), "Can of Monster", ExpenseType.OTHER, Money.parse("EUR 2.60"));
        Expense expense7 = new Expense(LocalDate.now(), "Paper for printer", ExpenseType.SUPPLIES, Money.parse("USD 12"));

        // Add expenses to portal
        portal.submitExpense(expense1, expense2, expense3, expense4, expense5, expense6, expense7);

        // lambda print expenses
        portal.printExpenses(expenses -> {
            for(Expense e : expenses){
                System.out.println(e);
            }
        });

        System.out.println(""); // new line

        // Print sum total 
        portal.printExpenses(new ExpensePrinter() {
            @Override
            public void print(List<Expense> expenses) {
                Money totalInEur = ExpensesPortal.sumExpenses(expenses);
                System.out.printf("There are %d expenses in the system totalling to a value of %s %.2f\n",
                        expenses.size(),
                        CurrencyUnit.EUR.toString(), totalInEur.getAmount());
            }
        });

        System.out.println(""); // new line

        // Print by label
        portal.printExpenses(new PrinterByLabel());




    }

}
