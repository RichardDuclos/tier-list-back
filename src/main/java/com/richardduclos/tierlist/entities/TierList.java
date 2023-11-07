package com.richardduclos.tierlist.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class TierList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private String tag;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "tierList")
    private Set<Rank> ranks = new HashSet<>();

    public TierList(Integer id, String name, String description, String tag) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tag = tag;
    }

    public TierList(String name, String description, String tag) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tag = tag;
    }

    public TierList() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
