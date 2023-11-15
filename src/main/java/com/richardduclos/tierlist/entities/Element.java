package com.richardduclos.tierlist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Blob;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Element {



    public interface Creation {}

    public interface Update {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Lob
    @JdbcTypeCode(SqlTypes.BLOB)
    @JsonIgnore
    @Null(groups = {Update.class, Creation.class}, message = "null")
    private Blob imageBlob;


    @Null(groups = {Update.class}, message = "null")
    @NotBlank(groups = {Creation.class}, message = "not-blank")
    @Transient
    private String imageData;


    @Null(groups = {Creation.class}, message = "null")
    @NotBlank(groups = {Update.class}, message = "not-blank")
    private Integer order;

    @Null(groups = {Creation.class}, message = "null")
    private String tag;

    @JsonIgnoreProperties(value = {"rank", "handler","hibernateLazyInitializer"}, allowSetters = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @Valid
    @NotNull(groups = {Creation.class}, message = "not-null")
    @JoinColumn(name = "rank_id")
    private Rank rank;
}
