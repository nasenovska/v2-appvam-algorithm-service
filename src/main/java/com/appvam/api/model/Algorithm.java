package com.appvam.api.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "algorithm")
@Data
public class Algorithm {

    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "key", unique = true, updatable = false)
    private String key;

    @Column(name = "animation_path", unique = true, updatable = false)
    private String animationPath;
}
