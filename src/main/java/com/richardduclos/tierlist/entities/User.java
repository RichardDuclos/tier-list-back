package com.richardduclos.tierlist.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class User implements UserDetails {

    public interface Creation {}

    public interface Update {}

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    @NotBlank(message = "not-blank", groups = {Creation.class, Update.class})
    @Length(max = 20, message = "max",  groups = {Creation.class, Update.class})
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
    @JsonIgnoreProperties(value = {"owner", "handler","hibernateLazyInitializer"}, allowSetters = true)
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
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
