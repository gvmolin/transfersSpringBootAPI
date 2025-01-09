package com.example.transfersApi.repositories;

import com.example.transfersApi.models.UserModel;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserModel, UUID> {
  Page<UserModel> findAll(Pageable pageable);
  Page<UserModel> findByIsDeletedFalse(Pageable pageable);
  Optional<UserModel> findByIdAndIsDeletedFalse(UUID id);
  Boolean existsByAccountNumber(String accountNumber);
}
