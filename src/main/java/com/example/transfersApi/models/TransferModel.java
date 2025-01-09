package com.example.transfersApi.models;

import java.util.Date;

import com.example.transfersApi.common.BaseModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true) 
@Entity
@Table(name = "transfers")
public class TransferModel extends BaseModel {
  
  @Column(name = "finalValue")
  public Double finalValue;

  @Column(name = "originalValue")
  public Double originalValue;

  @ManyToOne
  @JoinColumn(name = "accountOrigin")
  public UserModel accountOrigin;

  @ManyToOne
  @JoinColumn(name = "accountDestination")
  public UserModel accountDestination;

  @Column(name = "scheduledDate")
  public Date scheduledDate;

  @Column(name = "fee")
  public Double fee;
  
}
