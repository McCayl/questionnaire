package com.mccayl.questionnaire.repo;

import com.mccayl.questionnaire.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {
    @Query("""
            from Test t
            join fetch t.theme
            where t.id = :test_id
            """)
    Optional<Test> findById(@Param("test_id") Long testId);
}
