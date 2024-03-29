package org.garden.com.repository;

import org.garden.com.entity.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesJpaRepository extends JpaRepository<Favorites, Long> {
}
