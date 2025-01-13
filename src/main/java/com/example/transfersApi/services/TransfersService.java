package com.example.transfersApi.services;

import java.util.Optional;
import java.util.UUID;

import org.hibernate.exception.DataException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.transfersApi.dto.CheckFeeDTO;
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
    if(transfer.getOriginAccountNumber() == transfer.getDestinationAccountNumber()){
      throw new DataException("Conta de origem nao pode ser a mesma de destino!", null);
    }

    TransferModel transferModel = modelMapper.map(transfer, TransferModel.class);
    Optional<UserModel> accountOrigin, accountDestination;
    
    try {
      accountOrigin = usersRepository.findByAccountNumberAndIsDeletedFalse(transfer.getOriginAccountNumber());
      accountDestination = usersRepository.findByAccountNumberAndIsDeletedFalse(transfer.getDestinationAccountNumber());

    } catch (Exception e) {
      throw new RuntimeException("Erro ao buscar usuarios");
    }

    UserModel existingOrigin, existingDestination;
    if(!accountOrigin.isEmpty() && !accountDestination.isEmpty()){
      existingOrigin = accountOrigin.get();
      existingDestination = accountDestination.get();

      transferModel.setAccountOrigin(existingOrigin);
      transferModel.setAccountDestination(existingDestination);

    } else {
      throw new RuntimeException("Erro ao montar dados");
    }

    Calcs calcsClass = new Calcs();
    Double calculatedFee = calcsClass.calculateFee(transfer);
    Double finalValue = (transfer.getOriginalValue() + calculatedFee);

    if(finalValue > existingOrigin.getBalance()){
      throw new DataException("Conta de destino nao possui saldo em conta", null);
    }

    transferModel.setFinalValue(finalValue);
    transferModel.setFee(calculatedFee);

    try {
      TransferModel result = transfersRepository.save(transferModel);
      return result;
    } catch (Exception e) {
      throw new RuntimeException("Erro ao salvar dados");
    }

  }

  public CheckFeeDTO checkFee(TransfersDTO transfer){
    if(transfer.getOriginAccountNumber() == transfer.getDestinationAccountNumber()){
      throw new DataException("Conta de origem nao pode ser a mesma de destino!", null);
    }

    TransferModel transferModel = modelMapper.map(transfer, TransferModel.class);
    Optional<UserModel> accountOrigin, accountDestination;
    
    try {
      accountOrigin = usersRepository.findByAccountNumberAndIsDeletedFalse(transfer.getOriginAccountNumber());
      accountDestination = usersRepository.findByAccountNumberAndIsDeletedFalse(transfer.getDestinationAccountNumber());

    } catch (Exception e) {
      throw new RuntimeException("Erro ao buscar usuarios");
    }

    UserModel existingOrigin, existingDestination;
    if(!accountOrigin.isEmpty() && !accountDestination.isEmpty()){
      existingOrigin = accountOrigin.get();
      existingDestination = accountDestination.get();

      transferModel.setAccountOrigin(existingOrigin);
      transferModel.setAccountDestination(existingDestination);

    } else {
      throw new RuntimeException("Erro ao montar dados");
    }

    Calcs calcsClass = new Calcs();
    Double calculatedFee = calcsClass.calculateFee(transfer);

    CheckFeeDTO checkFeeDTO = new CheckFeeDTO();

    checkFeeDTO.setFee(calculatedFee);
    checkFeeDTO.setOriginAccountBalance(existingOrigin.getBalance());
    checkFeeDTO.setFinalValue(transfer.getOriginalValue() + calculatedFee);

    return checkFeeDTO;
  }

  public TransferModel update(TransfersDTO transfer, UUID id) {
    Optional<TransferModel> optionalTransfer = transfersRepository.findById(id);

    if (optionalTransfer.isEmpty()) {
        throw new RuntimeException("Usuário não encontrado com o ID: " + id);
    }

    TransferModel existingTransfer = optionalTransfer.get();
    TransferModel transferModel = modelMapper.map(existingTransfer, TransferModel.class);
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