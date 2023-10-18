/*
 * 
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package main.java.com.dave.app;

import java.time.LocalDate;
import org.joda.money.Money;

import main.java.com.dave.exceptions.IllegalDateException;

/*
 * Expense class containing all field members
 *  and accessor methods
 */

public class Expense {

    // field memebers
    private LocalDate localDate;
    private String description;
    private ExpenseType expenseType;
    private Money amount;
    private boolean approved = false;

    /**
     * @param localDate
     * @param description
     * @param expenseType
     * @param amount
     * @throws IllegalDateException
     */
    public Expense(LocalDate localDate,
            String description,
            ExpenseType expenseType,
            Money amount) throws IllegalDateException {
        this.setLocalDate(localDate);
        this.setDescription(description);
        this.setExpenseType(expenseType);
        this.setAmount(amount);

    }

    /**
     * 
     * @param amount
     * @throws NullPointerException
     */
    public void setAmount(Money amount) {
        if (amount == null) {
            throw new NullPointerException();
        }

        this.amount = amount;
    }

    /**
     * 
     * @param expenseType
     * @throws NullPointerException
     */
    public void setExpenseType(ExpenseType expenseType) {
        if (expenseType == null) {
            throw new NullPointerException();
        }
        this.expenseType = expenseType;
    }

    /**
     * 
     * @param description
     * @throws NullPointerException
     */
    public void setDescription(String description) {
        if (description == null) {
            throw new NullPointerException();
        }
        this.description = description;
    }

    /**
     * 
     * @param localDate
     * @throws IllegalDateException
     * @throws NullPointerException
     */
    public void setLocalDate(LocalDate localDate) throws IllegalDateException {
        if (localDate == null) {
            throw new NullPointerException();
        }
        if (localDate.isAfter(LocalDate.now())) {
            throw new IllegalDateException("Date in future exception!");
        }

        this.localDate = localDate;
    }

    public void approveExpense() {
        this.approved = true;
    }

    // toggle current value of approved
    public void toggleExpense() {
        this.approved = !this.approved;
    }

    // getters

    public Money getAmount() {
        return this.amount;
    }

    public String getExpenseType() {
        return this.expenseType.type();
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getLocalDate() {
        return this.localDate;
    }

    public boolean isApproved() {
        return this.approved;
    }

    // To String method
    @Override
    public String toString() {
        return String.format("%s: %s - %s - %s %s - is approved: %s", this.getLocalDate().toString(),
                this.getDescription(), this.getExpenseType(), amount.getCurrencyUnit().toString(),
                amount.getAmount().toPlainString(), isApproved());
    }

}
