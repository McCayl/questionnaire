package com.mccayl.questionnaire.repo;

import com.mccayl.questionnaire.dto.TestScoreDTO;
import com.mccayl.questionnaire.model.Question;
import com.mccayl.questionnaire.model.User;
import com.mccayl.questionnaire.model.UserTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            from User u
            where u.email = :u_email
            """)
    Optional<User> getUserByEmail(@Param("u_email") String email);

    @Query("""
           select new com.mccayl.questionnaire.dto.TestScoreDTO(ut.test.name, sum(ut.userScore) as score)
           from UserTest ut
           where ut.user.id = :user_id
           group by ut.test.id
            """)
    Page<TestScoreDTO> getUserScore(@Param("user_id") Long uid, Pageable pageable);
    @Query(value = """
            from UserTest ut
            where ut.user.id = :user_id
            """)
    Page<UserTest> getUserInfoById(@Param("user_id") Long uid, Pageable pageable);
    @Query(value = """
            from UserTest ut
            where ut.user.email like :keyword%
            """)
    Page<UserTest> getUserInfoByEmailLikeKeyword(@Param("keyword") String keyword, Pageable pageable);
    @Query(value = """
            from UserTest ut
            """)
    Page<UserTest> getAllUserInfo(Pageable pageable);
    @Query("""
            select ut.questions
            from UserTest ut
            where ut.id = :ut_id
            """)
    Page<Question> getUserQuestionsByUtId(@Param("ut_id") Long userTestId, Pageable pageable);
}
