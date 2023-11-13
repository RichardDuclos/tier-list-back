package com.richardduclos.tierlist.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    public interface Creation {}

    interface Update {}

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    @NotBlank(message = "not-blank", groups = {Creation.class, Update.class})
    @Length(max = 20, message = "max")
    private String username;

    @Column(unique = true)
    @NotBlank(message = "not-blank", groups = {Creation.class, Update.class})
    private String email;

    private String password;

    @Transient
    @NotBlank(message = "not-blank", groups = {Creation.class})
    private String plainPassword;

    @Transient
    @NotBlank(message = "not-blank", groups = {Creation.class})
    private String plainPasswordConfirm;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Valid
    @OneToMany(mappedBy = "owner")
    private Set<TierList> tierLists = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
