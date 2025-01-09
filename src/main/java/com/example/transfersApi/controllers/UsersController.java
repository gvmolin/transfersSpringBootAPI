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

import com.example.transfersApi.dto.UsersDTO;
import com.example.transfersApi.models.UserModel;
import com.example.transfersApi.services.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {

  private final UsersService service;

  public UsersController(
    UsersService service
  ){
    this.service = service;
  }

  @PostMapping("/create")
  public UserModel create(@RequestBody UsersDTO user){
    return service.create(user);
  }

  @PutMapping("/update/{id}")
  public UserModel update(@PathVariable UUID id, @RequestBody UsersDTO user){
    return service.update(user, id);
  }

  @DeleteMapping("/delete/{id}")
  public UserModel delete(@PathVariable UUID id){
    return service.delete(id);
  }

  @GetMapping("/get")
  public Page<UserModel> get(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ){
    return service.findAll(page, size);
  }

  @GetMapping("/get/{id}")
  public UserModel get(@PathVariable UUID id){
    return service.findOne(id);
  }
}
