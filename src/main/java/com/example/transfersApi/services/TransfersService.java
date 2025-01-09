package com.example.transfersApi.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.transfersApi.dto.TransfersDTO;
import com.example.transfersApi.models.TransferModel;
import com.example.transfersApi.repositories.TransfersRepository;

@Service
public class TransfersService {

  private final TransfersRepository TransfersRepository; 
  private final ModelMapper modelMapper;

  public TransfersService(TransfersRepository TransfersRepository, ModelMapper modelMapper) {
    this.TransfersRepository = TransfersRepository;
    this.modelMapper = modelMapper;
  }

  public TransferModel create(TransfersDTO Transfer){
    TransferModel TransferModel = modelMapper.map(Transfer, TransferModel.class);
    TransferModel result = TransfersRepository.save(TransferModel);
    return result;
  }

  public TransferModel update(TransfersDTO TransferDTO, UUID id) {
    Optional<TransferModel> optionalTransfer = TransfersRepository.findById(id);

    if (optionalTransfer.isEmpty()) {
        throw new RuntimeException("Usuário não encontrado com o ID: " + id);
    }

    TransferModel existingTransfer = optionalTransfer.get();
    modelMapper.map(TransferDTO, existingTransfer);

    return TransfersRepository.save(existingTransfer);
  }

  public TransferModel delete(UUID id) {
    Optional<TransferModel> foundTransfer = TransfersRepository.findById(id);

    if (foundTransfer.isEmpty()) {
        throw new RuntimeException("Usuário não encontrado com o ID: " + id);
    }

    TransferModel existingTransfer = foundTransfer.get();
    existingTransfer.setDeleted(true);

    return TransfersRepository.save(existingTransfer);
  }
  
  public Page<TransferModel> findAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return TransfersRepository.findByIsDeletedFalse(pageable);
  }

  public TransferModel findOne(UUID id){
    Optional<TransferModel> optionalTransfer = TransfersRepository.findByIdAndIsDeletedFalse(id);

    if (optionalTransfer.isEmpty()) {
        throw new RuntimeException("Usuário não encontrado com o ID: " + id);
    }

    TransferModel existingTransfer = optionalTransfer.get();
    return existingTransfer;
  }


}