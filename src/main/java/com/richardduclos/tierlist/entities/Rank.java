package com.richardduclos.tierlist.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
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
    public Rank(Integer id, String name, String color, Integer order) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.order = order;
    }

    public Rank(String name, String color, Integer order) {
        this.name = name;
        this.color = color;
        this.order = order;
    }


    public Rank() {

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
