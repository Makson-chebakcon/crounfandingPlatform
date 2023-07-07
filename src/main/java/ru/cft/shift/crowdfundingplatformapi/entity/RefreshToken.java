package ru.cft.shift.crowdfundingplatformapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 1024)
    private String value;

    private Date createdAt;

    private Date expiredAt;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Person owner;

}
