package com.example.REST_API.dtos;

import com.example.REST_API.entities.Character;
import com.example.REST_API.entities.Spellbook;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpellbookRequest {
    private long spellbookId;
    private String text;
    private String trainer;
    private String rank;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE LLL dd HH:mm:ss Z yyyy")pell
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy HH:mm:ss aa")
    private Date date;
    private Character character;
}
