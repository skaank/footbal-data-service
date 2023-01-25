package com.project.footballApp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    @Length(max = 100,min=2)
    private String name;
    @Column(name = "email")
    private String email;
    @Length(max = 100,min=4)
    @Column(name = "password")
    private String password;
    @Column(name = "roles")
    private String roles;
}
