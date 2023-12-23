package account.exceptions;

public class AccountCreationException extends RuntimeException {
    
    private String message;

    public AccountCreationException() {
        this.message = "Account creation failed";
    }

    public String getMessage() {
        return message;
    }
}
