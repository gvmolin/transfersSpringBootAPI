package com.example.transfersApi.dto;

import java.util.Date;

import com.example.transfersApi.models.UserModel;

import lombok.Data;

@Data
public class TransfersDTO {
  public Double finalValue;
  public Double originalValue;
  public UserModel accountOrigin;
  public UserModel accountDestination;
  public Date scheduledDate;
  public Double fee;
}
