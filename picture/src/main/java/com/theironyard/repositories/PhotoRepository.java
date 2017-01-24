package com.theironyard.repositories;

import com.theironyard.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sparatan117 on 1/23/17.
 */
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
}
