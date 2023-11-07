package com.richardduclos.tierlist.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Boolean isAdmin = false;

    private String username;

    private String email;

    private String password;

    @OneToMany(mappedBy = "owner")
    private Set<TierList> tierLists = new HashSet<>();

    public User(UUID id, Boolean isAdmin, String username, String email, String password) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(Boolean isAdmin, String username, String email, String password) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
