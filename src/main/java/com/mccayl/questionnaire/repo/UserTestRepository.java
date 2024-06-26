package com.mccayl.questionnaire.repo;

import com.mccayl.questionnaire.model.Answer;
import com.mccayl.questionnaire.model.Test;
import com.mccayl.questionnaire.model.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserTestRepository extends JpaRepository<UserTest, Long> {
    @Query("""
            select ut.test from UserTest ut
            where ut.test.id = :test_id
            """)
    Optional<Test> getTestById(@Param("test_id") Long tid);
    @Query("""
            from UserTest ut
            where ut.id = :ut_id
            """)
    UserTest findUserTestById(@Param("ut_id") Long userTestId);
    @Query("""
            from Answer a
            where a.id in :ids
            """)
    List<Answer> findUserAnswersByIds(@Param("ids") Iterable<Long> ids);
}
