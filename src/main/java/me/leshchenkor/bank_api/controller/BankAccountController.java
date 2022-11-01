package me.leshchenkor.bank_api.controller;

import me.leshchenkor.bank_api.entity.BankAccount;
import me.leshchenkor.bank_api.exception.NotEnoughMoneyException;
import me.leshchenkor.bank_api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BankAccountController {
    @Autowired
    AccountService accountService;

    @PostMapping(value = "/create")
    //    @ApiOperation(value = "Создание нового аккаунта")
    public BankAccount createAccount(@Validated @RequestBody BankAccount acc) {
        return accountService.createAccount(acc);
    }

    @DeleteMapping("/delete/{user_id}")
    //    @ApiOperation(value = "Удаление аккаунта")
    public ResponseEntity<?> deleteBankAccount(@PathVariable Long user_id) throws AccountNotFoundException {
        accountService.deleteAccount(user_id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<BankAccount>> read() {
        final List<BankAccount> bankAccounts = accountService.readAll();
        return bankAccounts != null && bankAccounts.isEmpty()
                ? new ResponseEntity<>(bankAccounts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//------------------------------------------------------------------------------------------------------

    @GetMapping(value = "/getBalance/{userId}")
//    @ApiOperation(value = "Получение баланса по id")
    public double readBankAccount(@PathVariable(value = "userId") Long id) throws AccountNotFoundException {
        return accountService.getClientBalance(id);
    }

    @PutMapping(value = "/putMoney/{userId}")
    //    @ApiOperation(value = "Внесение средств")
    public BankAccount putMoneyById(@PathVariable(value = "userId") Long id, @RequestParam double income)
            throws AccountNotFoundException {
        return accountService.putMoney(id, income);
    }

    @PostMapping(value = "/takeMoney/{userId}")
    //    @ApiOperation(value = "Снятие средств")
    public BankAccount takeMoneyById(@PathVariable(value = "userId") Long id, @RequestBody double spend)
            throws AccountNotFoundException, NotEnoughMoneyException {
        return accountService.takeMoney(id, spend);
    }
}
