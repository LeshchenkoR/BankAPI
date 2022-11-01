package me.leshchenkor.bank_api.entity;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNTS")
public class BankAccount {

    private Long user_id;
    private Double balance;

    public BankAccount(Double balance) {
        this.balance = balance;
    }

    public BankAccount() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
