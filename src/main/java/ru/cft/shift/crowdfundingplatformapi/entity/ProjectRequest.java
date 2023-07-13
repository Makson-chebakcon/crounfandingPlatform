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
public class ProjectRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date creationDate;

    @OneToOne(mappedBy = "request", fetch = FetchType.EAGER)
    private Project project;

}
