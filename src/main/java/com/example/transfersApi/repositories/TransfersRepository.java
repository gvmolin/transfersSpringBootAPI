package com.example.transfersApi.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transfersApi.models.TransferModel;

public interface TransfersRepository extends JpaRepository<TransferModel, UUID> {
  Page<TransferModel> findAll(Pageable pageable);
  Page<TransferModel> findByIsDeletedFalse(Pageable pageable);
  Optional<TransferModel> findByIdAndIsDeletedFalse(UUID id);
}
