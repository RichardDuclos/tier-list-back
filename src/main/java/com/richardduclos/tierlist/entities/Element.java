package com.richardduclos.tierlist.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String imagePath;

    private Integer order;

    private String tag;

    @ManyToOne
    @JoinColumn(name = "rank_id")
    private Rank rank;
}
