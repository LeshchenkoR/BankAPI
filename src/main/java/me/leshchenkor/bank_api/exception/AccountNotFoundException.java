package me.leshchenkor.bank_api.exception;

public class AccountNotFoundException extends Exception {
    private long user_id;

    public AccountNotFoundException(long user_id) {
        super(String.format("Account is not found with id: '%s", user_id));
    }
}
