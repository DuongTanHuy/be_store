package com.learnspringboot.store.data;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.learnspringboot.store.model.User;
import com.learnspringboot.store.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;

    private void createDefaultUserIfNotExits() {
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User newUser = new User();
            newUser.setFirstName("The User");
            newUser.setLastName("User " + i);
            newUser.setEmail(defaultEmail);
            newUser.setPassword("123456");
            userRepository.save(newUser);

            System.out.println("Created default user with email: " + defaultEmail);
        }
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExits();
    }

}
