package com.richardduclos.tierlist.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
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

    public interface Creation {}

    public interface Update {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Null(groups = {Creation.class, Update.class}, message = "null")
    private String imagePath;

    @Null(groups = {Creation.class}, message = "null")
    @NotBlank(groups = {Update.class}, message = "not-blank")
    private Integer order;

    @Null(groups = {Creation.class}, message = "null")
    private String tag;

    @ManyToOne
    @Valid
    @NotNull(groups = {Creation.class}, message = "not-null")
    @JoinColumn(name = "rank_id")
    private Rank rank;
}
