package com.example.transfersApi.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.transfersApi.dto.UsersDTO;
import com.example.transfersApi.models.UserModel;
import com.example.transfersApi.repositories.UsersRepository;

@Service
public class UsersService {

  private final UsersRepository usersRepository; 
  private final ModelMapper modelMapper;

  public UsersService(UsersRepository usersRepository, ModelMapper modelMapper) {
      this.usersRepository = usersRepository;
      this.modelMapper = modelMapper;
  }

  public UserModel create(UsersDTO user){
    UserModel userModel = modelMapper.map(user, UserModel.class);
    UserModel result = usersRepository.save(userModel);
    return result;
  }

  public UserModel update(UsersDTO userDTO, UUID id) {
    Optional<UserModel> optionalUser = usersRepository.findById(id);

    if (optionalUser.isEmpty()) {
        throw new RuntimeException("Usuário não encontrado com o ID: " + id);
    }

    UserModel existingUser = optionalUser.get();
    modelMapper.map(userDTO, existingUser);

    return usersRepository.save(existingUser);
  }

  public UserModel delete(UUID id) {
    Optional<UserModel> foundUser = usersRepository.findById(id);

    if (foundUser.isEmpty()) {
        throw new RuntimeException("Usuário não encontrado com o ID: " + id);
    }

    UserModel existingUser = foundUser.get();
    existingUser.setDeleted(true);

    return usersRepository.save(existingUser);
  }
  
  public Page<UserModel> findAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return usersRepository.findAll(pageable);
  }

  public UserModel findOne(UUID id){
    Optional<UserModel> optionalUser = usersRepository.findById(id);

    if (optionalUser.isEmpty()) {
        throw new RuntimeException("Usuário não encontrado com o ID: " + id);
    }

    UserModel existingUser = optionalUser.get();
    return existingUser;
  }


}
