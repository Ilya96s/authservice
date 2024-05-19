package com.example.authservice.service;

import com.example.authservice.dto.UserDto;
import com.example.authservice.exception.AuthorizedException;
import com.example.authservice.exception.RegistrationException;
import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void register(UserDto userDto) {
        if (userRepository.findByName(userDto.getName()).isPresent()) {
            throw new RegistrationException(
                    String.format("User with username: %s already exists, choose another name", userDto.getName()));
        }
        String hash = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
        User buildUser = User.builder()
                .name(userDto.getName())
                .password(hash)
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .build();
        userRepository.save(buildUser);
    }

    public void checkCredentials(String username, String password) {
        Optional<User> userFromDb = userRepository.findByName(username);
        if (userFromDb.isEmpty()) {
            throw new AuthorizedException(
                    String.format("User with username: %s not found", username));
        }

        User user = userFromDb.get();
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new AuthorizedException("Password is incorrect");
        }
    }
}
