package me.leshchenkor.bank_api.service;

import lombok.RequiredArgsConstructor;
import me.leshchenkor.bank_api.entity.BankAccount;
import me.leshchenkor.bank_api.exception.NotEnoughMoneyException;
import me.leshchenkor.bank_api.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;

//переделать в интерфейс и добавить реализацию в пакете impl?
@Service
@RequiredArgsConstructor
public class AccountService {
    @Autowired
    BankAccountRepository bankAccountRepository;

    public BankAccount createAccount(BankAccount bankAcc) {
        return bankAccountRepository.save(bankAcc);
    }

    public void deleteAccount(Long userId) throws AccountNotFoundException {
        BankAccount account = bankAccountRepository.findById(userId)
                .orElseThrow(AccountNotFoundException::new);
        bankAccountRepository.delete(account);
    }

    public List<BankAccount> readAll(){
        return new ArrayList<>(bankAccountRepository.findAll());
    }
// --------------------------------------------------------------------------------------------------

    public double getClientBalance(long userId) throws AccountNotFoundException {
        BankAccount account = bankAccountRepository.findById(userId)
                .orElseThrow(AccountNotFoundException::new);
        return account.getBalance();
    }

    public BankAccount putMoney(long userId, double income) throws AccountNotFoundException {
        BankAccount account = bankAccountRepository.findById(userId)
                .orElseThrow(AccountNotFoundException::new);
        account.setBalance(account.getBalance() + income);
        return bankAccountRepository.save(account);
    }

    public BankAccount takeMoney(long userId, double spend) throws AccountNotFoundException, NotEnoughMoneyException {
        BankAccount account = bankAccountRepository.findById(userId)
                .orElseThrow(AccountNotFoundException::new);
        if (account.getBalance() < spend) {
            throw new NotEnoughMoneyException(spend);
        } else
            account.setBalance(account.getBalance() - spend);
        return bankAccountRepository.save(account);
    }
}
