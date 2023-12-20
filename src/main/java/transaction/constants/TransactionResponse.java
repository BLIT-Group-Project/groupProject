package transaction.constants;

public enum TransactionResponse {
    ACCEPTED("Accepted"), DECLINED("Declined"), PENDING("Pending");

    private String value;

    TransactionResponse(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
    
}
