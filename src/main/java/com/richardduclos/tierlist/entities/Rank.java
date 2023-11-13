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
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rank {

    public interface Creation{}

    public interface Update {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "not-blank", groups = {Creation.class, Update.class})
    @Length(max = 20, message = "max")
    private String name;

    @NotBlank(message = "not-blank", groups = {Creation.class})
    @Length(max = 15, message = "max-length")
    private String color;

    @NotBlank(message = "not-blank", groups = {Update.class})
    private Integer order;

    @ManyToOne
    @Valid
    @Null(groups = {Update.class}, message = "null")
    @NotNull(message = "not-null", groups = {Creation.class})
    @JoinColumn(name = "tier_list_id")
    private TierList tierList;

    @OneToMany(mappedBy = "rank")
    private Set<Element> elements = new HashSet<>();
}
