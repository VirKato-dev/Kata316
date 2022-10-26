package com.example.kata316.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String lastName;
    private Byte age;

    public String toString() {
        return String.format("{id=%d, name=%s, lastName=%s, age=%d}", id, name, lastName, age);
    }
}
