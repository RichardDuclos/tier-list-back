package com.richardduclos.tierlist.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TierList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private String tag;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User owner;

    @OneToMany(mappedBy = "tierList")
    private Set<Rank> ranks = new HashSet<>();
}
