package com.luxoft.proj.naceapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "nacedb",
       name = "nace")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
public class NaceEntity
{
    @Id
    @Column(name = "orderid",
            nullable = false)
    private Integer id;

    @Column(name = "level")
    private Integer level;

    @Column(name = "code")
    private String code;

    @Column(name = "parent")
    private String parent;

    @Column(name = "description")
    private String description;

    @Column(name = "includes")
    private String includes;

    @Column(name = "also_includes")
    private String also_includes;

    @Column(name = "rulings")
    private String rulings;

    @Column(name = "excludes")
    private String excludes;

    @Column(name = "referenceISIC_Rev4")
    private String references;
}
