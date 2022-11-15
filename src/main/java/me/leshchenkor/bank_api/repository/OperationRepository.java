package me.leshchenkor.bank_api.repository;

import me.leshchenkor.bank_api.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    Optional<List<Operation>> findAllById(long id);
    List<Operation> findByDateBetween(Date dateFrom, Date dateTo);
}
