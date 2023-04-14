package com.example.REST_API.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "characters")
@Data
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long characterId;
    private String name;
    private int level;
    private int exp;
    private int strength;
    private int agility;
//    private int power;
//    private int intelligence;
//    private int influence;
    @JsonManagedReference
    @OneToMany(mappedBy = "characterId", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private List<Spellbook> spellbooks = new ArrayList<Spellbook>();
}
