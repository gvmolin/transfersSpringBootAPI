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
import com.example.transfersApi.models.UserModel;
import com.example.transfersApi.repositories.TransfersRepository;
import com.example.transfersApi.repositories.UsersRepository;
import com.example.transfersApi.utils.calcs.Calcs;

@Service
public class TransfersService {

  private final TransfersRepository transfersRepository; 
  private final ModelMapper modelMapper;
  private final UsersRepository usersRepository;

  public TransfersService(TransfersRepository transfersRepository, ModelMapper modelMapper, UsersRepository usersRepository) {
    this.transfersRepository = transfersRepository;
    this.modelMapper = modelMapper;
    this.usersRepository = usersRepository;
  }

  public TransferModel create(TransfersDTO transfer){
    TransferModel transferModel = modelMapper.map(transfer, TransferModel.class);
    Optional<UserModel> accountOrigin, accountDestination;
    
    try {
      accountOrigin = usersRepository.findByAccountNumberAndIsDeletedFalse(transfer.getOriginAccountNumber());
      accountDestination = usersRepository.findByAccountNumberAndIsDeletedFalse(transfer.getDestinationAccountNumber());

    } catch (Exception e) {
      throw new RuntimeException("Erro ao buscar usuarios");
    }

    if(!accountOrigin.isEmpty() && !accountDestination.isEmpty()){
      UserModel existingOrigin = accountOrigin.get();
      UserModel existingDestination = accountDestination.get();

      transferModel.setAccountOrigin(existingOrigin);
      transferModel.setAccountDestination(existingDestination);

    } else {
      throw new RuntimeException("Erro ao montar dados");
    }

    Calcs calcsClass = new Calcs();
    Double calculatedFee = calcsClass.calculateFee(transfer);
    Double finalValue = (transfer.getOriginalValue() - calculatedFee);

    transferModel.setFinalValue(finalValue);
    transferModel.setFee(calculatedFee);

    try {
      TransferModel result = transfersRepository.save(transferModel);
      return result;
    } catch (Exception e) {
      throw new RuntimeException("Erro ao salvar dados");
    }

  }

  public TransferModel update(TransfersDTO TransferDTO, UUID id) {
    Optional<TransferModel> optionalTransfer = transfersRepository.findById(id);

    if (optionalTransfer.isEmpty()) {
        throw new RuntimeException("Usuário não encontrado com o ID: " + id);
    }

    TransferModel existingTransfer = optionalTransfer.get();
    modelMapper.map(TransferDTO, existingTransfer);

    return transfersRepository.save(existingTransfer);
  }

  public TransferModel delete(UUID id) {
    Optional<TransferModel> foundTransfer = transfersRepository.findById(id);

    if (foundTransfer.isEmpty()) {
        throw new RuntimeException("Usuário não encontrado com o ID: " + id);
    }

    TransferModel existingTransfer = foundTransfer.get();
    existingTransfer.setDeleted(true);

    return transfersRepository.save(existingTransfer);
  }
  
  public Page<TransferModel> findAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return transfersRepository.findByIsDeletedFalse(pageable);
  }

  public TransferModel findOne(UUID id){
    Optional<TransferModel> optionalTransfer = transfersRepository.findByIdAndIsDeletedFalse(id);

    if (optionalTransfer.isEmpty()) {
      throw new RuntimeException("Usuário não encontrado com o ID: " + id);
    }

    TransferModel existingTransfer = optionalTransfer.get();
    return existingTransfer;
  }


}