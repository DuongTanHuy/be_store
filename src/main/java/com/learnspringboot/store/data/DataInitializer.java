package com.learnspringboot.store.data;

import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.learnspringboot.store.model.Role;
import com.learnspringboot.store.model.User;
import com.learnspringboot.store.repository.RoleRepository;
import com.learnspringboot.store.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private void createDefaultUserIfNotExits() {
        Role userRole = roleRepository.findByName("ROLE_USER");
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User newUser = new User();
            newUser.setFirstName("The User");
            newUser.setLastName("User " + i);
            newUser.setEmail(defaultEmail);
            newUser.setPassword(passwordEncoder.encode("123456"));
            newUser.setRoles(Set.of(userRole));
            userRepository.save(newUser);

            System.out.println("Created default user with email: " + defaultEmail);
        }
    }

    private void createDefaultAdminIfNotExits() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        String defaultEmail = "admin@gmail.com";
        if (userRepository.existsByEmail(defaultEmail)) {
            return;
        }
        User newUser = new User();
        newUser.setFirstName("The Admin");
        newUser.setLastName("Admin");
        newUser.setEmail(defaultEmail);
        newUser.setPassword(passwordEncoder.encode("123456"));
        newUser.setRoles(Set.of(adminRole));
        userRepository.save(newUser);

        System.out.println("Created default admin with email: " + defaultEmail);
    }

    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role) == null)
                .map(Role::new)
                .forEach(roleRepository::save);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultRoleIfNotExists(Set.of("ROLE_ADMIN", "ROLE_USER"));
        createDefaultAdminIfNotExits();
        createDefaultUserIfNotExits();
    }

}
