package me.leshchenkor.bank_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ACCOUNTS")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private Double balance;

    public BankAccount(Double balance) {
        this.balance = balance;
    }

    public BankAccount() {
    }

    @OneToMany(mappedBy = "user_id")
   // @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    @JsonIgnore
    private List<Operation> operations;
}
