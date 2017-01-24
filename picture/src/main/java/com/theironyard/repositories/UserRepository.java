package com.theironyard.repositories;

import com.theironyard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sparatan117 on 1/23/17.
 */
public interface UserRepository extends JpaRepository<User, Integer>{
    User findFirstByUsername(String username);
}
