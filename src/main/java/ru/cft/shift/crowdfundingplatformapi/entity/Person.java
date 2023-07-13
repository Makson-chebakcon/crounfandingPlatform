package ru.cft.shift.crowdfundingplatformapi.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.cft.shift.crowdfundingplatformapi.enumeration.PersonRole;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PersonRole role;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String patronymic;

    private String bio;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean emailIsConfirm;

    private UUID emailConfirmCode;

    private LocalDateTime emailConfirmCodeExpiresAt;

    @Column(nullable = false)
    private BigDecimal money;

    @OneToMany(mappedBy = "owner")
    private List<RefreshToken> refreshTokens;

    @OneToMany(mappedBy = "author")
    private List<Project> projects;

    private UUID avatarId;

}
