package com.example.transfersApi.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TransfersDTO {
  public Double originalValue;
  public String originAccountNumber;
  public String destinationAccountNumber;
  public Date scheduledDate;
}
