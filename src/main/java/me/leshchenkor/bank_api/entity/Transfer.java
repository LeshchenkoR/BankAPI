package me.leshchenkor.bank_api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "TRANSFER")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OP_ID")
    private Long id;
    private Long user_id;

    @Column(name = "transfer_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") //ПАЧИМУ НЕ РАБОТАЕТ????!!!
    private LocalDateTime date;

    private String type;
    private Double amount;

    public Transfer(Long user_id, LocalDateTime date, String type, Double amount) {
        this.user_id = user_id;
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    public Transfer() {
    }
}
