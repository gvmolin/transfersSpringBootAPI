package com.example.transfersApi.repositories;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.transfersApi.models.TransferModel;

public interface TransfersRepository extends JpaRepository<TransferModel, UUID> {
  Page<TransferModel> findAll(Pageable pageable);
  Page<TransferModel> findByIsDeletedFalse(Pageable pageable);
  Optional<TransferModel> findByIdAndIsDeletedFalse(UUID id);

  @Query("SELECT t FROM TransferModel t WHERE CAST(t.scheduledDate AS DATE) = ?1 AND t.isDeleted = false")
  List<TransferModel> findByScheduledDateAndIsDeletedFalse(LocalDate scheduledDate);
}
