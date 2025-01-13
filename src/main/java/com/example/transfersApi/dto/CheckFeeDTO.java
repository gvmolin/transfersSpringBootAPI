package com.example.transfersApi.dto;

import lombok.Data;

@Data
public class CheckFeeDTO {
  public Double fee;
  public Double finalValue;
  public Double originAccountBalance;
}
