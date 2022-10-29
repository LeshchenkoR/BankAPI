package me.leshchenkor.bank_api.repository;

import me.leshchenkor.bank_api.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long>,
        CrudRepository<BankAccount, Long> {
}

