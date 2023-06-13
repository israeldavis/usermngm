package com.buildingblocks.base.repositories;

import com.buildingblocks.base.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);
}
