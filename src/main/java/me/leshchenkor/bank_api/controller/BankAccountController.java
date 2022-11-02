package me.leshchenkor.bank_api.controller;

import me.leshchenkor.bank_api.entity.BankAccount;
import me.leshchenkor.bank_api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankAccountController {
    @Autowired
    AccountService accountService;

    @PostMapping(value = "/create")
    //    @ApiOperation(value = "Создание нового аккаунта")
    public ResponseEntity<Object> createAccount(@RequestBody BankAccount acc) {
        return accountService.createAccount(acc);
    }

//    @GetMapping(value = "/{id}")
//    //@ApiOperation(value = "Получение аккаунта по ID")
//    public BankAccount getById(@PathVariable Long id) {
//        return accountService.findById(id);
//    }

    @GetMapping(value = "/{id}")
    //@ApiOperation(value = "Получение аккаунта по ID")
    public ResponseEntity<BankAccount> getById(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @DeleteMapping("/delete/{user_id}")
    //    @ApiOperation(value = "Удаление аккаунта")
    public ResponseEntity<?> deleteBankAccount(@PathVariable Long user_id) {
        accountService.deleteAccount(user_id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/accounts")
    //    @ApiOperation(value = "Получение списка всех аккаунтов")
    public ResponseEntity<List<BankAccount>> read() {
        final List<BankAccount> bankAccounts = accountService.readAll();
        return bankAccounts != null && !bankAccounts.isEmpty()
                ? new ResponseEntity<>(bankAccounts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//------------------------------------------------------------------------------------------------------

    @GetMapping(value = "/getBalance/{userId}")
//    @ApiOperation(value = "Получение баланса по id")
    public double readBankAccount(@PathVariable(value = "userId") Long id) {
        return accountService.getBalanceByID(id);
    }

    @PutMapping(value = "/putMoney/{userId}/{amount}")
    //    @ApiOperation(value = "Внесение средств")
    public BankAccount putMoneyById(@PathVariable long userId, @PathVariable double amount) {
        return accountService.putMoney(userId, amount);
    }

    @PostMapping(value = "/takeMoney/{userId}/{amount}")
    //    @ApiOperation(value = "Снятие средств")
    public ResponseEntity<Object> takeMoneyById(@PathVariable long userId, @PathVariable double amount) {
        return accountService.takeMoney(userId, amount);
    }
}
