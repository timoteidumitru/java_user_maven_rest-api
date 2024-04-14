package com.users.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private String email;
    private String password;
}
