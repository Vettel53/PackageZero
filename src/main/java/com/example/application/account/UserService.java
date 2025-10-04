package com.example.application.account;

import com.example.application.security.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDetailsManager userDetailsManager;
    private final SecurityService securityService;
    private final UserRepo userRepo;

    public UserService(UserDetailsManager userDetailsManager, SecurityService securityService, UserRepo userRepo) {
        this.userDetailsManager = userDetailsManager;
        this.securityService = securityService;
        this.userRepo = userRepo;
    }

    public boolean userExists(String accountName) {
        return userDetailsManager.userExists(accountName); // Return true or false based on if user exists
    }

    public String getUsername() {
        UserDetails loadUserDetails = securityService.getAuthenticatedUser();
        if (loadUserDetails == null) {
            return null;
        }
        String username = loadUserDetails.getUsername();
        System.out.println("The Authenticated User returned this username: " + username);
        return username;
    }

    public AppUser getCurrentAppUser(String username) {
        return userRepo.findByUsername(username);
    }
}
