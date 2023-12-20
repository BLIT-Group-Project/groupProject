import java.util.List;

import account.Account;
import account.AccountService;
import account.AccountServiceImpl;
import account.CreditAccount;
import account.constants.AccountType;

public class AccountServiceDocs {
    public static void main(String[] args) {
        // right now the service requires dependency injection of the repository to work
        // when we do Spring boot this will work slightly differently because the framework will handle part of this.
        
        AccountService accountService = new AccountServiceImpl();

        // there are public constructors:
        Account testAccount1 = new Account(12345, AccountType.SAVINGS);
        Account testAccount2 = new Account(12345, AccountType.CHECKING);
        CreditAccount testAccount3 = new CreditAccount(12345);

        // you can also create a credit account like this:
        Account testAccount4 = new CreditAccount(123455);

        // but I'd rather you didn't since I can't prevent you from creating a credit account using the account constructor.
        Account testAccount5 = new Account(1234567, AccountType.CREDIT);

        
        // instead, go through the service to create new accounts:
        Account testSavingsAccount = new Account(accountService.createAccount(new Account(2984239, AccountType.SAVINGS)));
        // or:
        Account testCheckingAccount = new Account(2984239, AccountType.CHECKING);
        accountService.createAccount(testCheckingAccount);
        // you'll have to grab the account back out of the database before working with it:
        testCheckingAccount = new Account(accountService.getAccountById(2));

        // the service will automatically correctly save credit accounts too:
        CreditAccount testCreditAccount = new CreditAccount(accountService.createAccount(new Account(2984239, AccountType.CREDIT)));
        System.out.println(accountService.getAccountType(testCreditAccount));

        // the credit account can make charges, calculate its minimum payment and pay off its balance correctly:
        System.out.println(accountService.charge(testCreditAccount, 900.00));
        System.out.println(accountService.getMinimumPayment(testCreditAccount));
        System.out.println(accountService.makePayment(testCreditAccount, 120.00));

        // make deposits and withdrawals on the checking and savings accounts:
        System.out.println(accountService.deposit(testSavingsAccount, 4500.00));System.out.println(accountService.deposit(testCheckingAccount, 7534.00));
        System.out.println(accountService.withdraw(testSavingsAccount, 350.00));System.out.println(accountService.withdraw(testCheckingAccount, 458.00));

        // you save your new accounts to the repository like this:
        accountService.createAccount(testAccount1);
        accountService.createAccount(testAccount2);
        accountService.createAccount(testAccount3);
        accountService.createAccount(testAccount4);
        // ***!!!!!!! CREATE/SAVE YOUR ACCOUNTS TO THE REPOSITORY BEFORE UPDATING
        //  otherwise the program will throw an exception and crash because the account ID must already be in the repository for it to update!

        // remember this account I made further up in the page?
        CreditAccount testAccount6 = new CreditAccount(accountService.createAccount(testAccount5));
        System.out.println(testAccount6.toString());
        // the createAccount method will correctly make it into an actual credit account with the proper functionality:
        System.out.println(accountService.charge(testAccount6, 450.75));
        System.out.println(accountService.makePayment(testAccount6, 300.00));

        // // update the account in order to save all deposits, charges, withdrawals, and payments
        accountService.updateAccount(testSavingsAccount);
        accountService.updateAccount(testCheckingAccount);
        accountService.updateAccount(testCreditAccount);
        accountService.updateAccount(testAccount6);
        // // there's some casting magic that makes this work, and I hope it doesn't all break when we move this to an actual database.

        // you can get a list of accounts owned by any given userId:
        List<Account> accountList = accountService.getAllAccountsByUserId(2984239);
        System.out.println(accountList.toString());

        // and a list of all accounts in the repository:
        List<Account> accountList2 = accountService.getAll();
        System.out.println(accountList2.toString());

        // Except for deleteById and applyInterest, all public methods in the service layer return data for your API to consume.

        // look at account/AccountService.java for the list of public methods, their return types and parameters, and thank you for coming to my TED Talk.

    }
}
