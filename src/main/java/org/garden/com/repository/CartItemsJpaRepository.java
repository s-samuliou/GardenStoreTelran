package org.garden.com.repository;

import org.garden.com.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsJpaRepository extends JpaRepository<CartItem, Long> {
}
