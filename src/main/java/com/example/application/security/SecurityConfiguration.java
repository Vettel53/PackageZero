package com.example.application.security;

import com.example.application.account.AppUser;
import com.example.application.account.UserRepo;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    private final UserRepo userRepo;

    public SecurityConfiguration(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/images/**")).permitAll()
                //.anyRequest().authenticated() // Require login for everything else
        );
        http.formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
        );

        super.configure(http);
    }

    @Bean
    UserDetailsManager userDetailsManager(UserRepo userRepo) {
        return new InMemoryUserDetailsManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // User database initilization on application startup
    @Bean
    public CommandLineRunner loadInitialUsers(UserRepo userRepo, UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        return args -> {
            // Insert hardcoded TEST users into the database if they don't exist already
            if (userRepo.findByUsername("javi") == null) {
                AppUser testUser = new AppUser();
                testUser.setUsername("javi");
                testUser.setHashedPassword(passwordEncoder.encode("javi")); // Encrypt the password
                userRepo.save(testUser);
            }

            List<AppUser> users = userRepo.findAll();

            // Loop through all users
            for (AppUser appUser : users) {
                if (!userDetailsManager.userExists(appUser.getUsername())) {
                    userDetailsManager.createUser(
                            User.withUsername(appUser.getUsername())
                                    .password(appUser.getHashedPassword()) // Assume password is already encoded
                                    .roles("USER") // assuming role is simple like "USER"
                                    .build()
                    );
                }
            }
        };
    }

}
