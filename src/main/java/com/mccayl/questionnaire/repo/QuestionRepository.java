package com.mccayl.questionnaire.repo;

import com.mccayl.questionnaire.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
//    @Query("""
//            select t.questions
//            from Test t
//            where t.id = :test_id
//            """)
//    Page<Question> findQuestionsByTestId(@Param("test_id") Long testId,
//                                         Pageable pageable);
    @Query("""
            from Question q
            where q.id = :question_id
            """)
    Optional<Question> findQuestionById(@Param("question_id") Long questionId);

//    @Query("""
//            from Test t
//            join t.questions tq
//            where t.id = :test_id
//            and tq.id not in :ids
//            """)
//    List<Question> findQuestionsByTestIdAndNotIds(@Param("test_id") Long testId,
//                                                  @Param("ids") Iterable<Long> ids);
}
