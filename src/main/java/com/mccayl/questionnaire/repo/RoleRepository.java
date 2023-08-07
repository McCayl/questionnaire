package com.mccayl.questionnaire.repo;

import com.mccayl.questionnaire.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
