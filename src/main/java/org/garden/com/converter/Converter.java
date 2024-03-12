package org.garden.com.converter;

public interface Converter <Entity, Dto> {

    Dto toDto(Entity entity);
    Entity toEntity(Dto dto);
}
