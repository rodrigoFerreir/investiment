package com.project.investment.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.investment.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}