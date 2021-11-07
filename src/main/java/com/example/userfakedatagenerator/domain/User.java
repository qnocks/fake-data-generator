package com.example.userfakedatagenerator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {


    private Long number;

    @Id
    private Long id;

    private String fullName;

    private String address;

    private String phone;

}
