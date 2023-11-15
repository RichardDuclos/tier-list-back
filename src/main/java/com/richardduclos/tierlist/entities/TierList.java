package com.richardduclos.tierlist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TierList {

    public interface Creation {}

    public interface Update {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(groups = {Creation.class}, message = "not-blank")
    @Length(max = 20, groups = {Creation.class}, message = "max-length")
    private String name;

    @NotBlank(groups = {Creation.class}, message = "not-blank")
    @Length(max = 250, groups = {Creation.class}, message = "max-length")
    private String description;

    @NotBlank(groups = {Update.class})
    private boolean draft = true;

    @Enumerated(EnumType.STRING)
    private TierListState approvedState = TierListState.PENDING;

    @JsonIgnoreProperties(value = {"owner", "handler","hibernateLazyInitializer"}, allowSetters = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private User owner;

    @JsonIgnoreProperties(value = {"tierlist", "handler","hibernateLazyInitializer"}, allowSetters = true)
    @OneToMany(mappedBy = "tierlist", fetch = FetchType.EAGER)
    private Set<Rank> ranks = new HashSet<>();
}
