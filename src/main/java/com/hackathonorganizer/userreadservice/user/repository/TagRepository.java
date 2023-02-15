package com.hackathonorganizer.userreadservice.user.repository;

import com.hackathonorganizer.userreadservice.user.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
