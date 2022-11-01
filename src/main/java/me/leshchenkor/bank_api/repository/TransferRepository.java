package me.leshchenkor.bank_api.repository;

import me.leshchenkor.bank_api.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    public Optional<List<Transfer>> findAllById(long id);
}
