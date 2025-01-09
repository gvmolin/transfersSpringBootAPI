package com.example.transfersApi.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.transfersApi.dto.TransfersDTO;
import com.example.transfersApi.models.TransferModel;
import com.example.transfersApi.services.TransfersService;

@RestController
@RequestMapping("/transfers")
public class TransfersController {

  private final TransfersService service;

  public TransfersController(
    TransfersService service
  ){
    this.service = service;
  }

  @PostMapping("/create")
  public TransferModel create(@RequestBody TransfersDTO Transfer){
    return service.create(Transfer);
  }

  @PutMapping("/update/{id}")
  public TransferModel update(@PathVariable UUID id, @RequestBody TransfersDTO Transfer){
    return service.update(Transfer, id);
  }

  @DeleteMapping("/delete/{id}")
  public TransferModel delete(@PathVariable UUID id){
    return service.delete(id);
  }

  @GetMapping("/get")
  public Page<TransferModel> get(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ){
    return service.findAll(page, size);
  }

  @GetMapping("/get/{id}")
  public TransferModel get(@PathVariable UUID id){
    return service.findOne(id);
  }
}
