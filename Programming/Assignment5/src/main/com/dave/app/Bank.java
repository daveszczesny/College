
/*
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */



 /* Bank Class, responsible for a collection of customer bank accounts */

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class Bank {
    
    // Attributes
    // accounts hashmap holds all account <Account Number, Account Object>
    private Map<Integer, Account> accounts = new HashMap<>();
    // Linked Blocking
    private LinkedBlockingQueue<Transaction> transactionQueue = new LinkedBlockingQueue<>();

    /**
     * Method to add account to the Bank
     * @param account
     * @throws IllegalArgumentException
     */
    public void addAccount(Account account) throws DuplicateAccountException{

        // if account argument is null
        if(account == null){
            throw new IllegalArgumentException();
        }

        // if account is already in bank
        if(accounts.containsKey(account.getAccountNumber())){
            throw new DuplicateAccountException("Acount already in bank!");
        }
        
        // add acount to banks collection
        accounts.put(account.getAccountNumber(), account);
    }


    /**
     * Method to retrieve an account from the bank's collection from the accounts account number
     * @param accNumber
     * @return Account
     * @throws AccountNotFoundException
     * @throws IllegalArgumentException
     */
    public Account retrieveAccount(int accNumber) throws AccountNotFoundException{
        // if account number is not valid
        if(accNumber < 0){
            throw new IllegalArgumentException();
        }

        // Create account instance
        Account account = accounts.get(accNumber);
        // check if account is found
        if(account == null){
            throw new AccountNotFoundException("The accound number provided yields no account!");
        }

        // return account found!
        return account;
    }

    /**
     * Method to submit a transaction to be processed; Adds transaction to queue to await its turn
     * @param transaction
     * @throws IllegalArgumentException
     */
    public void submitTransaction(Transaction transaction){

        // if transaction invalid
        if(transaction == null){
            throw new IllegalArgumentException();
        }

        // Add to queue
        transactionQueue.add(transaction);

    }

    /**
     * Method to retrieve the next transaction from the queue
     * @return Transaction
     * @throws NoSuchElementException
     */
    public Transaction retrieveNextTransaction(){
        // Checks if the transaction queue is empty
        if(transactionQueue.isEmpty()){
            return null;
        }

        // assign transaction to the first element in the queue and remove it from the queue
        Transaction transaction = transactionQueue.peek();

        // If the transaction is the 'Poison Pill', return transaction
        if(transaction == Transaction.POISON_PILL){
            return transaction;
        }

        // remove head of transaction queue and the return
        transactionQueue.remove();
        return transaction;    
    }



    /**
     * Method to print out the account details given an account number
     * @param accNumvber
     * @return
     * @throws AccountNotFoundException
     */
    public String printAccountDetails(int accNumber) throws AccountNotFoundException{

        // retrieve account from collection
        Account account = retrieveAccount(accNumber);
        return account.toString();
    }

    /**
     * Method to return summary of all accounts from bank's collection
     * @return Summary of Accounts in bank's collection
     */
    public String printAllAccountDetails(){
        
        // Create a string builder
        StringBuilder summary = new StringBuilder();

        // Retrieve all account numbers, and for each of them and retrieve the according account
        // add account to String builder
        retrieveAccountNumbers().forEach(accNumber -> {
            try {
                summary.append(retrieveAccount(accNumber)).append("\n");
            } catch (AccountNotFoundException e) {
                e.printStackTrace();
            }
        });

        // return string of StringBuilder
        return summary.toString();
    }

    /**
     * Method to retrieve a set of all account numbers
     * @return null : set of the account numbers
     */
    public Set<Integer> retrieveAccountNumbers(){
        return accounts.isEmpty() ? null : accounts.keySet();
    }   

}
