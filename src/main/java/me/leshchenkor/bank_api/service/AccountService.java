package me.leshchenkor.bank_api.service;

import lombok.RequiredArgsConstructor;
import me.leshchenkor.bank_api.entity.BankAccount;
import me.leshchenkor.bank_api.entity.Transfer;
import me.leshchenkor.bank_api.exception.AccountNotFoundException;
import me.leshchenkor.bank_api.repository.BankAccountRepository;
import me.leshchenkor.bank_api.repository.TransferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final BankAccountRepository bankAccountRepository;
    private final TransferRepository transferRepository;

    public ResponseEntity<Object> createAccount(BankAccount bankAcc) {
        bankAccountRepository.save(bankAcc);
        return ResponseEntity.status(HttpStatus.CREATED).body("New account created successfully");
    }

//    public BankAccount findById(Long id) {
//        return bankAccountRepository.findById(id).
//                orElseThrow(() -> new AccountNotFoundException
//                        (String.format("Account with [%s] not exist!", id)));
//    }

    public ResponseEntity<BankAccount> findById(Long id) {
        return bankAccountRepository.findById(id)
                .map(acc -> ResponseEntity.ok().body(acc))
                .orElse(ResponseEntity.notFound().build());
    }

    public void deleteAccount(Long userId) {
        BankAccount account = bankAccountRepository.findById(userId)
                .orElseThrow(() -> new AccountNotFoundException
                        (String.format("Account with [%s] not exist!", userId)));
        bankAccountRepository.delete(account);
    }

    public List<BankAccount> readAll() {
        return new ArrayList<>(bankAccountRepository.findAll());
    }

    public double getBalanceByID(Long userId) {
        BankAccount account = bankAccountRepository.findById(userId)
                .orElseThrow(() -> new AccountNotFoundException
                        (String.format("Account with [%s] not exist!", userId)));
        return account.getBalance();
    }
//---------------------------------------------------------------------------------------------------

    public BankAccount putMoney(long userID, double amount) {
        BankAccount account = bankAccountRepository.findById(userID)
                .orElseThrow(() -> new AccountNotFoundException
                        (String.format("Account with [%s] not exist!", userID)));
        account.setBalance(account.getBalance() + amount);
        Transfer transfer = transferRepository
                .save(new Transfer(userID, LocalDateTime.now(), "Пополнение баланса", amount));
        transferRepository.saveAndFlush(transfer);
        return bankAccountRepository.save(account);
    }

    public ResponseEntity<Object> takeMoney(long userID, double amount) {
        BankAccount account = bankAccountRepository.findById(userID)
                .orElseThrow(() -> new AccountNotFoundException
                        (String.format("Account with [%s] not exist!", userID)));
        if (account.getBalance() < amount) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).
                    body(String.format("Not enough money: '%s", amount));
        } else
            account.setBalance(account.getBalance() - amount);
        Transfer transfer = transferRepository
                .save(new Transfer(userID, LocalDateTime.now(), "Уменьшение баланса", amount));
        transferRepository.saveAndFlush(transfer);
        bankAccountRepository.save(account);
        return ResponseEntity.status(HttpStatus.OK).body("Took successfully");
    }

    public ResponseEntity<Object> transferMoney(Long accountIdSource, Long accountIdDestination, double amount) {
        takeMoney(accountIdSource, amount);
        putMoney(accountIdDestination, amount);
        return ResponseEntity.status(HttpStatus.OK).body("Transfer successfully");
    }
}
