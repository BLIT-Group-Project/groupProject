package account.exceptions;

public class AccountNotFoundException extends RuntimeException {
    private String message;

    public AccountNotFoundException() {
        this.message = "Account not found";
    }
    
    public String getMessage() {
        return message;
    }
}
