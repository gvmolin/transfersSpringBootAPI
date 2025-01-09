package com.example.transfersApi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfers")
public class TransfersController {
  
  @GetMapping("/one")
  public String getAllTransfers(){
    return "retorna um";
  }

  @GetMapping("/list")
  public String getOneTransfer(){
    return "retorna varios";
  }

  @PostMapping("/create")
  public String createTransfers(){
    return "cria";
  }

  
}
