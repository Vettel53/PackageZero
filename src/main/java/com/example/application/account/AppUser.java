package com.example.application.account;

import jakarta.persistence.*;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String hashedPassword;

    // TODO: Understand this relationship deeper
    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    /**
     * Inverse Side of relationship (Bidirectional)
     * If you want to get a list of runs from a specific user.
     * (mappedBy = "appUser") -> Tells JPA the relationship in Run is managed by the appUser field.
     * JPA looks at the annotation in the Run "appUser" field to understand how join works.
     */
//    @OneToMany(mappedBy = "appUser")
//    private List<Run> runDetails;

    public AppUser() {
    }

    public AppUser(String username) {
        this.username = username;
    }

    public AppUser(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

//    public AppUser(String username, List<Run> runDetails) {
//        this.username = username;
//        this.runDetails = runDetails;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

//    public List<Run> getRunDetails() {
//        return runDetails;
//    }
//
//    public void setRunDetails(List<Run> runDetails) {
//        this.runDetails = runDetails;
//    }
}
