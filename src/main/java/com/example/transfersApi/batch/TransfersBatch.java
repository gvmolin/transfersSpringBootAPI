package com.example.transfersApi.batch;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.transfersApi.enums.TransferStatusEnum;
import com.example.transfersApi.models.TransferModel;
import com.example.transfersApi.producer.ITransferProcessProducer;
import com.example.transfersApi.repositories.TransfersRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TransfersBatch {

  @Autowired
  private final TransfersRepository transfersRepository; 

  @Autowired
  private final ITransferProcessProducer processTransfersProducer; 

  public TransfersBatch(TransfersRepository transfersRepository, ITransferProcessProducer processTransfersProducer) {
    this.transfersRepository = transfersRepository;
    this.processTransfersProducer = processTransfersProducer;
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
      processTransfersProducer.sendToQueue(scheduled);
    }

    log.info("Exiting batch process");
    log.info("------------------------");
  }
  
}
