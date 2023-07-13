package ru.cft.shift.crowdfundingplatformapi.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.cft.shift.crowdfundingplatformapi.enumeration.Category;
import ru.cft.shift.crowdfundingplatformapi.enumeration.Status;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String summary;

    @Column(length = 32768)
    private String description;

    private BigDecimal targetAmount;

    private BigDecimal collectedAmount;

    private UUID avatarId;

    private Date creationDate;

    private Date finishDate;
    private Boolean isApproved;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ElementCollection
    private List<UUID> attachmentIds;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Person author;

    @OneToOne
    @JoinColumn(name = "project_request_id")
    private ProjectRequest request;

}
