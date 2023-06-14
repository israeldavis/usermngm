package com.buildingblocks.base.repositories;

import com.buildingblocks.base.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
