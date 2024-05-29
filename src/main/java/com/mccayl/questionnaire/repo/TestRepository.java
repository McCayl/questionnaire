package com.mccayl.questionnaire.repo;

import com.mccayl.questionnaire.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {
    @Query("""
            from Test t
            where t.id = :test_id
            """)
    Optional<Test> findTestById(@Param("test_id") Long testId);
    @Query("""
            select t.theme.id
            from Test t
            where t.id = :test_id
            """)
    Long getThemeIdByTestId(@Param("test_id") Long testId);
}
