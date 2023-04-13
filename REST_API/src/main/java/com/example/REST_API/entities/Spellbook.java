package com.example.REST_API.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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


    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy HH:mm:ss aa")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE LLL dd HH:mm:ss Z yyyy")
    private Date date;
    @ManyToOne //(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    @JoinColumn(name = "characterId")
    private Character characterId;
}
