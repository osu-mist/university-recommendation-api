package edu.oregonstate.mist.recommendations.db

import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.SqlUpdate

/**
 * BatchScore DAO
 */
public interface BatchScoreDAO extends Closeable {

    @SqlQuery("""
               SELECT ID FROM BATCH_SCORE
               WHERE YEAR = :year AND
                     STUDENT_POOL_ID = ( SELECT ID FROM STUDENT_POOL
                                         WHERE PROVINCE = :province AND
                                               STU_TYPE = :studentType AND
                                               BATCH = :batch )
               """)
    Integer getBatchScoreID(@Bind("province") String province,
                            @Bind("studentType") String studentType,
                            @Bind("batch") Integer batch,
                            @Bind("year") Integer year)

    @SqlUpdate("""
                INSERT INTO BATCH_SCORE (ID, STUDENT_POOL_ID, YEAR, MIN_SCORE)
                    SELECT SEQ_BATCH_SCORE.NEXTVAL, STUDENT_POOL.ID, :year, :minScore
                    FROM STUDENT_POOL
                    WHERE PROVINCE = :province AND
                          STU_TYPE = :studentType AND
                          BATCH = :batch
               """)
    void insertBatchScore(@Bind("province") String province,
                          @Bind("studentType") String studentType,
                          @Bind("batch") Integer batch,
                          @Bind("year") Integer year,
                          @Bind("minScore") Integer minScore)

    @SqlUpdate("""
                UPDATE BATCH_SCORE
                  SET YEAR = 2015 , MIN_SCORE = 456,
                      STUDENT_POOL_ID = (SELECT ID FROM STUDENT_POOL
                                          WHERE STUDENT_POOL.BATCH = 3 AND
                                               STUDENT_POOL.PROVINCE = '福建' AND
                                               STUDENT_POOL.STU_TYPE = '文科')
                  WHERE ID = 1
                """)
    void updateBatchScore(@Bind("province") String province,
                          @Bind("studentType") String studentType,
                          @Bind("batch") Integer batch,
                          @Bind("year") Integer year,
                          @Bind("minScore") Integer minScore,
                          @Bind("id") Integer id)

    @Override
    void close()
}
