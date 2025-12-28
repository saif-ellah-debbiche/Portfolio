package org.example.portfolio.dtos;

import lombok.Data;

@Data
public class AccountCreationDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String imageLink;
}
