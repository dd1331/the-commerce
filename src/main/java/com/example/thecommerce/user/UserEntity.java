package com.example.thecommerce.user;

import lombok.*;

import javax.persistence.*;

@Table(name = "users")
@Entity
@Builder
@Getter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue()
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String identifier;

    private String password;

    private String nickname;

    private String name;

    @Column(unique = true)
    private String mobile;

    @Column(unique = true)
    private String email;


}
