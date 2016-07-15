package edu.oregonstate.mist.recommendations.db

import edu.oregonstate.mist.recommendations.core.Recommendation
import edu.oregonstate.mist.recommendations.mapper.RecommendationMapper
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

/**
 * Recommendation DAO
 */
@RegisterMapper(RecommendationMapper)
public interface RecommendationDAO extends Closeable {

    @SqlQuery("""
              SELECT * FROM (
                SELECT T.*, ROWNUM AS R_NUM
                FROM (
                    SELECT UNIVERSITY.NAME AS UNIVERSITY,
                           UNIVERSITY.IS_985,
                           UNIVERSITY.IS_211,
                           ENROLLMENT_UNIVERSITY.YEAR,
                           ENROLLMENT_UNIVERSITY.SCORE_AVG,
                           ENROLLMENT_UNIVERSITY.STU_COUNT,
                           RANKING.RANK,
                           BATCH_SCORE.MIN_SCORE AS BATCH_SCORE,
                           (ENROLLMENT_UNIVERSITY.SCORE_AVG - BATCH_SCORE.MIN_SCORE) AS SCORE_DIFF,
                           NULL AS MAJOR
                    FROM ENROLLMENT_UNIVERSITY
                      LEFT JOIN RANKING
                      ON RANKING.YEAR = ENROLLMENT_UNIVERSITY.YEAR AND
                         RANKING.SCORE = ENROLLMENT_UNIVERSITY.SCORE_AVG AND
                         RANKING.STUDENT_POOL_ID = ENROLLMENT_UNIVERSITY.STUDENT_POOL_ID
                      LEFT JOIN BATCH_SCORE
                      ON BATCH_SCORE.STUDENT_POOL_ID = ENROLLMENT_UNIVERSITY.STUDENT_POOL_ID AND
                         BATCH_SCORE.YEAR = ENROLLMENT_UNIVERSITY.YEAR
                      LEFT JOIN UNIVERSITY
                      ON UNIVERSITY.ID = ENROLLMENT_UNIVERSITY.UNIVERSITY_ID
                    WHERE
                       ENROLLMENT_UNIVERSITY.STUDENT_POOL_ID = (
                          SELECT STUDENT_POOL.ID FROM STUDENT_POOL
                          WHERE PROVINCE = :province AND
                                STU_TYPE = :studentType AND
                                BATCH = :batch
                         ) AND
                         RANK >= :minRank AND
                         RANK <= :maxRank AND
                         ENROLLMENT_UNIVERSITY.YEAR like '%' ||:year|| '%'
                    ORDER BY ENROLLMENT_UNIVERSITY.UNIVERSITY_ID, ENROLLMENT_UNIVERSITY.YEAR ) T
                )
                WHERE R_NUM > :pageSize * :pageNum AND
                      R_NUM <= :pageSize * :pageNum + :pageSize
              """)
    List<Recommendation> getUniversitiesByRank (@Bind("province") String province,
                                                @Bind("studentType") String studentType,
                                                @Bind("batch") Integer batch,
                                                @Bind("minRank") Integer minRank,
                                                @Bind("maxRank") Integer maxRank,
                                                @Bind("year") Integer year,
                                                @Bind("pageSize") Integer pageSize,
                                                @Bind("pageNum") Integer pageNum
                                               )

    @SqlQuery("""
                SELECT * FROM (
                SELECT T.*, ROWNUM AS R_NUM
                FROM (
                    SELECT UNIVERSITY.NAME AS UNIVERSITY,
                           UNIVERSITY.IS_985,
                           UNIVERSITY.IS_211,
                           ENROLLMENT_MAJOR.MAJOR,
                           ENROLLMENT_MAJOR.YEAR,
                           ENROLLMENT_MAJOR.SCORE_AVG,
                           RANKING.RANK,
                           BATCH_SCORE.MIN_SCORE AS BATCH_SCORE,
                           (ENROLLMENT_MAJOR.SCORE_AVG - BATCH_SCORE.MIN_SCORE) AS SCORE_DIFF,
                           NULL AS STU_COUNT
                    FROM ENROLLMENT_MAJOR
                      LEFT JOIN RANKING
                      ON RANKING.YEAR = ENROLLMENT_MAJOR.YEAR AND
                         RANKING.SCORE = ENROLLMENT_MAJOR.SCORE_AVG AND
                         RANKING.STUDENT_POOL_ID = ENROLLMENT_MAJOR.STUDENT_POOL_ID
                      LEFT JOIN BATCH_SCORE
                      ON BATCH_SCORE.STUDENT_POOL_ID = ENROLLMENT_MAJOR.STUDENT_POOL_ID AND
                         BATCH_SCORE.YEAR = ENROLLMENT_MAJOR.YEAR
                      LEFT JOIN UNIVERSITY
                      ON UNIVERSITY.ID = ENROLLMENT_MAJOR.UNIVERSITY_ID
                    WHERE
                       ENROLLMENT_MAJOR.STUDENT_POOL_ID = (
                          SELECT STUDENT_POOL.ID FROM STUDENT_POOL
                          WHERE PROVINCE = :province AND
                                STU_TYPE = :studentType AND
                                BATCH = :batch
                         ) AND
                         RANK >= :minRank AND
                         RANK <= :maxRank AND
                         ENROLLMENT_MAJOR.YEAR like '%' ||:year|| '%' AND
                         ENROLLMENT_MAJOR.MAJOR like '%' ||:major|| '%'
                    ORDER BY ENROLLMENT_MAJOR.UNIVERSITY_ID, ENROLLMENT_MAJOR.YEAR ) T
                )
                WHERE R_NUM > :pageSize * :pageNum AND
                      R_NUM <= :pageSize * :pageNum + :pageSize
              """)
    List<Recommendation> getMajorsByRank (@Bind("province") String province,
                                          @Bind("studentType") String studentType,
                                          @Bind("batch") Integer batch,
                                          @Bind("minRank") Integer minRank,
                                          @Bind("maxRank") Integer maxRank,
                                          @Bind("year") Integer year,
                                          @Bind("major") String major,
                                          @Bind("pageSize") Integer pageSize,
                                          @Bind("pageNum") Integer pageNum
    )

    @Override
    void close()
}
