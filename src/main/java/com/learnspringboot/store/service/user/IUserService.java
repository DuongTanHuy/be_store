package com.learnspringboot.store.service.user;

import com.learnspringboot.store.dto.UserDto;
import com.learnspringboot.store.model.User;
import com.learnspringboot.store.request.CreateUserRequest;
import com.learnspringboot.store.request.UpdateUserRequest;

public interface IUserService {
    UserDto getUserById(Long id);

    UserDto createUser(CreateUserRequest request);

    UserDto updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);

    User getAuthenticateUser();

}
