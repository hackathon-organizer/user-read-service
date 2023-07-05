package com.hackathonorganizer.userreadservice.user.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profile")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Username must not be empty")
    private String username;

    @NotEmpty(message = "Description must not be empty")
    private String description;

    @NotEmpty(message = "Keycloak id must not be empty")
    @Column(updatable = false)
    private String keyCloakId;

    private Long currentHackathonId;

    private Long currentTeamId;

    private boolean blocked;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<ScheduleEntry> scheduleEntries = new HashSet<>();
}
