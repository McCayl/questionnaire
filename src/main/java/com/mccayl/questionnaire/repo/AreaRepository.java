package com.mccayl.questionnaire.repo;

import com.mccayl.questionnaire.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {
    Area findByName(String name);
}

