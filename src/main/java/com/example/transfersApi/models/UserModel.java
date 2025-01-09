package com.example.transfersApi.models;

import com.example.transfersApi.common.BaseModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true) 
@Entity
@Table(name = "users")
public class UserModel extends BaseModel {

  @Column(name = "name", nullable = false)
  public String name;

  @Column(name = "accountNumber", unique = true, nullable = false)
  public String accountNumber;

  @Column(name = "balance", nullable = false)
  public Double balance;

}
