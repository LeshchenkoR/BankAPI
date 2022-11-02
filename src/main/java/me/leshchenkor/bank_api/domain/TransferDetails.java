package me.leshchenkor.bank_api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferDetails {
    private Long fromAccount;
    private Long toAccount;
    private Double transferAmount;
}
