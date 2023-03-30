package com.example.REST_API.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private long userId;
    private String name;
    private int strength;
//    private int power;
//    private int intelligence;
//    private int influence;
    @JsonManagedReference
    @OneToMany(mappedBy = "userId", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private List<Spellbook> spellbooks = new ArrayList<Spellbook>();
}
