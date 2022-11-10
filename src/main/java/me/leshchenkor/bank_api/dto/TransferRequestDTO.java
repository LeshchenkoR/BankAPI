package me.leshchenkor.bank_api.dto;

public class TransferRequestDTO {
    private long accountSource;
    private long accountDestination;
    private double amount;

    public TransferRequestDTO(long accountSource, long accountDestination, double amount) {
        this.accountSource = accountSource;
        this.accountDestination = accountDestination;
        this.amount = amount;
    }

    public TransferRequestDTO() {
    }

    public long getAccountSource() {
        return accountSource;
    }

    public void setAccountSource(long accountSource) {
        this.accountSource = accountSource;
    }

    public long getAccountDestination() {
        return accountDestination;
    }

    public void setAccountDestination(long accountDestination) {
        this.accountDestination = accountDestination;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
