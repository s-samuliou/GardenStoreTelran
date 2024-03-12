package org.garden.com.repository;

import org.garden.com.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findAllByName(String name);
}