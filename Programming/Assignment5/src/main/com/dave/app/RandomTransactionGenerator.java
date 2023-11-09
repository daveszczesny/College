/*
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/*
 * Random Transaction Generator Thread class
 * Randomly generates transactions for the accounts in the bank's accounts collection
 *  and submits them for the bank to be processed
 */
public class RandomTransactionGenerator implements Runnable {

    // Attributes
    private Bank bank;
    private List<Integer> accountNumbers;
    private Random random = new Random();

    /**
     * RandomTransactionGenerator constructor, initializes bank instance, and retrieves account numbers from bank.
     * @param bank
     */
    public RandomTransactionGenerator(Bank bank) {
        this.bank = bank;
        this.accountNumbers = new ArrayList<>(bank.retrieveAccountNumbers());
    }

    @Override
    public void run() {

        try {
            // While the thread is still running, call generateRandomTransaction
            while (!Thread.currentThread().isInterrupted()) {
                generateRandomTransaction();
            }
        } catch (InterruptedException e) {
            // Interrupt the thread if exception found
            Thread.currentThread().interrupt();
        } finally {
            // Once the thread is interrupted (terminating ...) create the 'Poison Pill'
            createPoisonPillTransaction();
            System.out.println("Random Transaction Generator terminated.");
        }

    }

    /**
     * Method to randomly generate transactions between two accounts
     * Shuffles accounts from bank's collection, and then retrieve the first two
     * 
     * @throws InterruptedException
     */
    private void generateRandomTransaction() throws InterruptedException {

        if (accountNumbers.size() > 2) {

            Collections.shuffle(accountNumbers);
        }

        // Get first two accounts from list
        int accountNumber1 = accountNumbers.get(0);
        int accountNumber2 = accountNumbers.get(1);

        // Gets random floating point number (0 -> 10,000) and rounds it to 2 decimal
        // points using
        // Math.round(x * 100.0f) / 100.0f
        float amountToTransact = Math.round(random.nextFloat(10000) * 100.0f) / 100.0f;

        // Create two transactions, one to withdraw, and the other to deposit
        Transaction transaction1 = new Transaction(accountNumber1, amountToTransact);
        Transaction transaction2 = new Transaction(accountNumber2, -amountToTransact);

        // Submit transactions to bank's transaction queue
        bank.submitTransaction(transaction1);
        bank.submitTransaction(transaction2);

        // sleep for random duration
        sleepRandomTime();
    }

    /**
     * Method to create a 'Poison Pill' transaction to indicate queue is closed
     */
    private void createPoisonPillTransaction() {
        bank.submitTransaction(Transaction.POISON_PILL);
    }

    /**
     * Method to sleep the thread for a random amount of time between 0 - 1 seconds
     * 
     * @throws InterruptedException
     */
    private void sleepRandomTime() throws InterruptedException {
        Thread.sleep(random.nextInt(1000));
    }

}
