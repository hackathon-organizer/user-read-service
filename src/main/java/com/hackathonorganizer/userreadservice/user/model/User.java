package com.hackathonorganizer.userreadservice.user.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profile")
public class User {

    @OneToMany(mappedBy = "user")
    Set<ScheduleEntry> scheduleEntries;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String username;

    private String description;

    @NotEmpty
    private String keyCloakId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private String githubProfileUrl;

    private String profilePictureUrl;

    private Long currentHackathonId;

    private Long currentTeamId;

    private boolean blocked;

    @ManyToMany
    @JoinTable(name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
}
