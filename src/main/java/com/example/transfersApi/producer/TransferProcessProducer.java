package com.example.transfersApi.producer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.transfersApi.models.TransferModel;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Component
public class TransferProcessProducer implements ITransferProcessProducer {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;
  
  @Override
  public void sendToQueue(TransferModel transfer){
    int attempts = 0;
    boolean success = false;

    log.info("Enviando transferencia de id '" + transfer.getId() + "' para a fila de processamento");
    while (!success && attempts < 3) {
      try {
        kafkaTemplate.send("proccessing-transfer-topic", transfer.getId().toString());
        log.info("Envio da transferencia de id '" + transfer.getId() + "' realizado com sucesso para a fila de processamento");
        success = true;
      } catch (Exception e) {
        log.error(e);
      }
      attempts++;
    }
  }
  
}
