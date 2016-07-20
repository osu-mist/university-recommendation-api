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

    @Override
    void close()
}
