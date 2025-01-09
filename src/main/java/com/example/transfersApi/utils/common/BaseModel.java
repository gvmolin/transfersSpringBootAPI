package com.example.transfersApi.utils.common;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
// import lombok.EqualsAndHashCode;

@Data
// @EqualsAndHashCode(callSuper = false) 
@MappedSuperclass
public class BaseModel {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  public UUID id;

  @Column(name = "created")
  public Date created;

  @Column(name = "updated")
  public Date updated;

  @Column(name = "isDeleted")
  public boolean isDeleted;

  @PrePersist
  public void onCreate() {
      Date now = new Date();
      this.created = now;
      this.updated = now;  
  }

  @PreUpdate
  public void onUpdate() {
      this.updated = new Date(); 
  }

}
