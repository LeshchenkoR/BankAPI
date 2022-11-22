package me.leshchenkor.bank_api.service;

import lombok.RequiredArgsConstructor;
import me.leshchenkor.bank_api.dto.OperationType;
import me.leshchenkor.bank_api.entity.BankAccount;
import me.leshchenkor.bank_api.entity.Operations;
import me.leshchenkor.bank_api.exception.AccountBalanceChangeException;
import me.leshchenkor.bank_api.exception.AccountNotFoundException;
import me.leshchenkor.bank_api.exception.RecursiveTransactionException;
import me.leshchenkor.bank_api.repository.BankAccountRepository;
import me.leshchenkor.bank_api.repository.OperationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final BankAccountRepository bankAccountRepository;
    private final OperationRepository operationRepository;

    public BankAccount createAccount(BankAccount bankAcc) {
        double newBalance = bankAcc.getBalance() <= 0 ? 0 : bankAcc.getBalance();
        bankAcc.setBalance(newBalance);
        return bankAccountRepository.save(bankAcc);
    }

    public BankAccount findById(Long id) {
        return bankAccountRepository.findById(id).
                orElseThrow(() -> new AccountNotFoundException
                        (String.format("Account with ID %s not exist!", id)));
    }

    public void deleteAccount(Long userId) {
        BankAccount account = findById(userId);
        bankAccountRepository.delete(account);
    }

    public List<BankAccount> readAllAccounts() {
        return new ArrayList<>(bankAccountRepository.findAll());
    }

    public double getBalanceByID(Long userId) {
        BankAccount account = findById(userId);
        return account.getBalance();
    }


    public BankAccount putMoney(Long userID, double amount, String description)
            throws AccountBalanceChangeException {
        if (amount <= 0) throw new AccountBalanceChangeException("Amount not valid");
        BankAccount account = findById(userID);
        Operations transfer = operationRepository
                .save(new Operations(userID, new Date(), OperationType.DEBIT, amount, description));
        account.setBalance(account.getBalance() + amount);
        operationRepository.saveAndFlush(transfer);
        return bankAccountRepository.save(account);
    }

    public ResponseEntity<Object> takeMoney(Long userID, double amount, String description)
            throws AccountBalanceChangeException {
        BankAccount account = findById(userID);
        double newBalance = account.getBalance() - amount;
        if (newBalance < 0) {
            throw new AccountBalanceChangeException(String.format("Not enough money: '%s", amount));
        }
        Operations transfer = operationRepository
                .save(new Operations(userID, new Date(), OperationType.CREDIT, amount, description));
        operationRepository.saveAndFlush(transfer);

        account.setBalance(newBalance);
        bankAccountRepository.save(account);
        return ResponseEntity.status(HttpStatus.OK).body("Took successfully");
    }

    @Transactional
    public ResponseEntity<Object> transferMoney(Long accountIdSource, Long accountIdDestination, double amount)
            throws AccountBalanceChangeException {
        if (accountIdSource.equals(accountIdDestination)) {
            throw new RecursiveTransactionException("Transaction to yourself");
        }
        takeMoney(accountIdSource, amount, "Transfer to " + accountIdDestination);
        putMoney(accountIdDestination, amount, "Transfer from " + accountIdSource);
        return ResponseEntity.status(HttpStatus.OK).body("Transfer successfully");
    }

    public List<Operations> getOperationList(Date dateStart, Date dateEnd) {
        if (dateStart == null || dateEnd == null) {
            return operationRepository.findAll();
        }
        return operationRepository.findByDateBetween(dateStart, dateEnd);
    }
//    public List<Operations> getOperationList(OperationListDTO listDTO) {
//       if (listDTO.getStartDate() == null || listDTO.getFinishDate() == null) {
//            return operationRepository.findAll();
//        }
//        return operationRepository.findByDateBetween(listDTO.getStartDate(), listDTO.getFinishDate());
//    }
}
