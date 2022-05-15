package com.example.research.Repositories;

import com.example.research.Entities.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {
    Area findByName(String name);
}
