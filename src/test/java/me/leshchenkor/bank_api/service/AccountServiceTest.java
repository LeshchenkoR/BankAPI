package me.leshchenkor.bank_api.service;

import me.leshchenkor.bank_api.entity.BankAccount;
import me.leshchenkor.bank_api.repository.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private BankAccountRepository bankAccountRepository;

//    @Test
//    void whenCreateAccountShouldCheckThatAccountWasAdded() {
//        BankAccount bankAccount = new BankAccount();
//        accountService.createAccount(bankAccount);
//        BankAccount actualAccount = accountService.findById(bankAccount.getUser_id());
//        assertEquals("Account was not saved properly", bankAccount, actualAccount);
//    }

    @Test
    void findById() {
    }


}