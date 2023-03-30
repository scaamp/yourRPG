package com.example.REST_API.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "spellbooks")
public class Spellbook {
    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private long spellbookId;
    private String text;
    private String trainer;
    private String rank;
    @ManyToOne //(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    @JoinColumn(name = "userId")
    private User userId;
}
