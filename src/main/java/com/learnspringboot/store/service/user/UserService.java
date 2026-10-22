package com.learnspringboot.store.service.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learnspringboot.store.dto.UserDto;
import com.learnspringboot.store.exceptions.AlreadyExistsException;
import com.learnspringboot.store.exceptions.ResourceNotFoundException;
import com.learnspringboot.store.model.User;
import com.learnspringboot.store.repository.UserRepository;
import com.learnspringboot.store.request.CreateUserRequest;
import com.learnspringboot.store.request.UpdateUserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private UserDto mapToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        return mapToUserDto(user);
    }

    @Override
    public UserDto createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(req -> !userRepository.existsByEmail(req.getEmail()))
                .map(req -> {
                    User newUser = new User();

                    newUser.setFirstName(req.getFirstName());
                    newUser.setLastName(req.getLastName());
                    newUser.setEmail(req.getEmail());
                    newUser.setPassword(passwordEncoder.encode(req.getPassword()));

                    return mapToUserDto(userRepository.save(newUser));
                }).orElseThrow(() -> new AlreadyExistsException(request.getEmail() + " already exists!"));
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserRequest request) {
        return userRepository.findById(id).map(user -> {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());

            return mapToUserDto(userRepository.save(user));
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete,
                () -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User getAuthenticateUser() {
        // request phải đi qua AuthTokenFilter (đã set authentication vào
        // SecurityContextHolder) thì mới có thể lấy được authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        return user;
    }

}
