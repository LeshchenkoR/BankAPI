package me.leshchenkor.bank_api.service;

import lombok.RequiredArgsConstructor;
import me.leshchenkor.bank_api.entity.BankAccount;
import me.leshchenkor.bank_api.exception.AccountNotFoundException;
import me.leshchenkor.bank_api.repository.BankAccountRepository;
import me.leshchenkor.bank_api.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferService {
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    TransferRepository transferRepository;

    public ResponseEntity<Object> transferMoney(long sender, long recipient, int amount)
            throws AccountNotFoundException {

        Optional<BankAccount> fromAcc = bankAccountRepository.findById(sender);
        if (fromAcc.isPresent()) {
            if (fromAcc.get().getBalance() >= amount) {
                fromAcc.get().setBalance(fromAcc.get().getBalance() - amount);
            } else throw new AccountNotFoundException(String.format("Sender with [%s] not exist!", sender));

            Optional<BankAccount> toAcc = bankAccountRepository.findById(recipient);
            if (toAcc.isPresent()) {
                toAcc.get().setBalance(toAcc.get().getBalance() + amount);
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                        ("recipient account" + recipient + "not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success: " + amount + " transferred for " + recipient);

    }
}
