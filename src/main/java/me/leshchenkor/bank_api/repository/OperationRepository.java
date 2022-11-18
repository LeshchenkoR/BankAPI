package me.leshchenkor.bank_api.repository;

import me.leshchenkor.bank_api.entity.Operations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operations, Long> {
    Optional<List<Operations>> findAllById(long id);
    List<Operations> findByDateBetween(Date dateFrom, Date dateTo);
}
