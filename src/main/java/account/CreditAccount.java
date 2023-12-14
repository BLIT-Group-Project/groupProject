package account;

import account.constants.AccountType;

public class CreditAccount extends Account{

    // this needs to also include a minimum payment
    // deposit and withdraw need to be overridden as 'balance' for a credit account means debt.  so deposit needs to reduce balance, and withdraw add to it

    private AccountType accountType;
    private double minimumPayment;

    protected CreditAccount(int userId) {
        super(userId);
        this.accountType = AccountType.CREDIT;
    }

    
    
}
