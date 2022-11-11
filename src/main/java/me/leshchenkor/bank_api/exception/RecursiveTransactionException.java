package me.leshchenkor.bank_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Recursive transaction")
public class RecursiveTransactionException extends RuntimeException {
    public RecursiveTransactionException(String message) {
        super(message);
    }
}
