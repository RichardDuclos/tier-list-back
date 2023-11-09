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
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String color;

    private Integer order;

    @ManyToOne
    @JoinColumn(name = "tier_list_id")
    private TierList tierList;

    @OneToMany(mappedBy = "rank")
    private Set<Element> elements = new HashSet<>();
}
