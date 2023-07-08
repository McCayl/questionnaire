package com.mccayl.questionnaire.repo;

import com.mccayl.questionnaire.dto.UserScoreDTO;
import com.mccayl.questionnaire.model.Test;
import com.mccayl.questionnaire.model.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    @Query("""
           select new com.mccayl.questionnaire.dto.UserScoreDTO(ut.user.email, sum(ut.userScore) as score)
           from UserTest ut
           where ut.test.theme.id = :th_id
           group by ut.user.email
           """)
    List<UserScoreDTO> getUserRating(@Param("th_id") Long themeId, Pageable pageable);
    @Query(value = """
            from Test t
            join fetch t.theme th
            where th.id = :th_id\s
            and t.available = true\s
            and t.attempts >\s
                (select count(ut.test)\s
                from UserTest ut\s
                where ut.user.id = :user_id and ut.test.id = t.id)
            """, countQuery = """
            select count(t)\s
            from Test t
            join t.theme th
            where th.id = :th_id\s
            and t.available = true\s
            and t.attempts >\s
                (select count(ut.test)\s
                from UserTest ut\s
                where ut.user.id = :user_id and ut.test.id = t.id)
            """)
    Page<Test> getAvailableTests(@Param("th_id") Long themeId,
                                 @Param("user_id") Long uid,
                                 Pageable pageable);
    @Query("""
            from Theme th
            """)
    Page<Theme> getThemes(Pageable pageable);
}
