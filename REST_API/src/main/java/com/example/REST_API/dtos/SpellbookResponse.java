package com.example.REST_API.dtos;

import com.example.REST_API.entities.User;
import com.example.REST_API.entities.Spellbook;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpellbookResponse {
    private long spellbookId;
    private String text;
    private String trainer;
    private String rank;
    private User user;
}
