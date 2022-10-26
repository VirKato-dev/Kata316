package com.example.kata316.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class Users {
    private List<User> users;

    public Users() {
        users = new ArrayList<>();
    }
}
