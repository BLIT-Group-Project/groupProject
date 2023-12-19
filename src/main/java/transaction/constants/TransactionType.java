package transaction.constants;

public enum TransactionType {
    DEPOSIT("Deposit"), WITHDRAW("Withdraw"), CHARGE("Charge"), MAKE_PAYMENT("Make Payment");

    private String value;

    TransactionType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
