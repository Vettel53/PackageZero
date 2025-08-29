package com.example.application.account.accountcreation;

import com.example.application.account.AppUser;
import com.example.application.account.UserRepo;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AccountCreationService {

    private final UserRepo userRepo;
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public AccountCreationService(UserRepo userRepo, UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    // Used to create users and save them in the database and detailsManager
    public void createUser(String username, String password) {
        String hashedPassword = passwordEncoder.encode(password); // Hash the password

        userDetailsManager.createUser(
                User.withUsername(username)
                        .password(hashedPassword) // Stored hashed password
                        .roles("USER").build()
        );

        AppUser newUser = new AppUser(username, hashedPassword); // Create user with hashed password
        userRepo.save(newUser);

    }

}
