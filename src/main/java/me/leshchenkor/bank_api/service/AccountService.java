package me.leshchenkor.bank_api.service;

import lombok.RequiredArgsConstructor;
import me.leshchenkor.bank_api.dto.OperationType;
import me.leshchenkor.bank_api.entity.BankAccount;
import me.leshchenkor.bank_api.entity.Operation;
import me.leshchenkor.bank_api.exception.AccountNotFoundException;
import me.leshchenkor.bank_api.exception.RecursiveTransactionException;
import me.leshchenkor.bank_api.repository.BankAccountRepository;
import me.leshchenkor.bank_api.repository.OperationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final BankAccountRepository bankAccountRepository;
    private final OperationRepository operationRepository;

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

    public BankAccount putMoney(long userID, double amount, String description) {
        BankAccount account = bankAccountRepository.findById(userID)
                .orElseThrow(() -> new AccountNotFoundException
                        (String.format("Account with [%s] not exist!", userID)));
        Operation transfer = operationRepository
                .save(new Operation(userID, LocalDateTime.now(), OperationType.CREDIT, amount, description));
        account.setBalance(account.getBalance() + amount);
        operationRepository.saveAndFlush(transfer);
        return bankAccountRepository.save(account);
    }

    public ResponseEntity<Object> takeMoney(long userID, double amount, String description) {
        BankAccount account = bankAccountRepository.findById(userID)
                .orElseThrow(() -> new AccountNotFoundException
                        (String.format("Account with [%s] not exist!", userID)));
        if (account.getBalance() < amount) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).
                    body(String.format("Not enough money: '%s", amount));
        }
        Operation transfer = operationRepository
                .save(new Operation(userID, LocalDateTime.now(), OperationType.DEBIT, amount, description));
        operationRepository.saveAndFlush(transfer);

        account.setBalance(account.getBalance() - amount);
        bankAccountRepository.save(account);
        return ResponseEntity.status(HttpStatus.OK).body("Took successfully");
    }

    public ResponseEntity<Object> transferMoney(Long accountIdSource, Long accountIdDestination, double amount) {
        if (accountIdSource.equals(accountIdDestination)){
            throw new RecursiveTransactionException("Transaction to yourself");
        }
        takeMoney(accountIdSource, amount, "Transfer to " + accountIdDestination);
        putMoney(accountIdDestination, amount, "Transfer from " + accountIdSource);
        return ResponseEntity.status(HttpStatus.OK).body("Transfer successfully");
    }

    public List<Operation> getOperationList(LocalDateTime dateStart, LocalDateTime dateEnd) {
        return operationRepository.findAll().stream()
                .filter(f -> f.getDate().isAfter(dateStart) && f.getDate().isBefore(dateEnd))
                .collect(Collectors.toList());

    }
}
