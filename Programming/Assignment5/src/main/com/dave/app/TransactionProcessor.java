/*
 *@Author: Dawid Szczesny
 *@ID: 21300293 
 */

import java.util.Random;
import org.joda.money.Money;


/*
 * TransactionProcessor Thread class
 * Takes the transactions from the bank's transaction queue
 *  and processes them (making deposits / withdrawals)
 */
public class TransactionProcessor implements Runnable {

    // Attributes
    private String processName;
    private Bank bank;
    private int withdrawalCount;
    private int depositCount;
    private long timeElapsedLastTransaction;

    // Alias for limit of time elapsed since the last transaction recieved
    private final int TIME_ELAPSED_LIMIT_FROM_LAST_TRANSACTION = 5000; // in miliseconds

    /**
     * TransactionProcessor constructor
     *  initializes attributes and takes note of current time as time elapsed since last transaction
     * @param transactionName
     * @param bank
     */
    public TransactionProcessor(String processName, Bank bank) {
        this.processName = processName;
        this.bank = bank;
        this.withdrawalCount = 0;
        this.depositCount = 0;
        this.timeElapsedLastTransaction = System.currentTimeMillis();
    }

    @Override
    public void run() {

        // Take reference of first transaction from bank
        Transaction transaction = bank.retrieveNextTransaction();

        // While the transaction is not the 'Poison Pill' or time elapsed hasn't hit its limit
        while (transaction != Transaction.POISON_PILL
                || timeElapsedLastTransaction < TIME_ELAPSED_LIMIT_FROM_LAST_TRANSACTION) {

            // If the transaction isn't null, proceed to processing the current transaction
            if (transaction != null) {
                try {
                    processTransaction(transaction);
                } catch (InsufficientFundsException | InterruptedException | AccountNotFoundException e) {
                    e.printStackTrace();
                }
            }
            // retrieve the next transaction
            transaction = bank.retrieveNextTransaction();
        }

        // Finally print the summary of thread
        printFinalSummary();
    }

    /**
     * Method to process a transaction. Retrieves the account from the bank from the
     * transaction account number,
     * Checks if the amount of the transaction is positive (Deposit) or negative
     * (Withdrawal) and uses the according method to process
     * 
     * @param transaction
     * @throws AccountNotFoundException
     * @throws InsufficientFundsException
     * @throws InterruptedException
     */
    private void processTransaction(Transaction transaction)
            throws AccountNotFoundException, InsufficientFundsException, InterruptedException {

        // Retrieves account for transaction
        Account account = bank.retrieveAccount(transaction.getAccountNumber());

        if (transaction.getAmount() > 0) { // positive number represents deposit
            // must parse transaction amount to joda Money (EUR)
            account.makeDeposit(Money.parse("EUR " + transaction.getAmount()));
            depositCount++;
        } else { // everything else represents a withdrawal
                 // must parse transaction amount to joda Money (EUR)
                 // converting the withdrawl amount to a positve number
            account.makeWithdrawal(Money.parse("EUR " + -transaction.getAmount()));
            withdrawalCount++;
        }

        // Reset timer since last transaction
        this.timeElapsedLastTransaction = System.currentTimeMillis();

        // print summary to console
        printSummary(transaction);

        // Sleep the process for random amount of time
        sleepRandomTime();
    }

    /**
     * Method to sleep the thread for a random amount of time between 0 - 1 seconds
     * 
     * @throws InterruptedException
     */
    private void sleepRandomTime() throws InterruptedException {
        Random random = new Random();
        Thread.sleep(random.nextInt(1000)); // in miliseconds
    }

    /**
     * Method to print out a given transaction summary
     * 
     * @param transaction
     */
    private void printSummary(Transaction transaction) {
        String summary = processName + " is processing " + transaction;
        System.out.println(summary);
    }

    /**
     * Method to print out a summary of all transactions conducted by thread
     */
    private void printFinalSummary() {
        String summary = String.format(
                "%s finished processing %d transactions, including %d deposits, and %d withdrawals",
                processName, withdrawalCount + depositCount, depositCount, withdrawalCount);
        System.out.println(summary);
    }

}
