package com.example.transfersApi.consumer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.transfersApi.services.TransfersService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class TransferProcessConsumer implements ITransferProcessConsumer {

    @Autowired
    private TransfersService transferService;

    @KafkaListener(id = "listen1", topics = "proccessing-transfer-topic")
    public void readQueue1(String transferId) {
      log.info("Consumo da transferencia de id: '" + transferId + "'");
      transferService.ProcessTransfer(UUID.fromString(transferId));
    }
}
