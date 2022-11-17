package me.leshchenkor.bank_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Amount not valid")
public class AccountBalanceChangeException extends BankException {

    public AccountBalanceChangeException(String errorMessage) {
        super(errorMessage);
    }
}
