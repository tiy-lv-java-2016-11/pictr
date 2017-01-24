package com.theironyard.repositories;

import com.theironyard.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Integer>{
}
