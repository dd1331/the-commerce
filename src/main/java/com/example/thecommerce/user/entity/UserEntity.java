package com.example.thecommerce.user.entity;

import com.example.thecommerce.user.dto.JoinDto;
import com.example.thecommerce.user.dto.UpdateDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Table(name = "users")
@Entity
@Builder
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue()
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String identifier;

    @Embedded
    private Password password;


    private String nickname;

    private String name;

    @Column(unique = true)
    private String mobile;

    @Column(unique = true)
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void update(UpdateDto dto) {
        this.nickname = Optional.ofNullable(dto.getNickname()).orElse(this.nickname);
        this.name = Optional.ofNullable(dto.getName()).orElse(this.name);
        this.mobile = Optional.ofNullable(dto.getMobile()).orElse(this.mobile);
        this.email = Optional.ofNullable(dto.getEmail()).orElse(this.email);
        this.password = Optional.ofNullable(dto.getPassword())
                .map(Password::new)
                .orElse(this.password);
    }

    public void join(JoinDto dto) {
        this.identifier = dto.getIdentifier();
        this.password = new Password(dto.getPassword());
        this.nickname = dto.getNickname();
        this.name = dto.getName();
        this.mobile = dto.getMobile();
        this.email = dto.getEmail();
    }
}
