package me.leshchenkor.bank_api.exception;

public class BankException extends Exception{
    public BankException(String errorMessage) {
        super(errorMessage);
    }
}
