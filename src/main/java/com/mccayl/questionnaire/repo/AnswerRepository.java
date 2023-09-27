package com.mccayl.questionnaire.repo;

import com.mccayl.questionnaire.model.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("""
            select q.answers
            from Question q
            where q.id = :question_id
            """)
    Page<Answer> findAnswersByQuestionId(@Param("question_id") Long questionId,
                                         Pageable pageable);
    @Query("""
            from Answer a
            where a.id = :answer_id
            """)
    Optional<Answer> findAnswerById(@Param("answer_id") Long answer_id);
    @Query("""
            from Answer a
            where a.id in :ids
            """)
    Page<Answer> findAnswersByIds(@Param("ids") Iterable<Long> ids, Pageable pageable);
}
