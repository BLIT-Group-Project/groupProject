import java.util.List;

import account.Account;
import account.AccountRepository;
import account.AccountService;
import account.AccountServiceImpl;
import account.CreditAccount;
import account.constants.AccountType;

public class AccountServiceDocs {
    public static void main(String[] args) {
        // right now the service requires dependency injection of the repository to work
        // when we do Spring boot this will work slightly differently because the framework will handle part of this.
        // start by making a new AccountRepository object:
        AccountRepository accountRepo = new AccountRepository();

        // then make a new AccountService object passing the repository as an argument:
        AccountService accountService = new AccountServiceImpl(accountRepo);

        // there are public constructors, but don't use them directly.  Otherwise things will throw errors because of my fake repository.  Using a real repository may fix this, so.. 

        // instead, go through the service to create new accounts:
        Account testSavingsAccount = new Account(accountService.createAccount(new Account(2984239, AccountType.SAVINGS)));
        // or:
        Account testCheckingAccount = new Account(2984239, AccountType.CHECKING);
        accountService.createAccount(testCheckingAccount);

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

        // update the account in order to save all deposits, charges, withdrawals, and patments
        // this works because of the behind the scenes casting when the accounts are created (and is why when you use the constructors directly the program will crash)
        accountService.updateAccount(testSavingsAccount);
        accountService.updateAccount(testCheckingAccount);
        accountService.updateAccount(testCreditAccount);

        // Except for deleteById and applyInterest, all public methods in the service layer return data for your API to use.

        // you can get a list of accounts owned by any given userId:
        List<Account> accountList = accountService.getAllAccountsByUserId(2984239);
        System.out.println(accountList.toString());

        // look at account/AccountService.java for the list of public methods, their return types and parameters, and thank you for coming to my TED Talk.

    }
}
