package com.example.REST_API.dtos;
import com.example.REST_API.entities.Spellbook;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse
{
    private long userId;
    private String name;
    private int strength;
    private List<Spellbook> spellbooks = new ArrayList<Spellbook>();

    public UserResponse(long id) {
    }
}

