package me.leshchenkor.bank_api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import me.leshchenkor.bank_api.dto.OperationType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Schema(description = "Account transactions")
@Entity
@Table(name = "OPERATIONS")
public class Operations {

    @Schema(description = "Auto generated unique id", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OP_ID")
    private Long id;
    private Long user_id;

    @Schema(description = "Transaction timestamp")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OPERATION_DATE")
    private Date date;

    @Schema(description = "Transaction TYPE")
    @Enumerated(EnumType.STRING)
    @Column(name = "OPERATION_TYPE")
    private OperationType type;
    private Double amount;
    @Schema(description = "Transaction description")
    private String description;

//    @ManyToOne
//    BankAccount bankAccount;

    public Operations(Long user_id, Date date, OperationType type, Double amount, String description) {
        this.user_id = user_id;
        this.date = new Date();
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public Operations() {
    }
}
