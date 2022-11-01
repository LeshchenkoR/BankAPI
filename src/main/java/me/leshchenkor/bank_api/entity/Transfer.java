package me.leshchenkor.bank_api.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANSFER")
public class Transfer {

    private Long id;
    private Long user_id;
    private Date date;
    private String type;
    private Double amount;

    public Transfer(Long user_id, Date date, String type, Double amount) {
        this.user_id = user_id;
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    public Transfer() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OP_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(targetEntity = BankAccount.class)
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
