package me.leshchenkor.bank_api.exception;

public class NotEnoughMoneyException extends Exception{
    private double money;

    public NotEnoughMoneyException(double money) {
        super(String.format("Not enough money: '%s", money));
    }
}
