package account.exceptions;

public class AccountMismatchException extends RuntimeException {
    
    // this error is for the application to know if it's tried to use deposit or withdrawal on a credit account, or makePayment or charge on checking or savings accounts.
    private String message;
    
    public AccountMismatchException(String functionality, String accountType) {
        this.message = functionality + " not available to " + accountType.toString() + " accounts.";
    }

    public AccountMismatchException() {
        this.message = "This is only for creating checking or savings accounts";
    }

    public String getMessage() {
        return message;
    }
}
