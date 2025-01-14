package com.example.transfersApi.producer;

import com.example.transfersApi.models.TransferModel;

public interface ITransferProcessProducer {
  void sendToQueue(TransferModel model);
}
