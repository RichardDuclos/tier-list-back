package com.richardduclos.tierlist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
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

    @JsonIgnoreProperties(value = {"tierlist", "handler","hibernateLazyInitializer"}, allowSetters = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @Valid
    @Null(groups = {Update.class}, message = "null")
    @NotNull(message = "not-null", groups = {Creation.class})
    @JoinColumn(name = "tier_list_id")
    private TierList tierlist;

    @JsonIgnoreProperties(value = {"rank", "handler","hibernateLazyInitializer"}, allowSetters = true)
    @OneToMany(mappedBy = "rank", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Element> elements = new HashSet<>();
}
