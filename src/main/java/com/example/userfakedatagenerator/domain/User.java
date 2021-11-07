package com.example.userfakedatagenerator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long number;

    private Long id;

    private String fullName;

    private String address;

    private String phone;

}
