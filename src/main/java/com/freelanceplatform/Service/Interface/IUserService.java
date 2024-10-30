package com.freelanceplatform.Service.Interface;

import com.freelanceplatform.DTO.UserLoginDTO;
import com.freelanceplatform.DTO.UserRegisterDTO;
import com.freelanceplatform.DTO.UserUpdateDTO;
import com.freelanceplatform.DAL.Entity.User;

import java.util.List;

public interface IUserService {

    User getUserById(Long id);

    List<User> getAllUsers();

    User createUser(UserRegisterDTO userRegisterDTO);

    User updateUser(UserUpdateDTO userUpdateDTO);

    boolean deleteUser(Long id);

    boolean authenticate(UserLoginDTO userLoginDTO);
}
