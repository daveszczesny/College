/*
 * 
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package test.java.com.dave.app;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.joda.money.Money;
import org.junit.Test;

import main.java.com.dave.app.Expense;
import main.java.com.dave.app.ExpenseType;
import main.java.com.dave.app.ExpensesPortal;
import main.java.com.dave.exceptions.IllegalDateException;

public class AppTest {

    /*
     * Testing for the corrext declaration of an Expense
     */
    @Test
    public void testFor_CorrectExpenseDeclaration() {
        Expense expense;
        try {
            expense = new Expense(LocalDate.now(), "Business Trip", ExpenseType.TRAVEL_AND_SUBSISTENCE,
                    Money.parse("EUR 60"));

            assertTrue(expense.getLocalDate().equals(LocalDate.now()));
            assertTrue(expense.getDescription().equals("Business Trip"));
            assertTrue(expense.getExpenseType().equals(ExpenseType.TRAVEL_AND_SUBSISTENCE.type()));
            assertTrue(expense.getAmount().equals(Money.parse("EUR 60")));
            assertTrue(expense.isApproved() == false);
        } catch (IllegalDateException e) {
            e.printStackTrace();
        }

    }

    /**
     * Testing to ensure that an Expense is not set for a future date
     */
    @Test
    public void testFor_IllegalDateException() {
        assertThrows(IllegalDateException.class, () -> {
            new Expense(LocalDate.of(2024, 1, 10), "Business Trip", ExpenseType.TRAVEL_AND_SUBSISTENCE,
                    Money.parse("USD 60"));
        });
    }

    /*
     * Testing to ensure no null values are passed
     */
    @Test
    public void testFor_NullValueExceptionsExpense() {
        assertThrows(NullPointerException.class, () -> {
            new Expense(null, "test", ExpenseType.ENTERTAINMENT, Money.parse("USD 60"));
        });
        assertThrows(NullPointerException.class, () -> {
            new Expense(LocalDate.now(), null, ExpenseType.ENTERTAINMENT, Money.parse("USD 60"));
        });
        assertThrows(NullPointerException.class, () -> {
            new Expense(LocalDate.now(), "test", null, Money.parse("USD 60"));
        });
        assertThrows(NullPointerException.class, () -> {
            new Expense(LocalDate.now(), "test", ExpenseType.ENTERTAINMENT, null);
        });
    }

    /*
     * Testing for correct expense submissions
     * Testing for correct sum of all expenses
     * Testing to check when an expense is approved it actually approves it
     */
    @Test
    public void testFor_ExpensesPortal() {
        try {
            Expense expense1 = new Expense(LocalDate.now(), "Test1", ExpenseType.ENTERTAINMENT, Money.parse("EUR 20"));
            Expense expense2 = new Expense(LocalDate.now(), "Test1", ExpenseType.ENTERTAINMENT, Money.parse("EUR 40"));

            Expense expenseInUSD = new Expense(LocalDate.now(), "Test in USD", ExpenseType.OTHER, Money.parse("USD 1"));

            ExpensesPortal portal = new ExpensesPortal();
            portal.submitExpense(expense1, expense2, expenseInUSD);

            assertTrue(portal.getExpenses().size() == 3);

            Money total = ExpensesPortal.sumExpenses(portal.getExpenses());
            assertTrue(total.equals(Money.parse("EUR 60.95")));

            portal.approveExpense(expense1);
            assertTrue(expense1.isApproved() == true);

        } catch (IllegalDateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Testing to check that all expenses are correctly categorized
     */
    @Test
    public void testFor_CorrectExpenseCategorization() {
        try {
            Expense expense1 = new Expense(LocalDate.now(), "Test 1", ExpenseType.ENTERTAINMENT, Money.parse("EUR 70"));
            Expense expense2 = new Expense(LocalDate.now(), "Test 1", ExpenseType.ENTERTAINMENT, Money.parse("EUR 70"));
            Expense expense3 = new Expense(LocalDate.now(), "Test 1", ExpenseType.EQUIPMENT, Money.parse("EUR 70"));
            Expense expense4 = new Expense(LocalDate.now(), "Test 1", ExpenseType.SUPPLIES, Money.parse("EUR 70"));
            Expense expense5 = new Expense(LocalDate.now(), "Test 1", ExpenseType.ENTERTAINMENT, Money.parse("EUR 70"));

            ExpensesPortal portal = new ExpensesPortal();

            portal.submitExpense(expense1, expense2, expense3, expense4, expense5);
            PrinterByLabelTest plt = new PrinterByLabelTest();
            plt.print(portal.getExpenses());

            Map<String, List<Expense>> tempMap = plt.getCategories();
            assertTrue(tempMap.containsKey(ExpenseType.ENTERTAINMENT.type()));
            assertTrue(tempMap.containsKey(ExpenseType.EQUIPMENT.type()));
            assertTrue(tempMap.containsKey(ExpenseType.SUPPLIES.type()));

        } catch (IllegalDateException e) {
            e.printStackTrace();
        }
    }

}
