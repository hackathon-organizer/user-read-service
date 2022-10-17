package com.hackathonorganizer.userreadservice.tag.dto;

import com.hackathonorganizer.userreadservice.tag.model.Tag;

public record TagResponseDto(

        Long id,

        String name
) {

    public TagResponseDto(Tag tag){
        this(
                tag.getId(),
                tag.getName()
        );
    }
}
