package account.constants;

public enum AccountType {
    SAVINGS("Savings"), CHECKING("Checking"), CREDIT("Credit");

    private String value;

    AccountType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
