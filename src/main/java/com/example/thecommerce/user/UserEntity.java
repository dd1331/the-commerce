package com.example.thecommerce.user;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user")
public class UserEntity {
    @Id @GeneratedValue()
    @Column(name = "user_id")
    private Long id;

    private String identifier;

    private String password;

    private String nickname;

    private String name;

    private String mobile;

    private String email;





}
