package com.richardduclos.tierlist.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
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

    @NotBlank(groups = {Creation.class})
    @Length(max = 250, groups = {Creation.class}, message = "max-length")
    private String description;

    @NotBlank(groups = {Update.class})
    private boolean draft = true;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User owner;

    @OneToMany(mappedBy = "tierList")
    private Set<Rank> ranks = new HashSet<>();
}
