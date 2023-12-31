
/*
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.joda.money.Money;

public class App {
    public static void main(String[] args) {

        final int DURATION_TO_RUN_RANDOM_TRANSACTION_GENERATOR = 10; // in seconds
        Bank bank = new Bank();

        try {

            // Creates 3 accounts with 100,000 EUR
            Account account1 = new Account(12347, Money.parse("EUR" + 100_000.0f));
            Account account2 = new Account(25348, Money.parse("EUR" + 50_050.0f));
            Account account3 = new Account(11284, Money.parse("EUR" + 90_500.0f));

            // Adds accounts to bank
            bank.addAccount(account1);
            bank.addAccount(account2);
            bank.addAccount(account3);

            // Create 1 RandomTransactionGenerator, and 2 TransactionProcessors
            RandomTransactionGenerator randomTransactionGenerator = new RandomTransactionGenerator(bank);
            TransactionProcessor transactionProcessor1 = new TransactionProcessor("TP1", bank);
            TransactionProcessor transactionProcessor2 = new TransactionProcessor("TP2", bank);

            // Creates two ExecutorServices, 1 for transaction processors, 1 for random
            // transaction generator
            ExecutorService transactionProcessorThreadPool = Executors.newFixedThreadPool(2);
            ExecutorService randomTransactionGeneratorThreadPool = Executors.newFixedThreadPool(1);

            // Add Threads to ExecutorService
            transactionProcessorThreadPool.execute(transactionProcessor1);
            transactionProcessorThreadPool.execute(transactionProcessor2);
            randomTransactionGeneratorThreadPool.execute(randomTransactionGenerator);

            // Calls to shutdown ExecutorServices
            transactionProcessorThreadPool.shutdown();
            randomTransactionGeneratorThreadPool.shutdown();

            try {
                // Awaits for the duration of time to lapse, and forcefully shutdown thread
                if (!randomTransactionGeneratorThreadPool.awaitTermination(DURATION_TO_RUN_RANDOM_TRANSACTION_GENERATOR,
                        TimeUnit.SECONDS)) {
                    randomTransactionGeneratorThreadPool.shutdownNow();

                    // After RandomTransactionGenerator thread completes,
                    // Wait for TransactionProcessor to finish (Given limited time incase of
                    // deadlock)
                    if (transactionProcessorThreadPool.awaitTermination(30, TimeUnit.SECONDS)) {
                        // Print out account summary after threads complete
                        System.out.println(bank.printAllAccountDetails());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (NegativeBalanceException | DuplicateAccountException e) {
            e.printStackTrace();
        }
    }
}
