package ru.cft.shift.crowdfundingplatformapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FileMetaInformation {

    @Id
    private UUID id;

    private String name;

}
