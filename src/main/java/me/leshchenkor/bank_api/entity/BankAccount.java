package me.leshchenkor.bank_api.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
//@NoArgsConstructor
//@RequiredArgsConstructor
@Entity
@Table(name = "BANK_ACCOUNT")
public class BankAccount {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "USER_ID", length = 8, nullable = false)
    private Long user_id;

    @Column(name = "BALANCE")
    private Double balance;
}
