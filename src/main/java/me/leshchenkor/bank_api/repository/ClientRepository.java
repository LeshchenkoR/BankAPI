package me.leshchenkor.bank_api.repository;

import me.leshchenkor.bank_api.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
