package me.leshchenkor.bank_api.entity;

import lombok.Getter;
import lombok.Setter;
import me.leshchenkor.bank_api.dto.OperationType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "OPERATIONS")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OP_ID")
    private Long id;
    private Long user_id;

    @Column(name = "OPERATION_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") //ПАЧИМУ НЕ РАБОТАЕТ????!!!
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "OPERATION_TYPE")
    private OperationType type;
    private Double amount;
    private String description;

//    @ManyToOne
//    BankAccount bankAccount;

    public Operation(Long user_id, LocalDateTime date, OperationType type, Double amount, String description) {
        this.user_id = user_id;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public Operation() {
    }
}
