package com.richardduclos.tierlist.entities;

import jakarta.persistence.*;

@Entity
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String imagePath;

    private Integer order;

    @ManyToOne
    @JoinColumn(name = "rank_id")
    private Rank rank;
    public Element(Integer id, String imagePath, Integer order) {
        this.id = id;
        this.imagePath = imagePath;
        this.order = order;
    }

    public Element(String imagePath, Integer order) {
        this.imagePath = imagePath;
        this.order = order;
    }


    public Element() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
