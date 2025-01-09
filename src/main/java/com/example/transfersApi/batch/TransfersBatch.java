package com.example.transfersApi.batch;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.transfersApi.enums.TransferStatusEnum;
import com.example.transfersApi.models.TransferModel;
import com.example.transfersApi.models.UserModel;
import com.example.transfersApi.repositories.TransfersRepository;
import com.example.transfersApi.repositories.UsersRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TransfersBatch {

  @Autowired
  private final TransfersRepository transfersRepository; 

  private final UsersRepository usersRepository;
  public TransfersBatch(TransfersRepository transfersRepository, UsersRepository usersRepository) {
    this.transfersRepository = transfersRepository;
    this.usersRepository = usersRepository;
  }

  // @Scheduled(cron = "0 1 0 * * ?")
  @Scheduled(cron = "0 0/1 * * * ?")
  public void processTransfers(){
    log.info("------------------------");
    log.info("Executing transfers batch");
    LocalDate today = LocalDate.now();
    List<TransferModel> transfers = transfersRepository.findByScheduledDateAndIsDeletedFalse(today);

    List<TransferModel> filteredTransfers = transfers.stream()
      .filter(transfer -> !transfer.getStatus().equals(TransferStatusEnum.FINISHED))
      .filter(transfer -> transfer.getAttempts() < 3)
      .collect(Collectors.toList());

    for(TransferModel scheduled : filteredTransfers){
      log.info("------------------------");
      log.info("Processing " + scheduled.getId() + " scheduled transfer...");
      log.info("Origin account: " + scheduled.getAccountOrigin());
      log.info("Origin destination " + scheduled.getAccountDestination());

      Optional<UserModel> accountDestination = this.usersRepository.findByAccountNumberAndIsDeletedFalse(scheduled.getAccountDestination().getAccountNumber());
      Optional<UserModel> accountOrigin = this.usersRepository.findByAccountNumberAndIsDeletedFalse(scheduled.getAccountOrigin().getAccountNumber());

      if(accountDestination.isEmpty() || accountOrigin.isEmpty()){
        scheduled.setStatus(TransferStatusEnum.ERROR);
        log.error("Error finding accounts on transfers batch process.");

      } else if (accountOrigin.get().getBalance() < scheduled.getFinalValue()) {
        scheduled.setStatus(TransferStatusEnum.ERROR);
        log.error("Error on proccess transfer, user '" + accountOrigin.get().getAccountNumber() + "' doesnt have enough balance.");

      } else {
        UserModel userOriginModel = accountOrigin.get();
        UserModel userDestinationModel = accountDestination.get();

        log.info("Transaction original value " + scheduled.getOriginalValue());
        log.info("Transaction final value " + scheduled.getFinalValue());

        userOriginModel.setBalance(accountOrigin.get().getBalance() - scheduled.getOriginalValue());
        userDestinationModel.setBalance(accountDestination.get().getBalance() + scheduled.getFinalValue());

        usersRepository.save(userOriginModel);
        usersRepository.save(userDestinationModel);

        scheduled.setStatus(TransferStatusEnum.FINISHED);
      }

      scheduled.setAttempts(scheduled.getAttempts() + 1);
      transfersRepository.save(scheduled);
      
      log.info("Finishing scheduled transfer process");
      log.info("------------------------");
      
    }

    log.info("Exiting batch process");
    log.info("------------------------");
  }
  
}
