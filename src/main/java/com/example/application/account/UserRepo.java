package com.example.application.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);

}
